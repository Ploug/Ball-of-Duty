package application.engine.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain_Entities.EntityType;

import application.account.Player;
import application.communication.Broker;
import application.communication.GameObjectDAO;
import application.engine.entities.BoDCharacter;
import application.engine.entities.Bullet;
import application.engine.factories.EntityFactory;
import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.physics.CollisionHandler;
import application.gui.GUI;
import application.gui.Leaderboard;
import application.util.LightEvent;
import application.util.Observable;
import application.util.Observation;
import application.util.Resources;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.transform.Affine;

/**
 * The map is where all the game objects are being handled.
 * 
 * @author Gruppe6
 *
 */
public class ClientMap extends Observable
{
    private static final long NANOSECONDS_TO_MILLISECONDS = 1000000;
    private static final long MILLISECONDS_TO_SECONDS = 1000;
    private Broker broker;
    private GraphicsContext gc;
    private Canvas canvas;
    private int mapWidth;
    private int mapHeight;
    private int frames = 0;
    public ConcurrentMap<Integer, GameObject> gameObjects;
    private Label fpsLabel, scoreLabel, healthLabel, ammoLabel, reloadingLabel;
    private VBox labelBox;
    private BoDCharacter clientChar;
    private Thread updateThread;
    private AnimationTimer animationTimer;
    private boolean mapActive = false;
    private int serverGameId;
    private Leaderboard leaderboard;
    private TranslatedPoint mapPoint;
    // private ConcurrentLinkedQueue<GameObject> unassignedBullets;
    private Affine startingAffine;
    private ConcurrentLinkedQueue<GameObjectDAO> addQueue;

    /**
     * Creates a client map defining the serverMap its based upon, the gamebox it should be drawn in, the broker it uses to communicate with
     * the server with and the character that belongs to the client.
     * 
     * @param serverGame
     *            The server map which the ClientMap is based upon.
     * @param gameBox
     *            The game box in which the ClientMap is drawn.
     * @param broker
     *            The broker the ClientMap uses to communicate with the server.
     * @param clientChar
     *            The character of the client.
     */
    public ClientMap(GameDTO serverGame, BorderPane gameBox, Broker broker, Player clientPlayer)
    {
        mapPoint = new TranslatedPoint(0, 0);
        mapWidth = serverGame.getMapWidth();
        mapHeight = serverGame.getMapHeight();

        // this.unassignedBullets = new ConcurrentLinkedQueue<>();

        this.addQueue = new ConcurrentLinkedQueue<>();
        this.serverGameId = serverGame.getGameId();
        this.leaderboard = new Leaderboard();

        mapActive = true;
        gameObjects = new ConcurrentHashMap<>();

       

        for (GameObjectDTO dto : serverGame.getGameObjects())
        {
                if (dto.getId() != clientPlayer.getCharacter().getId())
            {
                addGameObject(EntityFactory.getEntity(dto));
            }

        }
        setCharacter(clientPlayer.getCharacter());
        System.out.println("My id " + clientChar.getId());
        
        this.broker = broker;
        broker.activate(this);
        for (PlayerDTO pdto : serverGame.getPlayers())
        {
            BoDCharacter character = (BoDCharacter)gameObjects.get(pdto.getCharacterId());
            if (character == null)
            {
                continue;
            }
            character.setNickname(pdto.getNickname());
            if (clientPlayer.getId() == pdto.getId())
            {
                clientPlayer.setHighscore(pdto.getHighScore());
            }
        }

        fpsLabel = new Label();
        fpsLabel.setPrefSize(50, 20);
        fpsLabel.setText("fps: ");
        scoreLabel = new Label();
        scoreLabel.setPrefSize(70, 20);
        scoreLabel.setText("Score: ");
        healthLabel = new Label();
        healthLabel.setPrefSize(80, 20);
        healthLabel.setText("Health: ");
        ammoLabel = new Label();
        ammoLabel.setPrefSize(80, 20);
        ammoLabel.setText("Ammo: ");
        reloadingLabel = new Label();
        reloadingLabel.setPrefSize(80, 20);
        reloadingLabel.setText("");

        labelBox = new VBox();
        labelBox.setSpacing(1);
        gameBox.setLeft(labelBox);
        gameBox.setRight(leaderboard);
        BorderPane.setAlignment(leaderboard, Pos.TOP_LEFT);

        labelBox.getChildren().add(fpsLabel);
        labelBox.getChildren().add(scoreLabel);
        labelBox.getChildren().add(healthLabel);
        labelBox.getChildren().add(ammoLabel);
        labelBox.getChildren().add(reloadingLabel);

        this.canvas = (Canvas)gameBox.getCenter();
        gc = canvas.getGraphicsContext2D();
        startingAffine = gc.getTransform();
    }

    
    /**
     * Activates the game loop.
     */
    public void activate()
    {
        Image mapImage = new Image("images/texture_dirt.png");

        LightEvent uiPanelEvent = new LightEvent(250, () ->
        {
            ammoLabel.setText(clientChar.getWeapon().getMagazineSize() + "/" + clientChar.getWeapon().getMagazineMaxSize());
            if (clientChar.getWeapon().getReloading())
            {
                reloadingLabel.setText("Reloading");
            }
            else
            {
                reloadingLabel.setText("");
            }

            fpsLabel.setText("fps: " + frames * 4);// every 0.25 second, time by 4 to get frame per second.
            scoreLabel.setText("Score: " + (int)clientChar.getScore());
            if (!clientChar.isDestroyed())
            {
                healthLabel.setText("Health: " + clientChar.getHealth().getValue());
            }
            else
            {
                healthLabel.setText("Health: DEAD");
            }
            leaderboard.refresh();

            frames = 0;

        });

        animationTimer = new AnimationTimer()
        {
            boolean firstUpdate = true;
            long lastNanoTime;

            public void handle(long currentNanoTime)
            {
                if (firstUpdate)
                {
                    lastNanoTime = currentNanoTime;
                    firstUpdate = false;
                }

                double secondsSinceLastUpdate = ((double)(currentNanoTime - lastNanoTime)) / (NANOSECONDS_TO_MILLISECONDS * MILLISECONDS_TO_SECONDS);
                lastNanoTime = currentNanoTime;

                double translateX = clientChar.getBody().getCenter().getX() - canvas.getWidth() / 2;
                double translateY = clientChar.getBody().getCenter().getY() - canvas.getHeight() / 2;
                TranslatedPoint.setTranslate(-translateX, -translateY);

                ImagePattern mapPattern = new ImagePattern(mapImage, mapPoint.getTranslatedX(), mapPoint.getTranslatedY(), mapImage.getWidth(),
                        mapImage.getHeight(), false);
                gc.setFill(mapPattern);
                gc.fillRect(0, 0, GUI.CANVAS_START_WIDTH, GUI.CANVAS_START_HEIGHT);

                for (GameObject go : gameObjects.values())
                {
                    if (go != clientChar)
                    {
                        go.update(secondsSinceLastUpdate, gc);
                    }
                }
                if (!clientChar.isDestroyed())
                {
                    clientChar.updateWithCollision(secondsSinceLastUpdate, gc, gameObjects);
                }

                ++frames; // fps is reliant on the refresh rate.
                canvas.requestFocus();
            }
        };
        animationTimer.start();

        updateThread = new Thread(() ->
        {
            long lastUpdate = System.nanoTime();

            while (mapActive)
            {
                long currentTime = System.nanoTime();
                long deltaTime = (currentTime - lastUpdate) / NANOSECONDS_TO_MILLISECONDS;
                lastUpdate = currentTime;

                Platform.runLater(() ->
                {
                    uiPanelEvent.update(deltaTime);
                });

                for (GameObject go : gameObjects.values())
                {
                    go.update(deltaTime);
                }

                sendUpdate();
                broker.update(deltaTime);

                try
                {
                    Thread.sleep(20);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        });
        updateThread.start();
    }

    /**
     * Deactivates the game loop.
     */
    public void deactivate()
    {
        mapActive = false;
        animationTimer.stop();
        updateThread.interrupt();
        broker.stop();
    }

    /**
     * Updates the position of game objects received from the server.
     * 
     * @param positions
     *            The position of the game objects.
     */
    public void updatePositions(List<GameObjectDAO> positions)
    {
        for (GameObjectDAO pos : positions)
        {
            GameObject go = gameObjects.get(pos.objectId);
            if (go == null)
            {
                continue;
            }
            if (pos.objectId != clientChar.getId())
            {
                go.getBody().setPosition(new TranslatedPoint(pos.x, pos.y));
            }
        }
    }

    /**
     * For every GameObject go in gameObjects, checks if go.iD() matches a key in the scoreMap. If it does, and the go is an instance of
     * BoDCharacter, then it calls the setScore method of the BoDCharacter and gives the value associated with the matching key as the
     * score.
     * 
     * @param scoreMap
     */
    public void updateScores(HashMap<Integer, Double> scoreMap)
    {
        for (Integer id : scoreMap.keySet())
        {

            GameObject go = gameObjects.get(id);

            if (go != null)
            {

                BoDCharacter bodCharacter = (BoDCharacter)go;
                double score = scoreMap.get(id);
                bodCharacter.setScore(score);
            }
        }
    }

    /**
     * Updates healths for all the game objects in the game that has health.
     * 
     * @param healths
     */
    public void updateHealths(List<GameObjectDAO> healths)
    {
        for (GameObjectDAO data : healths)
        {
            GameObject go = gameObjects.get(data.objectId);
            if (go != null && go.getHealth() != null)
            {
                go.getHealth().setValue(data.healthValue);
                go.getHealth().setMax(data.maxHealth);
            }
        }
    }

    /**
     * Adds a new gameObject to the game based on data from charData
     * 
     * @param data
     *            Data about the new object.
     */
    public void addGameObject(GameObjectDAO data)
    {
        if (data.objectId != clientChar.getId())
        {
            addGameObject(EntityFactory.getEntity(data, data.entityType));
        }

    }

    private void addGameObject(GameObject go)
    {
        if (go instanceof BoDCharacter)
        {
            BoDCharacter character = (BoDCharacter)go;
            leaderboard.addCharacter(character);
            
                    && !Resources.johnCena.isPlaying())
            {
                Resources.johnCena.setVolume(0.1);
                Resources.johnCena.play();
            }
        }
        gameObjects.put(go.getId(), go);
    }

    /**
     * Destroys a GameObject from the clientmap based on its ID.
     * 
     * @param id
     *            The id of the object to be removed.
     */
    public void destroyGameObject(int id)
    {
        GameObject go = gameObjects.get(id);
        if (go != null)
        {
            if (go instanceof BoDCharacter)
            {
                leaderboard.remove((BoDCharacter)go);
            }
            go.destroy();

            gameObjects.remove(id);

            go.unregisterAll(this);
            if (go.getWeapon() != null)
            {
                go.getWeapon().unregisterAll(this);
            }
        }
    }

    /**
     * Sends an update to the server which data such as the clients character position and active bullets.
     */

    public void sendUpdate()
    {
        List<GameObjectDAO> posList = new ArrayList<>();
        double x = clientChar.getBody().getPosition().getX();
        double y = clientChar.getBody().getPosition().getY();
        GameObjectDAO cData = new GameObjectDAO();
        cData.objectId = clientChar.getId();
        cData.x = x;
        cData.y = y;
        posList.add(cData);
        broker.sendUpdate(posList);
    }

    public void killNotification(int victimId, int killerId)
    {

        destroyGameObject(victimId);
        if (victimId == clientChar.getId())
        {
            System.out.println("YOU DIED");

        }
        else if (killerId == clientChar.getId())
        {
            System.out.println("You killed: " + victimId);
        }
        else
        {
            System.out.println(killerId + " pwned " + victimId + "'s head");
        }
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + mapHeight;
        result = prime * result + mapWidth;
        return result;
    }

    public void newBullet(Bullet bullet)
    {
        // unassignedBullets.add(bullet);
        GameObjectDAO data = new GameObjectDAO();
        data.x = bullet.getBody().getPosition().getX();
        data.y = bullet.getBody().getPosition().getY();
        data.height = bullet.getBody().getHeight();
        data.velocityX = bullet.getPhysics().getVelocity().getX();
        data.velocityY = bullet.getPhysics().getVelocity().getY();
        data.bulletType = bullet.getType();
        data.damage = bullet.getDamage();
        data.ownerId = bullet.getOwnerId();
        data.entityType = EntityFactory.EntityType.BULLET;
        broker.requestBulletCreation(data);
    }

    public void setCharacter(BoDCharacter character)
    {
        clientChar = character;
        clientChar.getBody().setRandomPosition(gameObjects.values(), 100, 100, mapWidth-200, mapHeight-200);
        
        
        addGameObject(clientChar);
        if (clientChar.getWeapon() != null)
        {
            clientChar.getWeapon().register(Observation.SPAWNING, this, (observable, data) -> newBullet((Bullet)data));
        }
    }

    public void setScale(double xFactor, double yFactor)
    {
        gc.setTransform(startingAffine);
        gc.scale(xFactor, yFactor);
    }
}

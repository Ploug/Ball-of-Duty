package application.engine.rendering;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO;

import application.communication.Broker;
import application.communication.GameObjectDAO;
import application.engine.entities.BoDCharacter;
import application.engine.entities.Bullet;
import application.engine.factories.EntityFactory;
import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.Weapon;
import application.gui.Leaderboard;
import application.util.Timer;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * The map is where all the game objects are being handled.
 * 
 * @author Gruppe6
 *
 */
public class ClientMap implements Observer
{

    private Broker broker;
    private GraphicsContext gc;
    private Canvas canvas;
    private int mapWidth = 1200;
    private int mapHeight = 700;
    public ConcurrentMap<Integer, GameObject> gameObjects;
    public Timer timer;
    private Label fpsLabel, scoreLabel, healthLabel;
    private VBox labelBox;
    private BoDCharacter clientChar;
    private Thread updateThread;
    private AnimationTimer animationTimer;
    private boolean mapActive = false;
    private int serverGameId;
    private Leaderboard leaderboard;
    private ConcurrentLinkedQueue<GameObject> unassignedBullets;

    /**
     * Creates a client map defining the serverMap its based upon, the gamebox
     * it should be drawn in, the broker it uses to communicate with the server
     * with and the character that belongs to the client.
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
    public ClientMap(GameDTO serverGame, BorderPane gameBox, Broker broker, BoDCharacter clientChar)
    {
        this.unassignedBullets = new ConcurrentLinkedQueue<>();
        this.serverGameId = serverGame.getGameId();
        this.clientChar = clientChar;
        this.clientChar.addObserver(this);
        this.leaderboard = new Leaderboard();
        if (clientChar.getWeapon() != null)
        {
            clientChar.getWeapon().addObserver(this);
        }
        mapActive = true;
        gameObjects = new ConcurrentHashMap<>();
        addGameObject(clientChar);

        this.broker = broker;
        broker.activate(this);

        timer = new Timer();
        timer.start();

        for (GameObjectDTO dto : serverGame.getGameObjects())
        {

            if (dto.getBody().getType() == Body.Geometry.CIRCLE.ordinal())
            {
                if (dto.getId() != clientChar.getId())
                {
                    addGameObject(EntityFactory.getEntity(dto, EntityFactory.EntityType.ENEMY_CHARACTER));

                }
            }
            else if (dto.getBody().getType() == Body.Geometry.RECTANGLE.ordinal())
            {
                addGameObject(EntityFactory.getEntity(dto, EntityFactory.EntityType.WALL));
            }

        }

        for (PlayerDTO pdto : serverGame.getPlayers())
        {
            BoDCharacter character = (BoDCharacter)gameObjects.get(pdto.getCharacterId());
            if (character == null)
            {
                continue;
            }
            character.setNickname(pdto.getNickname());
            leaderboard.addCharacter(character);
        }
        gameBox.setRight(leaderboard);

        fpsLabel = new Label();
        fpsLabel.setPrefSize(50, 20);
        fpsLabel.setText("fps: ");
        scoreLabel = new Label();
        scoreLabel.setPrefSize(70, 20);
        scoreLabel.setText("Score: ");
        healthLabel = new Label();
        healthLabel.setPrefSize(80, 20);
        healthLabel.setText("Health: ");

        labelBox = new VBox();
        labelBox.setSpacing(1);
        gameBox.setLeft(labelBox);

        labelBox.getChildren().add(fpsLabel);
        labelBox.getChildren().add(scoreLabel);
        labelBox.getChildren().add(healthLabel);

        this.canvas = (Canvas)gameBox.getCenter();
        gc = canvas.getGraphicsContext2D();
    }

    /**
     * Activates the game loop.
     */
    public void activate()
    {
        Image mapImage = new Image("images/map_field.png");
        animationTimer = new AnimationTimer()
        {
            int frames = 0;

            public void handle(long currentNanoTime)
            {
                gc.drawImage(mapImage, 0, 0, mapWidth, mapHeight);
                for (GameObject go : gameObjects.values())
                {
                    if (go != clientChar)
                    {
                        go.update(gc);
                    }
                }
                if (!clientChar.isDestroyed())
                {
                    clientChar.updateWithCollision(gc, gameObjects);
                }

                if (timer.getDuration() > 250)
                {
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

                    timer.reset();
                    frames = 0;
                }
                else
                {
                    frames++;
                }
                canvas.requestFocus();

            }
        };
        animationTimer.start();

        updateThread = new Thread(() ->
        {
            while (mapActive)
            {
                sendUpdate();
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

        if (positions.size() < gameObjects.values().size())
        {
            boolean isInGame = false;
            for (GameObject go : gameObjects.values())
            {
                if (go.getBody().getType() == Body.Geometry.RECTANGLE)
                {
                    continue;
                }
                isInGame = false;
                for (GameObjectDAO pos : positions)
                {
                    if (go.getId() == pos.objectId || go instanceof Bullet)
                    {
                        isInGame = true;
                        break;
                    }

                }
                if (!isInGame)
                {
                    if (go.getId() != clientChar.getId())
                    {
                        destroyGameObject(go.getId());
                    }
                    break;
                }

            }
        }
        for (GameObjectDAO pos : positions)
        {
            GameObject go = gameObjects.get(pos.objectId);
            if (go == null)
            {
                continue;
            }
            if (pos.objectId != clientChar.getId())
            {
                go.getBody().setPosition(new Point2D(pos.x, pos.y));
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
        if (data.entityType != null)
        {
            if (data.ownerId == clientChar.getId())
            {
                Bullet bullet = (Bullet)unassignedBullets.poll();
                bullet.setId(data.objectId);
                addGameObject(bullet);
                return;
            }

            if (data.objectId != clientChar.getId())
            {
                addGameObject(EntityFactory.getEntity(data, data.entityType));
            }
        }
    }

    private void addGameObject(GameObject go)
    {
        if (go instanceof BoDCharacter)
        {
            leaderboard.addCharacter((BoDCharacter)go);
        }
        gameObjects.put(go.getId(), go);
        go.addObserver(this);
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
            go.destroy();
            if (go instanceof BoDCharacter)
            {
                leaderboard.remove((BoDCharacter)go);
            }
            gameObjects.remove(id);
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
            System.out.println("YOU DIED MOTAFUCASPKDSAD FUCKA");
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

    @Override
    public void update(Observable o, Object arg)
    {
        if (o instanceof Weapon) // Spawned a bullet.
        {
            Bullet bullet = (Bullet)arg;
            unassignedBullets.add(bullet);
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
        else if (o instanceof Bullet)
        {
            Bullet bullet = (Bullet)o;
            gameObjects.remove(bullet.getId());
            clientChar.getWeapon().getActiveBullets().remove(bullet.getId());
            o.deleteObserver(this);
            // TODO Server should handle if a bullet have been active for too long, not client.

        }
        else if (o instanceof GameObject)
        {
            GameObject go = (GameObject)o;

            System.out.println("Game object destroyed: " + go.getId());
            gameObjects.remove(go.getId());
            o.deleteObserver(this);
        }
    }
}

package application.engine.rendering;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import application.communication.Broker;
import application.communication.GameObjectDAO;
import application.engine.entities.Bullet;
import application.engine.factories.EntityFactory;
import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.Weapon;
import application.util.Timer;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

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
    Label fpsLabel;
    private GameObject clientChar;
    private Thread updateThread;
    private AnimationTimer animationTimer;
    private boolean mapActive = false;
    private int serverGameId;
    private ConcurrentLinkedQueue<GameObject> unassignedBullets;

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
    public ClientMap(GameDTO serverGame, BorderPane gameBox, Broker broker, GameObject clientChar)
    {
        this.unassignedBullets = new ConcurrentLinkedQueue<>();
        this.serverGameId = serverGame.getGameId();
        this.clientChar = clientChar;
        if (clientChar.getWeapon() != null)
        {
            clientChar.getWeapon().addObserver(this);
            System.out.println(clientChar.getWeapon().countObservers());
        }
        mapActive = true;
        gameObjects = new ConcurrentHashMap<>();
        gameObjects.put(clientChar.getId(), clientChar);

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
                    GameObject enemy = EntityFactory.getEntity(dto, EntityFactory.EntityType.ENEMY_CHARACTER);
                    gameObjects.put(dto.getId(), enemy);

                }
            }
            else if (dto.getBody().getType() == Body.Geometry.RECTANGLE.ordinal())
            {
                GameObject wall = EntityFactory.getEntity(dto, EntityFactory.EntityType.WALL);
                gameObjects.put(dto.getId(), wall);
            }


        }
        fpsLabel = new Label();
        fpsLabel.setPrefSize(50, 20);
        gameBox.setLeft(fpsLabel);
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

                clientChar.updateWithCollision(gc, gameObjects);

                if (timer.getDuration() > 1000)
                {
                    fpsLabel.setText("fps: " + frames);
                    timer.reset();
                    frames = 0;
                }
                else
                {
                    frames++;
                }

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
                        gameObjects.remove(go.getId());
                    }
                    break;
                }

            }
        }
        for (GameObjectDAO pos : positions)
        {
            GameObject go = gameObjects.get(pos.objectId);

            if (go != null)
            {

                // System.out.println(pos.getId() +" "+go.getId());
            }
            else
            {
                // System.out.println("its null");
            }

            if (pos.objectId != clientChar.getId())
            {
                if (go == null)
                {
                    continue;
                    // go = EntityFactory.getDefaultEntity(pos.objectId, EntityFactory.EntityType.ENEMY_CHARACTER); // TODO nees to be done
                    // // TCP,
                    // // this is bad.
                    // gameObjects.put(go.getId(), go);
                }

                go.getBody().setPosition(new Point2D(pos.x, pos.y));
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

        if(data.entityType != null)
        {
            if (data.ownerId == clientChar.getId()) 
            {
                gameObjects.put(data.objectId, unassignedBullets.poll());
                return;
            }
            
            gameObjects.put(data.objectId, EntityFactory.getEntity(data, data.entityType));
        }
    }

    /**
     * Removes a GameObject from the clientmap based on its ID.
     * 
     * @param id
     *            The id of the object to be removed.
     */
    public void removeGameObject(int id)
    {
        gameObjects.remove(id);
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
            // TODO Server should handle if a bullet have been active for too long, not client.

        }

    }

}

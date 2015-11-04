package application.engine.rendering;

import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.MapDTO;

import application.communication.Broker;
import application.communication.ObjectPosition;
import application.engine.entities.Bullet;
import application.engine.factories.EntityFactory;
import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.Weapon;
import application.util.Timer;
import javafx.animation.AnimationTimer;
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

    /**
     * Creates a client map defining the serverMap its based upon, the gamebox it should be drawn in, the broker it uses to communicate
     * with the server with and the character that belongs to the client.
     * 
     * @param serverMap The server map which the ClientMap is based upon.
     * @param gameBox The game box in whic the ClientMap is drawn.
     * @param broker The broker the ClientMap uses to communicate with the server.
     * @param clientChar The character of the client.
     */
    public ClientMap(MapDTO serverMap, BorderPane gameBox, Broker broker, GameObject clientChar)
    {
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

        for (GameObjectDTO dto : serverMap.getGameObjects())
        {

            if (dto.getBody().get_type() == dto.getBody().getCIRCLE())
            {
                if (dto.getId() != clientChar.getId())
                {
                    GameObject enemy = EntityFactory.getEntity(dto, EntityFactory.EntityType.ENEMY_CHARACTER);
                    gameObjects.put(dto.getId(), enemy);

                }
            }
            else if (dto.getBody().get_type() == dto.getBody().getRECTANGLE())
            {
                GameObject wall = EntityFactory.getEntity(dto, EntityFactory.EntityType.WALL);
                gameObjects.put(dto.getId(), wall);
            }

            System.out.println("id of object received: " + dto.getId() + " my id = " + clientChar.getId());

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
     * @param positions The position of the game objects.
     */
    public void updatePositions(List<ObjectPosition> positions)
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
                for (ObjectPosition pos : positions)
                {
                    if (go.getId() == pos.Id || go instanceof Bullet)
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
        for (ObjectPosition pos : positions)
        {
            GameObject go = gameObjects.get(pos.Id);

            if (go != null)
            {

                // System.out.println(pos.getId() +" "+go.getId());
            }
            else
            {
                // System.out.println("its null");
            }

            if (pos.Id != clientChar.getId())
            {
                if (go == null)
                {
                    go = EntityFactory.getDefaultEntity(pos.Id, EntityFactory.EntityType.ENEMY_CHARACTER); // TODO nees to be done TCP,
                                                                                                                // this is bad.
                    gameObjects.put(go.getId(), go);
                }

                go.getBody().setPosition(pos.Position);
            }
        }

    }

    /**
     * Sends an update to the server which data such as the clients character position and active bullets.
     */
    
    public void sendUpdate()
    {

        broker.sendUpdate(clientChar.getBody().getPosition(), clientChar.getId(), clientChar.getWeapon().getActiveBullets());

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
            if (arg instanceof Bullet)
            {

                Bullet bullet = (Bullet)arg;
                bullet.addObserver(this);
                gameObjects.put(bullet.getId(), bullet);

            }

            // Shooting occured
        }
        else if (o instanceof Bullet)
        {

            Bullet bullet = (Bullet)o;
            gameObjects.remove(bullet.getId());

        }

    }

}

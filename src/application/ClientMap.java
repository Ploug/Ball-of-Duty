package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.MapDTO;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class ClientMap implements Observer
{

    private Broker broker;
    private GraphicsContext gc;
    private Canvas canvas;
    private int mapWidth = 1200;
    private int mapHeight = 700;
    public HashMap<Integer, GameObject> gameObjects;
    public HashMap<Integer, GameObject> noneCollidable;
    public HashMap<String, Image> images;
    public Timer timer;
    Label fpsLabel;
    private BoDCharacter clientChar;
    private Thread updateThread;
    private AnimationTimer animationTimer;
    private boolean mapActive = false;

    public ClientMap(MapDTO serverMap, BorderPane gameBox, Broker broker, BoDCharacter clientChar)
    {
        this.clientChar = clientChar;
        if (clientChar.weapon != null)
        {
            clientChar.weapon.registerObserver(this);
        }
        mapActive = true;
        gameObjects = new HashMap<>();
        noneCollidable = new HashMap<>();
        gameObjects.put(clientChar.getId(), clientChar);

        this.broker = broker;
        broker.activate(this);

        timer = new Timer();
        timer.start();

        for (GameObjectDTO sgo : serverMap.getGameObjects())
        {
            synchronized (gameObjects)
            {
                if (sgo.getBody().get_type() == sgo.getBody().getCIRCLE())
                {
                    if (sgo.getId() != clientChar.getId())
                    {
                        BoDCharacter character = new BoDCharacter(sgo);
                        gameObjects.put(sgo.getId(), character);

                    }
                }
                else if (sgo.getBody().get_type() == sgo.getBody().getRECTANGLE())
                {
                    Wall wall = new Wall(sgo);
                    gameObjects.put(sgo.getId(), wall);
                }
            }

            System.out.println("id of object received: " + sgo.getId() + " my id = " + clientChar.getId());

        }
        fpsLabel = new Label();
        fpsLabel.setPrefSize(50, 20);
        gameBox.setLeft(fpsLabel);
        this.canvas = (Canvas) gameBox.getCenter();
        gc = canvas.getGraphicsContext2D();
        images = new HashMap<>();
        images.put("map_field", new Image("images/map_field.png"));
        images.put("ball_red", new Image("images/ball_red.png"));
        images.put("ball_blue", new Image("images/ball_blue.png"));
        images.put("wall_box", new Image("images/wall_box.png"));
    }

    public int getPort()
    {
        return broker.getPort();
    }

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
                        if (go instanceof Wall)
                        {
                            go.update(gc, images.get("wall_box")); // Skal gøres når view bliver created, ikke her.
                        }
                        else if (go instanceof BoDCharacter)
                        {
                            go.update(gc, images.get("ball_red"));
                        }

                    }
                }
                synchronized (noneCollidable)
                {
                    GameObject destroy = null;
                    for (GameObject go : noneCollidable.values())
                    {
                        if (go instanceof Bullet)
                        {
                            go.update(gc, images.get("ball_red"));
                            if (((Bullet) go).livedTooLong())
                            {
                                destroy = go;
                               //remove this object
                            }
                        }
                    }
                    if(destroy != null)
                    {
                        noneCollidable.remove(destroy); // Not the right way.
                    }
                   

                }

                clientChar.update(gc, gameObjects, images.get("ball_blue"));

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
                sendPositionUpdate();

            }
        });
        updateThread.start();
    }

    public void deactivate()
    {
        mapActive = false;
        animationTimer.stop();
        updateThread.interrupt();
        broker.stop();
    }

    public void updatePositions(List<ObjectPosition> positions)
    {

        synchronized (gameObjects)
        {

            if (positions.size() < gameObjects.values().size())
            {
                boolean isInGame = false;
                for (GameObject go : gameObjects.values())
                {
                    if (go.getBody().getType() == Body.Type.RECTANGLE)
                    {
                        continue;
                    }
                    isInGame = false;
                    for (ObjectPosition pos : positions)
                    {
                        if (go.getId() == pos.getId())
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
                GameObject go = gameObjects.get(pos.getId());

                if (go != null)
                {

                    // System.out.println(pos.getId() +" "+go.getId());
                }
                else
                {
                    // System.out.println("its null");
                }

                if (pos.getId() != clientChar.getId())
                {
                    if (go == null)
                    {
                        go = new BoDCharacter(pos.getId()); // We need to talk how
                                                            // to handle different
                                                            // objects over network.
                        gameObjects.put(go.getId(), go);
                    }

                    go.getBody().setPosition(pos.getPosition());
                }
            }
        }
    }

    public void sendPositionUpdate()
    {
        try
        {
            broker.sendPositionUpdate(clientChar.getBody().getPosition(), clientChar.getId());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void update(Observable observable, Object args)
    {
        if (observable instanceof Weapon)
        {
            if (args instanceof Bullet)
            {
                synchronized (noneCollidable)
                {
                    Bullet bullet = (Bullet) args;
                    noneCollidable.put(bullet.getId(), bullet);
                }

            }

            // Shooting occured
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

}

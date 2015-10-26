package application;

import java.io.IOException;
import java.util.ArrayList;
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

public class ClientMap
{

    private Broker broker;
    private GraphicsContext gc;
    private Canvas canvas;
    private int mapWidth = 1280;
    private int mapHeight = 720;
    public HashMap<Integer, GameObject> gameObjects;
    public ArrayList<Wall> walls;
    public HashMap<String, Image> images;
    public Timer timer;
    Label fpsLabel;
    private BoDCharacter clientChar;
    private Thread updateThread;
    private AnimationTimer animationTimer;
    private boolean mapActive = false;

    public ClientMap(MapDTO serverMap, BorderPane gameBox, BoDCharacter clientChar)
    {
        this.clientChar = clientChar;
        mapActive = true;
        gameObjects = new HashMap<>();
        gameObjects.put(clientChar.getId(), clientChar);
        walls = new ArrayList<>();

        this.broker = new Broker(this, serverMap.getIPAddress());
        timer = new Timer();
        timer.start();

        for (GameObjectDTO sgo : serverMap.getGameObjects())
        {
            if (sgo.getId() != clientChar.getId())
            {
                gameObjects.put(sgo.getId(), new BoDCharacter(sgo));
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
                        go.update(gc, images.get("ball_red"));
                    }
                }

                
                clientChar.update(gc, gameObjects, walls, images.get("ball_blue"));

//                System.out.println(MouseInfo.getPointerInfo().getLocation().getX()
//                        - (GUI.stage.getX() + GUI.stage.getScene().getX() + canvas.getLayoutX()));
//                System.out.println(MouseInfo.getPointerInfo().getLocation().getY()
//                        - (GUI.stage.getY() + GUI.stage.getScene().getY() + canvas.getLayoutY())); // gets mouse position even out of window. GUI.stage is not the proper way to do this.

                for (Wall wall : walls)
                {
                    wall.update(gc, images.get("wall_box"));
                }
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

        if (positions.size() < gameObjects.values().size())
        {
            boolean isInGame = false;
            for (GameObject go : gameObjects.values())
            {
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
                    go = new BoDCharacter(pos.getId()); // We need to talk how to handle different objects over network.
                    gameObjects.put(go.getId(), go);
                }

                go.getBody().setPosition(pos.getPosition());
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
}

package application;

import java.awt.Dimension;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class ClientMap
{

    private Broker broker;
    private long fpsLock = 120;
    private GraphicsContext gc;
    private Canvas canvas;
    private Dimension mapSize;
    public HashMap<Integer, GameObject> gameObjects;
    public BoDCharacter myChar;
    public Timer timer;
    Label fpsLabel;

    public ClientMap(IMap serverMap, BorderPane gameBox)
    {

        gameObjects = new HashMap<>();

        this.broker = new Broker(this);
        timer = new Timer();
        timer.start();

        if (serverMap.getCharacters() != null)
        {
            myChar = (BoDCharacter)serverMap.getCharacters().get(0);
            List<GameObject> characters = serverMap.getCharacters();
            for (GameObject character : characters)
            {
                gameObjects.put(character.getID(), character);
            }
        }
        mapSize = new Dimension(700, 500);
        fpsLabel = new Label();
        gameBox.setLeft(fpsLabel);
        this.canvas = (Canvas)gameBox.getCenter();
        gc = canvas.getGraphicsContext2D();
    }

    public void activate()
    {
        Image space = new Image("images/space.png");
        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                gc.drawImage(space, 0, 0, 600, 500);
                for (GameObject character : gameObjects.values())
                {
                    character.update(gc);
                }

                fpsLabel.setText("" + (int)(1000 / timer.getDuration()));
                timer.reset();
            }
        }.start();

        new Thread(() ->
        {
            while (true)
            {
                sendPositionUpdate();
                try
                {
                    Thread.sleep(10);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void deactivate()
    {

    }

    public void updatePositions(List<ObjectPosition> positions)
    {
        for (ObjectPosition pos : positions)
        {
            GameObject go = gameObjects.get(pos.getId());
            if (go != null && go.getID() != myChar.getID())
            {
                System.out.println(go.getID() + " client id = " + myChar.getID());
                go.getBody().setPosition(pos.getPosition());
            }
        }
    }

    public void sendPositionUpdate()
    {
        try
        {
            broker.sendPositionUpdate(myChar.getBody().getPosition(), myChar.getID());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

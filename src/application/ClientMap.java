package application;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain.ServerGameObject;

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
    public Timer timer;
    Label fpsLabel;
    private BoDCharacter clientChar;

    public ClientMap(ServerGameObject[] serverGameObjects, BorderPane gameBox, BoDCharacter clientChar)
    {
        this.clientChar = clientChar;

        gameObjects = new HashMap<>();

        this.broker = new Broker(this);
        timer = new Timer();
        timer.start();
        gameObjects.put(clientChar.getId(), clientChar);
        if (serverGameObjects != null)
        {
            for (ServerGameObject sgo : serverGameObjects)
            {
                if (sgo.getId() != clientChar.getId())
                {
                    gameObjects.put(sgo.getId(), new BoDCharacter(sgo));
                }

            }
        }
        mapSize = new Dimension(700, 500);
        fpsLabel = new Label();
        gameBox.setLeft(fpsLabel);
        this.canvas = (Canvas) gameBox.getCenter();
        gc = canvas.getGraphicsContext2D();
    }

    public void activate()
    {
        Image space = new Image("images/space.png");
        new AnimationTimer()
        {
            int frames = 0;

            public void handle(long currentNanoTime)
            {
                gc.drawImage(space, 0, 0, 600, 500);
                for (GameObject character : gameObjects.values())
                {
                    character.update(gc);
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
        }.start();

        new Thread(() ->
        {
            while (true)
            {
                sendPositionUpdate();
               
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
            if (go != null && go.getId() != clientChar.getId())
            {
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

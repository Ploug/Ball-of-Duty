package application;

import java.awt.Dimension;
import java.util.List;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.io.IOException;
import java.util.HashMap;
import java.util.Observable;

import javax.swing.JComponent;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class ClientMap
{
    public List<GameObject> characters;

    private Broker broker;
    private long fpsLock = 120;
    private GraphicsContext gc;
    private Canvas canvas;
    private Dimension mapSize;
	public HashMap<Integer, GameObject> gameObjects;
    public BoDCharacter myChar;

    public ClientMap(IMap serverMap, Broker broker, BorderPane gameBox)
    {
        this.broker = broker;
        if (serverMap.getCharacters() != null)
        {
            this.characters = serverMap.getCharacters();
        }
        mapSize = new Dimension(700, 500);
        setPreferredSize(mapSize);
      
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
                for(GameObject character : characters)
                {
                    character.update(gc);
                }
            }
        }.start();
    }


    public void deactivate()
    {

    }
	
	public void updatePositions(List<ObjectPosition> positions)
    {
    	for (ObjectPosition pos : positions) 
    	{
    		GameObject go = gameObjects.get(pos.getId());
    		if (go != null) 
    		{
    			go.getBody().setPosition(pos.getPosition());
    		}
    	}
    }
     
    public void sendPositionUpdate()
	{
    	try
        {
            broker.sendPositionUpdate(myChar.getBody().getPosition(), myChar.getId());
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}

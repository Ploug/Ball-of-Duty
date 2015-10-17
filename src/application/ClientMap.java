package application;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComponent;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;

public class ClientMap extends JComponent
{
    public List<GameObject> characters;

    private Broker broker;
    private long fpsLock = 120;
    private GraphicsContext gc;
    private Canvas canvas;
    private Dimension mapSize;

    public ClientMap(IMap serverMap, Broker broker, BorderPane gameBox)
    {
        this.broker = broker;
        if (serverMap.getCharacters() != null)
        {
            this.characters = serverMap.getCharacters();
        }
        mapSize = new Dimension(700, 500);
        setPreferredSize(mapSize);
      
        this.canvas = (Canvas) gameBox.getCenter();
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
}

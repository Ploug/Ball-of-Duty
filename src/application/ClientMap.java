package application;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;

import javax.swing.JComponent;

public class ClientMap extends JComponent
{
    public List<GameObject> characters;

    private Broker broker;
    private Timer timer;
    private long fpsLock = 120;
    private Graphics2D g2d;
    private Dimension mapSize;

    public ClientMap(IMap serverMap, Broker broker)
    {
        timer = new Timer();
        this.broker = broker;
        if (serverMap.getCharacters() != null)
        {
            this.characters = serverMap.getCharacters();
        }
        mapSize = new Dimension(700, 500);
        setPreferredSize(mapSize);

    }

    public void paint(Graphics g)
    {
        g2d = (Graphics2D) g;
        timer.catchupSleep(1000 / fpsLock);
        for (GameObject gameChar : characters)
        {
            gameChar.update(g2d);
        }
        repaint();
    }

    public void deactivate()
    {

    }
}

package application;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class View
{
    public GameObject gameObject;
    private Graphics2D g2d;
    public View(GameObject gO, Graphics2D g2d)
    {
        this.g2d = g2d;
        this.gameObject = gO;
    }
    
    public void draw()
    {
        
    }
}
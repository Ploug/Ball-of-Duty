package application;

import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.Color;

public class View
{
    public GameObject gameObject;

    public View(GameObject gO)
    {
        this.gameObject = gO;
    }

    public void draw(Graphics2D g2d)
    {
        Color oldColor = g2d.getColor();
        g2d.setColor(Color.green);
        System.out.println((int) gameObject.getBody().getX()+"  "+ (int) gameObject.getBody().getY());
        g2d.fillOval((int) gameObject.getBody().getX(), (int) gameObject.getBody().getY(), 40, 40);
        g2d.setColor(oldColor);
    }
}
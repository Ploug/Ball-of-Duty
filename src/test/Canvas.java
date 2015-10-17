package test;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Canvas extends JComponent
{
    private Graphics2D g2d;

    public void paint(Graphics g)
    {
        g2d = (Graphics2D) g;
        g2d.drawOval(20, 20, 50, 40);
        
    }

    public Graphics2D getGraphics()
    {
        return g2d;
    }
}

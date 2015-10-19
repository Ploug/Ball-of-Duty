package application;

import java.awt.geom.Point2D;

public class Body
{
    public GameObject gameObject;
    private Point2D.Double position;

    public Body(GameObject gO)
    {
    	position = new Point2D.Double(10, 10);
        this.gameObject = gO;
    }
    
    public void collidesWith(GameObject gO)
    {
        
    }
    
    public Point2D.Double getPosition()
    {
    	return position;
    }
    public void increasePosition(double xIncrease, double yIncrease)
    {
    	position.setLocation(position.getX()+xIncrease, position.getY()+yIncrease);
    }
    public void setPosition(Point2D.Double position)
    {
    	this.position = position;
    }
}

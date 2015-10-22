package application;

import java.awt.geom.Point2D;

public class Body
{
    public GameObject gameObject;
    private Point2D.Double position;
    private double orientation;

    public Body(GameObject gO, Point2D.Double position)
    {
        this.position = position;
        this.gameObject = gO;
        this.orientation = 0;
    }

    public void collidesWith(GameObject gO)
    {

    }

    public Point2D.Double getPosition()
    {
        return position;
    }

    public double getOrientation()
    {
        return orientation;
    }

   
    public void setOrientation(double orientation)
    {
        this.orientation = orientation;
    }

    public void increasePosition(double xIncrease, double yIncrease)
    {
        position.setLocation(position.getX() + xIncrease, position.getY() + yIncrease);
    }

    public void setPosition(Point2D.Double position)
    {
        this.position = position;
    }

   

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Body other = (Body) obj;
        if (Double.doubleToLongBits(orientation) != Double.doubleToLongBits(other.orientation)) return false;
        if (position == null)
        {
            if (other.position != null) return false;
        }
        else if (!position.equals(other.position)) return false;
        return true;
    }
    

}

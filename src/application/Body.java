package application;

import java.awt.geom.Point2D;

import application.Body.Type;

public class Body
{
    public GameObject gameObject;
    private Point2D.Double position;
    private Point2D.Double center;
    private double length;
    private double width;
    private double orientation;
    private Type type;
    public enum Type
    {
        CIRCLE, RECTANGLE
    }

    public Body(GameObject gO, Point2D.Double position, double length, double width, Type type)
    {
        this.type = type;
        this.gameObject = gO;
    	this.position = position;
        this.length = length;
        this.width = width;
        this.orientation = 0;
    }

    public Point2D.Double getPosition()
    {
        return position;
    }

    public void setPosition(Point2D.Double position)
    {
        this.position = position;
    }
    
    public Point2D.Double getCenter()
    {
        center = new Point2D.Double((position.getX() + (width / 2)), position.getY() + (length / 2));
        return center;
    }

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        this.length = length;
    }
    
    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
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

    public Type getType()
    {
        return this.type;
    }
    public void setType(Type type)
    {
        this.type = type;
        
    }
    

}

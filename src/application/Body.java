package application;


import javafx.geometry.Point2D;

public class Body
{
    public GameObject gameObject;
    private Point2D position;
    private double height;
    private double width;
    private Vector2 orientation;
    private Type type;
    public enum Type
    {
        CIRCLE, RECTANGLE
    }

    public Body(GameObject gO, Point2D position, double length, double width, Type type)
    {
        this.type = type;
        this.gameObject = gO;
    	this.position = position;
        this.height = length;
        this.width = width;
        this.orientation = new Vector2(0,0);
    }

    public Point2D getPosition()
    {
        return position;
    }

    public void setCenter(Point2D position)
    {
        this.position = new Point2D(position.getX()-width/2,position.getY()-height/2);
    }
    public void setPosition(Point2D position)
    {
        this.position = position;
    }
    
    public Point2D getCenter()
    {
        return new Point2D((position.getX() + (width / 2)), position.getY() + (height / 2));
    }

    public double getHeight()
    {
        return height;
    }

    public void setHeight(double length)
    {
        this.height = length;
    }
    
    public double getWidth()
    {
        return width;
    }

    public void setWidth(double width)
    {
        this.width = width;
    }

    public Vector2 getOrientation()
    {
        return orientation;
    }

   
    public void setOrientation(double x, double y)
    {
        this.orientation.setX(x);;
        this.orientation.setY(y);;
    }

    public void increasePosition(double xIncrease, double yIncrease)
    {
        position = new Point2D(position.getX() + xIncrease, position.getY() + yIncrease);
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

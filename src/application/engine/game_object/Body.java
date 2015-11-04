package application.engine.game_object;

import application.util.Vector2;
import javafx.geometry.Point2D;

/**
 * The body describes an objects dimensional properties, such as height, width and position. Without a body an object cannot be on a map.
 * @author Gruppe6
 *
 */
public class Body 
{
    public GameObject gameObject;
    private Point2D position;
    private double height;
    private double width;
    private Vector2 orientation;
    private Geometry type;

    public enum Geometry
    {
        CIRCLE, RECTANGLE; 
        private static final Geometry[] types = Geometry.values();
        public static Geometry getType(int index)
        {
            return types[index];
        }
    }

    /**
     *  Creates a body for the object.
     * @param gameObject The game object that this body is a composition of/belongs to.
     * @param position The position of the game objects body on a map.
     * @param width The width of the game objects body on a map.
     * @param height The height of the game objects body on a map.
     * @param type The type of body, i.e. rectangular or circle etc.
     */
    public Body(GameObject gameObject, Point2D position, double width, double height, Geometry type)
    {
        this.type = type;
        this.gameObject = gameObject;
        this.position = position;
        this.width = width;
        this.height = height;
        this.orientation = new Vector2(0, 0);
    }

    /**
     * The position of the top left corner of this body.
     * @return Returns the position of the top left corner of this body.
     */
    public Point2D getPosition()
    {
        return position;
    }

    /**
     * Sets the center position of this body.
     * @param position The new center position of this body.
     */
    public void setCenter(Point2D position)
    {
        this.position = new Point2D(position.getX() - width / 2, position.getY() - height / 2);
    }

    /**
     * Sets the top left corner position of this body.
     * @param position The new top left corner position of this body.
     */
    public void setPosition(Point2D position)
    {
        this.position = position;
    }

    /**
     * The center position of this body.
     * @return Returns the current center position of this body.
     */
    public Point2D getCenter()
    {
        return new Point2D((position.getX() + (width / 2)), position.getY() + (height / 2));
    }

    /**
     * The height of this body.
     * @return Returns the current height of this body.
     */
    public double getHeight()
    {
        return height;
    }

    /**
     * Sets the height of this body
     * @param height The new height of this body. 
     */
    public void setHeight(double height)
    {
        this.height = height;
    }

    /**
     * The width of this body.
     * @return Returns the current width of this body.
     */
    public double getWidth()
    {
        return width;
    }
    
    /**
     * Sets the width of this body.
     * @param width The new width of this body.
     */
    public void setWidth(double width)
    {
        this.width = width;
    }

    /**
     * The orientation of this body described as a vector.
     * @return Returns the orientation of this body as a vector.
     */
    public Vector2 getOrientation()
    {
        return orientation;
    }

    /**
     * Sets the orientation of this body as a vector.
     * @param orientation The new vector to describe the orientation of this boy.
     */
    public void setOrientation(Vector2 orientation)
    {
        this.orientation = orientation;
    }

    /**
     * Increases the current position of this body.
     * @param xIncrease The amount to add to the current x value of the body.
     * @param yIncrease The amount to add to the current y value of the body.
     */
    public void increasePosition(double xIncrease, double yIncrease)
    {
        position = new Point2D(position.getX() + xIncrease, position.getY() + yIncrease);
    }

    /**
     * The bodytype. I.e circle or rectangle.
     * @return Returns the bodytype. I.e circle or rectangle
     */
    public Geometry getType()
    {
        return this.type;
    }

    /**
     * Change the bodytype. I.e circle of rectangle.
     * @param type The new bodytype. I.e circle or rectangle.
     */
    public void setType(Geometry type)
    {
        this.type = type;

    }

    @Override
    public String toString()
    {
        return String.format("Body [position=%s, height=%s, width=%s, orientation=%s, type=%s]", position, height, width, orientation, type);
    }

   
    

}

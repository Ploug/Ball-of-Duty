package application.engine.game_object;

import application.engine.rendering.TranslatedPoint;
import application.util.Vector2;

/**
 * The body describes an objects dimensional properties, such as height, width and position. Without a body an object cannot be on a map.
 * @author Gruppe6
 *
 */
public class Body 
{
    private GameObject _gameObject;
    private TranslatedPoint _position;
    private double _height;
    private double _width;
    private Vector2 _orientation;
    private Geometry _type;

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
    public Body(GameObject gameObject, TranslatedPoint position, double width, double height, Geometry type)
    {
        this._type = type;
        this._gameObject = gameObject;
        this._position = position;
        this._width = width;
        this._height = height;
        this._orientation = new Vector2(0, 0);
    }

    /**
     * The position of the top left corner of this body.
     * @return Returns the position of the top left corner of this body.
     */
    public TranslatedPoint getPosition()
    {
        return _position;
    }

    /**
     * Sets the center position of this body.
     * @param position The new center position of this body.
     */
    public void setCenter(TranslatedPoint position)
    {
        this._position = new TranslatedPoint(position.getX() - _width / 2, position.getY() - _height / 2);
    }

    /**
     * Sets the top left corner position of this body.
     * @param position The new top left corner position of this body.
     */
    public void setPosition(TranslatedPoint position)
    {
        this._position = position;
    }

    /**
     * The center position of this body.
     * @return Returns the current center position of this body.
     */
    public TranslatedPoint getCenter()
    {
        return new TranslatedPoint((_position.getX() + (_width / 2)), _position.getY() + (_height / 2));
    }

    /**
     * The height of this body.
     * @return Returns the current height of this body.
     */
    public double getHeight()
    {
        return _height;
    }

    /**
     * Sets the height of this body
     * @param height The new height of this body. 
     */
    public void setHeight(double height)
    {
        this._height = height;
    }

    /**
     * The width of this body.
     * @return Returns the current width of this body.
     */
    public double getWidth()
    {
        return _width;
    }
    
    /**
     * Sets the width of this body.
     * @param width The new width of this body.
     */
    public void setWidth(double width)
    {
        this._width = width;
    }

    /**
     * The orientation of this body described as a vector.
     * @return Returns the orientation of this body as a vector.
     */
    public Vector2 getOrientation()
    {
        return _orientation.setMagnitude(30);
    }

    /**
     * Sets the orientation of this body as a vector.
     * @param orientation The new vector to describe the orientation of this boy.
     */
    public void setOrientation(Vector2 orientation)
    {
        this._orientation = orientation;
    }

    /**
     * Increases the current position of this body.
     * @param xIncrease The amount to add to the current x value of the body.
     * @param yIncrease The amount to add to the current y value of the body.
     */
    public void increasePosition(double xIncrease, double yIncrease)
    {
        _position = new TranslatedPoint(_position.getX() + xIncrease, _position.getY() + yIncrease);
    }

    /**
     * The bodytype. I.e circle or rectangle.
     * @return Returns the bodytype. I.e circle or rectangle
     */
    public Geometry getType()
    {
        return this._type;
    }

    /**
     * Change the bodytype. I.e circle of rectangle.
     * @param type The new bodytype. I.e circle or rectangle.
     */
    public void setType(Geometry type)
    {
        this._type = type;

    }

    @Override
    public String toString()
    {
        return String.format("Body [position=%s, height=%s, width=%s, orientation=%s, type=%s]", _position, _height, _width, _orientation, _type);
    }

   
    

}

package application.engine.game_object.physics;

import application.engine.entities.Bullet;
import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.rendering.TranslatedPoint;
import application.util.Vector2;

/**
 * Handles all collision in the game. Can check if an object is collided with another object and can provided a proper response.
 * 
 * @author Gruppe6
 *
 */
public class CollisionHandler
{
    /**
     * Provides a proper response where all kinetic energy is lost.
     * 
     * @param collided
     *            The object that will be acting to the collision.
     * @param other
     *            The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    public static TranslatedPoint collisionResponse(GameObject collided, GameObject other)
    {
        if(collided instanceof Bullet || other instanceof Bullet)
        {
            System.out.println("ITS A BULLET");
        }
        if (collided.getBody().getType() == Body.Geometry.CIRCLE && other.getBody().getType() == Body.Geometry.CIRCLE)
        {
            return collisionResponseCircleCircle(collided, other);
        }
        else if (collided.getBody().getType() == Body.Geometry.CIRCLE && other.getBody().getType() == Body.Geometry.RECTANGLE)
        {
            return collisionResponseCircleRectangle(collided, other);
        }
        else if (collided.getBody().getType() == Body.Geometry.RECTANGLE && other.getBody().getType() == Body.Geometry.CIRCLE)
        {
            return collisionResponseRectangleCircle(other, collided);
        }
        else if (collided.getBody().getType() == Body.Geometry.RECTANGLE && other.getBody().getType() == Body.Geometry.RECTANGLE)
        {
            collisionResponseRectangleRectangle(collided, other);
        }
        return null;
    }

    /**
     * Provides a proper response where all kinetic energy is lost for rectangle rectangle collision.
     * 
     * @param collided
     *            The object that will be acting to the collision.
     * @param other
     *            The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    private static TranslatedPoint collisionResponseRectangleRectangle(GameObject collided, GameObject other)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Provides a proper response where all kinetic energy is lost for rectangle circle collision, where the rectangle is the object acting
     * on collision.
     * 
     * @param collided
     *            The object that will be acting to the collision.
     * @param other
     *            The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    private static TranslatedPoint collisionResponseRectangleCircle(GameObject other, GameObject collided)
    {
        throw new UnsupportedOperationException("Not implemented");
    }

    /**
     * Check if 2 game objects are colliding.
     * 
     * @param o1
     *            game object 1.
     * @param o2
     *            game object 2.
     * @return Returns true if the objects are colliding.
     */
    public static boolean isColliding(GameObject o1, GameObject o2)
    {

        boolean retval = false;
        if (o1.getBody().getType() == Body.Geometry.CIRCLE && o2.getBody().getType() == Body.Geometry.CIRCLE)
        {
            retval = collisionCircleCircle(o1, o2);
        }
        else if (o1.getBody().getType() == Body.Geometry.CIRCLE && o2.getBody().getType() == Body.Geometry.RECTANGLE)
        {
            retval = collisionCircleRectangle(o1, o2);
        }
        else if (o1.getBody().getType() == Body.Geometry.RECTANGLE && o2.getBody().getType() == Body.Geometry.CIRCLE)
        {
            retval = collisionCircleRectangle(o2, o1);
        }
        else if (o1.getBody().getType() == Body.Geometry.RECTANGLE && o2.getBody().getType() == Body.Geometry.RECTANGLE)
        {
            retval = collisionRectangleRectangle(o1, o2);
        }

        return retval;
    }

    /**
     * Provides a proper response where all kinetic energy is lost for circle circle collision.
     * 
     * @param collided
     *            The object that will be acting to the collision.
     * @param other
     *            The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    public static TranslatedPoint collisionResponseCircleCircle(GameObject collided, GameObject other)
    {
        double collidedx = collided.getBody().getPosition().getX();
        double otherx = other.getBody().getPosition().getX();
        double collidedy = collided.getBody().getPosition().getY();
        double othery = other.getBody().getPosition().getY();
        // http://ericleong.me/research/circle-circle/ Need this link for bullet bounce or similar.
        Vector2 distanceBetweenObjects = new Vector2(collidedx - otherx, collidedy - othery);
        distanceBetweenObjects.setMagnitude(collided.getBody().getHeight() / 2 + other.getBody().getHeight() / 2);
        return new TranslatedPoint(otherx + distanceBetweenObjects.getX(), othery + distanceBetweenObjects.getY());
    }

    /**
     * Provides a proper response where all kinetic energy is lost for rectangle circle collision, where the circle is the object acting on
     * collision.
     * 
     * @param collided
     *            The object that will be acting to the collision.
     * @param other
     *            The object the acting object collided with.
     * @return Returns the new suggested position of the collided object.
     */
    public static TranslatedPoint collisionResponseCircleRectangle(GameObject collided, GameObject other)
    {
        double collidedx = collided.getBody().getPosition().getX();
        double collidedy = collided.getBody().getPosition().getY();
        double otherx = other.getBody().getPosition().getX();
        double othery = other.getBody().getPosition().getY();
        double collidedHeight = collided.getBody().getHeight();
        double collidedWidth = collided.getBody().getWidth();
        double otherHeight = other.getBody().getHeight();
        double otherWidth = other.getBody().getWidth();
        double collidedCenterX = collided.getBody().getCenter().getX();
        double collidedCenterY = collided.getBody().getCenter().getY();
        double otherCenterX = other.getBody().getCenter().getX();
        double otherCenterY = other.getBody().getCenter().getY();

        if (collidedCenterX > otherx && collidedCenterX < otherx + otherWidth)
        {
            if (collidedCenterY < otherCenterY)
            {
                return new TranslatedPoint(collidedx, othery - collidedHeight);
            }
            else
            {
                return new TranslatedPoint(collidedx, othery + otherHeight);
            }

        }
        else if (collidedCenterY > othery && collidedCenterY < othery + otherHeight)
        {
            if (collidedCenterX < otherCenterX)
            {
                return new TranslatedPoint(otherx - collidedWidth, collidedy);
            }
            else
            {
                return new TranslatedPoint(otherx + otherWidth, collidedy);
            }
        }

        double cornerX = otherx;
        double cornerY = othery;
        if (collidedCenterX > otherx)
        {
            cornerX += otherWidth;
            if (collidedCenterY > othery)
            {
                cornerY += otherHeight;
            }

        }
        else if (collidedCenterX < otherx)
        {
            if (collidedCenterY > othery)
            {
                cornerY += otherHeight;
            }
        }
        // http://math.stackexchange.com/questions/356792/how-to-find-nearest-point-on-line-of-rectangle-from-anywhere
        Vector2 distanceBetweenObjects = new Vector2(collidedCenterX - cornerX, collidedCenterY - cornerY);
        distanceBetweenObjects.setMagnitude(collided.getBody().getHeight() / 2);
        return new TranslatedPoint(cornerX + distanceBetweenObjects.getX() - collided.getBody().getWidth() / 2,
                cornerY + distanceBetweenObjects.getY() - collided.getBody().getHeight() / 2);
    }

    /**
     * Checks if 2 circles are colliding.
     * 
     * @param o1
     *            Circle 1
     * @param o2
     *            Circle 2
     * @return Returns true if the 2 circles are colliding.
     */
    private static boolean collisionCircleCircle(GameObject o1, GameObject o2)
    {
        double c1x = o1.getBody().getPosition().getX();
        double c2x = o2.getBody().getPosition().getX();
        double c1y = o1.getBody().getPosition().getY();
        double c2y = o2.getBody().getPosition().getY();

        double dx = c1x - c2x;
        double dy = c1y - c2y;
        double c1r = o1.getBody().getHeight() / 2;
        double c2r = o2.getBody().getHeight() / 2;

        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1r + c2r);
    }

    /**
     * Checks if a circle and rectangle is colliding.
     * 
     * @param circle
     *            The circle
     * @param rect
     *            The rectangle.
     * @return Returns true if the rectangle and the circle is colliding.
     */
    public static boolean collisionCircleRectangle(GameObject circle, GameObject rect)
    {
        double circleDistanceX = Math.abs(rect.getBody().getCenter().getX() - circle.getBody().getCenter().getX());
        double circleDistanceY = Math.abs(rect.getBody().getCenter().getY() - circle.getBody().getCenter().getY());

        if (circleDistanceY >= (rect.getBody().getHeight() / 2 + circle.getBody().getHeight() / 2))
        {
            return false;
        }
        if (circleDistanceX >= (rect.getBody().getWidth() / 2 + circle.getBody().getWidth() / 2))
        {
            return false;
        }
        if (circleDistanceY < (rect.getBody().getHeight() / 2))
        {
            return true;
        }
        if (circleDistanceX < (rect.getBody().getWidth() / 2))
        {
            return true;
        }
        double cornerDistanceSq = Math.sqrt(
                Math.pow((circleDistanceX - (rect.getBody().getWidth() / 2)), 2) + Math.pow((circleDistanceY - (rect.getBody().getHeight() / 2)), 2));

        return (cornerDistanceSq < circle.getBody().getHeight() / 2);
    }

    /**
     * Checks if 2 rectangles are colliding.
     * 
     * @param rect1
     *            Rectangle 1
     * @param rect2
     *            Rectangle 2
     * @return Returns true if the 2 rectangles are colliding
     */
    public static boolean collisionRectangleRectangle(GameObject rect1, GameObject rect2)
    {

        double r1X = rect1.getBody().getPosition().getX();
        double r1Y = rect1.getBody().getPosition().getY();
        double r1H = rect1.getBody().getWidth();
        double r1L = rect1.getBody().getHeight();
        double r2X = rect2.getBody().getPosition().getX();
        double r2Y = rect2.getBody().getPosition().getY();
        double r2H = rect2.getBody().getWidth();
        double r2L = rect2.getBody().getHeight();

        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
        return xOverlap && yOverlap;
    }

    /**
     * Checks if a value is within a range.
     * 
     * @param value
     *            The value to be checked if its within a range.
     * @param start
     *            The start of the range.
     * @param end
     *            The end of the range.
     * @return Returns true if the value is bigger than or equal to the start value and smaller than or equal to the end value.
     */
    private static boolean valueInRange(double value, double start, double end)
    {
        // FIXME if more methods like these pop up around the application it should be put in a new Math class.
        return (value >= start) && (value <= end);
    }

}

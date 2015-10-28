package application;

import javafx.geometry.Point2D;

public class CollisionHandler
{
    public static Point2D collisionResponse(GameObject collided, GameObject other)
    {
        if (collided.getBody().getType() == Body.Type.CIRCLE && other.getBody().getType() == Body.Type.CIRCLE)
        {
            return collisionResponseCircleCircle(collided, other);
        }
        else if (collided.getBody().getType() == Body.Type.CIRCLE && other.getBody().getType() == Body.Type.RECTANGLE)
        {
            return null;
            // return collisionCircleRectangle(gO1, gO2);
        }
        else if (collided.getBody().getType() == Body.Type.RECTANGLE && other.getBody().getType() == Body.Type.CIRCLE)
        {
            return null;
            // return collisionCircleRectangle(gO2, gO1);
        }
        else if (collided.getBody().getType() == Body.Type.RECTANGLE && other.getBody().getType() == Body.Type.RECTANGLE)
        {
            return null;
            // return collisionRectangleRectangle(gO1, gO2);
        }
        return null;
    }

    public static boolean testCollision(GameObject gO1, GameObject gO2)
    {

        if (gO1.getBody().getType() == Body.Type.CIRCLE && gO2.getBody().getType() == Body.Type.CIRCLE)
        {
            return collisionCircleCircle(gO1, gO2);
        }
        else if (gO1.getBody().getType() == Body.Type.CIRCLE && gO2.getBody().getType() == Body.Type.RECTANGLE)
        {
            return collisionCircleRectangle(gO1, gO2);
        }
        else if (gO1.getBody().getType() == Body.Type.RECTANGLE && gO2.getBody().getType() == Body.Type.CIRCLE)
        {
            return collisionCircleRectangle(gO2, gO1);
        }
        else if (gO1.getBody().getType() == Body.Type.RECTANGLE && gO2.getBody().getType() == Body.Type.RECTANGLE)
        {
            return collisionRectangleRectangle(gO1, gO2);
        }
        return false;
    }

    // http://ericleong.me/research/circle-circle/  Need this link for bullet bounce or similar.
    public static Point2D collisionResponseCircleCircle(GameObject collided, GameObject other)
    {
        double collidedx = collided.getBody().getPosition().getX();
        double otherx = other.getBody().getPosition().getX();
        double collidedy = collided.getBody().getPosition().getY();
        double othery = other.getBody().getPosition().getY();

        Vector2 distanceBetweenObjects = new Vector2(collidedx - otherx, collidedy - othery);
        distanceBetweenObjects.setMagnitude(collided.body.getLength() / 2 + other.body.getLength() / 2);
        return new Point2D(otherx + distanceBetweenObjects.getX(), othery + distanceBetweenObjects.getY());
    }

    public static boolean collisionCircleCircle(GameObject c1, GameObject c2)
    {
        double c1x = c1.getBody().getPosition().getX();
        double c2x = c2.getBody().getPosition().getX();
        double c1y = c1.getBody().getPosition().getY();
        double c2y = c2.getBody().getPosition().getY();

        double dx = c1x - c2x;
        double dy = c1y - c2y;
        double c1r = c1.getBody().getLength() / 2;
        double c2r = c2.getBody().getLength() / 2;

        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1r + c2r);
    }

    public static boolean collisionCircleRectangle(GameObject c, GameObject r)
    {
        double circleDistanceX = Math.abs(r.getBody().getCenter().getX() - c.getBody().getCenter().getX());
        double circleDistanceY = Math.abs(r.getBody().getCenter().getY() - c.getBody().getCenter().getY());

        if (circleDistanceX > (r.getBody().getLength() / 2 + c.getBody().getLength()))
        {
            return false;
        }
        if (circleDistanceY > (r.getBody().getWidth() / 2 + c.getBody().getLength()))
        {
            return false;
        }
        if (circleDistanceX <= (r.getBody().getLength() / 2))
        {
            return true;
        }
        if (circleDistanceY <= (r.getBody().getWidth() / 2))
        {
            return true;
        }

        double cornerDistanceSq = Math.pow((circleDistanceX - (r.getBody().getLength() / 2)), 2)
                + Math.pow((circleDistanceY - (r.getBody().getWidth() / 2)), 2);

        return (cornerDistanceSq <= (Math.pow(c.getBody().getLength(), 2)));
    }

    public static boolean collisionRectangleRectangle(GameObject r1, GameObject r2)
    {

        double r1X = r1.getBody().getPosition().getX();
        double r1Y = r1.getBody().getPosition().getY();
        double r1H = r1.getBody().getWidth();
        double r1L = r1.getBody().getLength();
        double r2X = r2.getBody().getPosition().getX();
        double r2Y = r2.getBody().getPosition().getY();
        double r2H = r2.getBody().getWidth();
        double r2L = r2.getBody().getLength();

        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
        return xOverlap && yOverlap;
    }

    private static boolean valueInRange(double value, double min, double max)
    {
        return (value >= min) && (value <= max);
    }

}

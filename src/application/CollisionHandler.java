package application;

import javafx.geometry.Point2D;

public class CollisionHandler
{
    public static Point2D collisionResponse(GameObject collided, GameObject other)
    {

        if (collided.body.getType() == Body.Type.CIRCLE && other.body.getType() == Body.Type.CIRCLE)
        {
            return collisionResponseCircleCircle(collided, other);
        }
        else if (collided.body.getType() == Body.Type.CIRCLE && other.body.getType() == Body.Type.RECTANGLE)
        {
            return collisionResponseCircleRectangle(collided, other);
            // return collisionCircleRectangle(gO1, gO2);
        }
        else if (collided.body.getType() == Body.Type.RECTANGLE && other.body.getType() == Body.Type.CIRCLE)
        {
            return collisionResponseCircleRectangle(other, collided);
            // return collisionCircleRectangle(gO2, gO1);
        }
        else if (collided.body.getType() == Body.Type.RECTANGLE && other.body.getType() == Body.Type.RECTANGLE)
        {
            return null;
            // return collisionRectangleRectangle(gO1, gO2);
        }
        return null;
    }

    public static boolean testCollision(GameObject gO1, GameObject gO2)
    {

        boolean retval = false;
        if (gO1.body.getType() == Body.Type.CIRCLE && gO2.body.getType() == Body.Type.CIRCLE)
        {
            retval = collisionCircleCircle(gO1, gO2);
        }
        else if (gO1.body.getType() == Body.Type.CIRCLE && gO2.body.getType() == Body.Type.RECTANGLE)
        {
            retval = collisionCircleRectangle(gO1, gO2);
        }
        else if (gO1.body.getType() == Body.Type.RECTANGLE && gO2.body.getType() == Body.Type.CIRCLE)
        {
            retval = collisionCircleRectangle(gO2, gO1);
        }
        else if (gO1.body.getType() == Body.Type.RECTANGLE && gO2.body.getType() == Body.Type.RECTANGLE)
        {
            retval = collisionRectangleRectangle(gO1, gO2);
        }

        return retval;
    }

    // http://ericleong.me/research/circle-circle/ Need this link for bullet bounce or similar.
    public static Point2D collisionResponseCircleCircle(GameObject collided, GameObject other)
    {
        double collidedx = collided.body.getPosition().getX();
        double otherx = other.body.getPosition().getX();
        double collidedy = collided.body.getPosition().getY();
        double othery = other.body.getPosition().getY();

        Vector2 distanceBetweenObjects = new Vector2(collidedx - otherx, collidedy - othery);
        distanceBetweenObjects.setMagnitude(collided.body.getHeight() / 2 + other.body.getHeight() / 2);
        return new Point2D(otherx + distanceBetweenObjects.getX(), othery + distanceBetweenObjects.getY());
    }

    public static Point2D collisionResponseCircleRectangle(GameObject collided, GameObject other)
    {
        double collidedx = collided.body.getPosition().getX();
        double collidedy = collided.body.getPosition().getY();
        double otherx = other.body.getPosition().getX();
        double othery = other.body.getPosition().getY();
        double collidedHeight = collided.body.getHeight();
        double collidedWidth = collided.body.getWidth();
        double otherHeight = other.body.getHeight();
        double otherWidth = other.body.getWidth();
        double collidedCenterX = collided.body.getCenter().getX();
        double collidedCenterY = collided.body.getCenter().getY();
        double otherCenterX = other.body.getCenter().getX();
        double otherCenterY = other.body.getCenter().getY();

        if (collidedCenterX > otherx && collidedCenterX < otherx + otherWidth)
        {
            if (collidedCenterY < otherCenterY)
            {
                return new Point2D(collidedx, othery - collidedHeight);
            }
            else
            {
                return new Point2D(collidedx, othery + otherHeight);
            }

        }
        else if (collidedCenterY > othery && collidedCenterY < othery + otherHeight)
        {
            if (collidedCenterX < otherCenterX)
            {
                return new Point2D(otherx - collidedWidth, collidedy);
            }
            else
            {
                return new Point2D(otherx + otherWidth, collidedy);
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
        distanceBetweenObjects.setMagnitude(collided.body.getHeight() / 2);
        return new Point2D(cornerX + distanceBetweenObjects.getX() - collided.body.getWidth() / 2,
                cornerY + distanceBetweenObjects.getY() - collided.body.getHeight() / 2);
    }

    public static boolean collisionCircleCircle(GameObject c1, GameObject c2)
    {
        double c1x = c1.body.getPosition().getX();
        double c2x = c2.body.getPosition().getX();
        double c1y = c1.body.getPosition().getY();
        double c2y = c2.body.getPosition().getY();

        double dx = c1x - c2x;
        double dy = c1y - c2y;
        double c1r = c1.body.getHeight() / 2;
        double c2r = c2.body.getHeight() / 2;

        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1r + c2r);
    }

    public static boolean collisionCircleRectangle(GameObject circle, GameObject r)
    {
        double circleDistanceX = Math.abs(r.body.getCenter().getX() - circle.body.getCenter().getX());
        double circleDistanceY = Math.abs(r.body.getCenter().getY() - circle.body.getCenter().getY());

        if (circleDistanceY >= (r.body.getHeight() / 2 + circle.body.getHeight() / 2))
        {
            return false;
        }
        if (circleDistanceX >= (r.body.getWidth() / 2 + circle.body.getWidth() / 2))
        {
            return false;
        }
        if (circleDistanceY < (r.body.getHeight() / 2))
        {
            return true;
        }
        if (circleDistanceX < (r.body.getWidth() / 2))
        {
            return true;
        }
        double cornerDistanceSq = Math
                .sqrt(Math.pow((circleDistanceX - (r.body.getWidth() / 2)), 2) + Math.pow((circleDistanceY - (r.body.getHeight() / 2)), 2));

        return (cornerDistanceSq < circle.body.getHeight() / 2);
    }

    public static boolean collisionRectangleRectangle(GameObject r1, GameObject r2)
    {

        double r1X = r1.body.getPosition().getX();
        double r1Y = r1.body.getPosition().getY();
        double r1H = r1.body.getWidth();
        double r1L = r1.body.getHeight();
        double r2X = r2.body.getPosition().getX();
        double r2Y = r2.body.getPosition().getY();
        double r2H = r2.body.getWidth();
        double r2L = r2.body.getHeight();

        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
        return xOverlap && yOverlap;
    }

    private static boolean valueInRange(double value, double min, double max)
    {
        return (value >= min) && (value <= max);
    }

}

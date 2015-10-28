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
            return collisionResponseCircleRectangle(collided, other);
            // return collisionCircleRectangle(gO1, gO2);
        }
        else if (collided.getBody().getType() == Body.Type.RECTANGLE && other.getBody().getType() == Body.Type.CIRCLE)
        {
            return collisionResponseCircleRectangle(other, collided);
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

    // http://ericleong.me/research/circle-circle/ Need this link for bullet bounce or similar.
    public static Point2D collisionResponseCircleCircle(GameObject collided, GameObject other)
    {
        double collidedx = collided.getBody().getPosition().getX();
        double otherx = other.getBody().getPosition().getX();
        double collidedy = collided.getBody().getPosition().getY();
        double othery = other.getBody().getPosition().getY();

        Vector2 distanceBetweenObjects = new Vector2(collidedx - otherx, collidedy - othery);
        distanceBetweenObjects.setMagnitude(collided.body.getHeight() / 2 + other.body.getHeight() / 2);
        return new Point2D(otherx + distanceBetweenObjects.getX(), othery + distanceBetweenObjects.getY());
    }

    public static Point2D collisionResponseCircleRectangle(GameObject collided, GameObject other)
    {
        double collidedx = collided.getBody().getPosition().getX();
        double collidedy = collided.getBody().getPosition().getY();
        double otherx = other.getBody().getPosition().getX();
        double othery = other.getBody().getPosition().getY();
        double collidedHeight = collided.getBody().getHeight();
        double collidedWidth = collided.getBody().getWidth();
        double otherHeight = other.getBody().getHeight();
        double otherWidth = other.getBody().getWidth();
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
        double c1x = c1.getBody().getPosition().getX();
        double c2x = c2.getBody().getPosition().getX();
        double c1y = c1.getBody().getPosition().getY();
        double c2y = c2.getBody().getPosition().getY();

        double dx = c1x - c2x;
        double dy = c1y - c2y;
        double c1r = c1.getBody().getHeight() / 2;
        double c2r = c2.getBody().getHeight() / 2;

        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1r + c2r);
    }

    public static boolean collisionCircleRectangle(GameObject circle, GameObject r)
    {
        double circleDistanceX = Math.abs(r.getBody().getCenter().getX() - circle.getBody().getCenter().getX());
        double circleDistanceY = Math.abs(r.getBody().getCenter().getY() - circle.getBody().getCenter().getY());

        if (circleDistanceY >= (r.getBody().getHeight() / 2 + circle.getBody().getHeight() / 2))
        {
            return false;
        }
        if (circleDistanceX >= (r.getBody().getWidth() / 2 + circle.getBody().getWidth() / 2))
        {
            return false;
        }
        if (circleDistanceY < (r.getBody().getHeight() / 2))
        {
            return true;
        }
        if (circleDistanceX < (r.getBody().getWidth() / 2))
        {
            return true;
        }
        double cornerDistanceSq = Math.sqrt(Math.pow((circleDistanceX - (r.getBody().getWidth() / 2)), 2)
                + Math.pow((circleDistanceY - (r.getBody().getHeight()/ 2)), 2));

        return (cornerDistanceSq < circle.getBody().getHeight() / 2);
    }

    public static boolean collisionRectangleRectangle(GameObject r1, GameObject r2)
    {

        double r1X = r1.getBody().getPosition().getX();
        double r1Y = r1.getBody().getPosition().getY();
        double r1H = r1.getBody().getWidth();
        double r1L = r1.getBody().getHeight();
        double r2X = r2.getBody().getPosition().getX();
        double r2Y = r2.getBody().getPosition().getY();
        double r2H = r2.getBody().getWidth();
        double r2L = r2.getBody().getHeight();

        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
        return xOverlap && yOverlap;
    }

    private static boolean valueInRange(double value, double min, double max)
    {
        return (value >= min) && (value <= max);
    }

}

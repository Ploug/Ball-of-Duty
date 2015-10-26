package application;

public class CollisionHandler
{
    public static Vector2 collisionResponse(GameObject collided, GameObject other)
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

    // http://ericleong.me/research/circle-circle/
    public static Vector2 collisionResponseCircleCircle(GameObject collided, GameObject other)
    {
        Vector2 collidedVelocity = collided.getPhysics().getVelocity();
        double collidedx = collided.getBody().getPosition().getX();
        double otherx = other.getBody().getPosition().getX();
        double collidedy = collided.getBody().getPosition().getY();
        double othery = other.getBody().getPosition().getY();
        double collidedvx = collidedVelocity.getX();
        double collidedvy = collidedVelocity.getY();
        double collisiondist = Math.sqrt(Math.pow(otherx - collidedx, 2) + Math.pow(othery - collidedy, 2));

        // Calculating norm vector
        double n_x = (otherx - collidedx) / collisiondist;
        double n_y = (othery - collidedy) / collisiondist;
        
        double p = 2 * (collidedvx * n_x + collidedvy * n_y) / (1 + 1); // Should just get tangent here since we dont use mass in our system.
        
        // Calculating the new velocity
        double w_x = collidedvx - p * 1 * n_x - p * 1 * n_x;
        double w_y = collidedvy - p * 1 * n_y - p * 1 * n_y;

      
        Vector2[] vectorArray = {new Vector2(w_x, w_y),collidedVelocity};
        Vector2 retVal = Vector2.averageVector(vectorArray);
        if(retVal.getMagnitude()<1) // Hvis den modsatte pil 180 grader modsat indgangsvinklen så er det = 0, men pga double er det = cirka nul. Her tjekker vi for det og ændre det så den ikke går i stå.
        {
            retVal = collidedVelocity;
            retVal.rotateDegrees(90);
        }
        retVal.setMagnitude(collidedVelocity.getMagnitude());
        return  retVal; 
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
        int r1X = (int) r1.getBody().getPosition().getX();
        int r1Y = (int) r1.getBody().getPosition().getY();
        int r1H = (int) r1.getBody().getWidth();
        int r1L = (int) r1.getBody().getLength();
        int r2X = (int) r2.getBody().getPosition().getX();
        int r2Y = (int) r2.getBody().getPosition().getY();
        int r2H = (int) r2.getBody().getWidth();
        int r2L = (int) r2.getBody().getLength();

        boolean xOverlap = valueInRange(r1X, r2X, r2X + r2L) || valueInRange(r2X, r1X, r1X + r1L);
        boolean yOverlap = valueInRange(r1Y, r2Y, r2Y + r2H) || valueInRange(r2Y, r1Y, r1Y + r1H);
        return xOverlap && yOverlap;
    }

    private static boolean valueInRange(int value, int min, int max)
    {
        return (value >= min) && (value <= max);
    }

}

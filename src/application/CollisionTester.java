package application;

public class CollisionTester
{
	public static boolean testCollision(GameObject gO1, GameObject gO2)
	{
		if (gO1.type.equals("Circle") && gO2.type.equals("Circle"))
		{
			return collisionCircleCircle(gO1,gO2);
		} else if (gO1.getType().equals("Circle") && gO2.type.equals("Rectangle"))
		{
			return collisionCircleRectangle(gO1,gO2);
		} else if (gO1.type.equals("Rectangle") && gO2.type.equals("Circle"))
		{
			return collisionCircleRectangle(gO2,gO1);
		} else if (gO1.type.equals("Rectangle") && gO2.type.equals("Rectangle"))
		{
			return collisionRectangleRectangle(gO1,gO2);
		}
		return false;
	}
	
    public static boolean collisionCircleCircle(GameObject c1, GameObject c2)
    {
        double dx = c1.getBody().getPosition().getX() - c2.getBody().getPosition().getX();
        double dy = c1.getBody().getPosition().getY() - c2.getBody().getPosition().getY();
        
        return Math.sqrt((dx * dx) + (dy * dy)) <= (c1.getBody().getLength() / 2) + (c2.getBody().getLength() / 2);
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

        double cornerDistanceSq = Math.pow((circleDistanceX - (r.getBody().getLength() / 2)),2) + Math.pow((circleDistanceY - (r.getBody().getWidth() / 2)),2);

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

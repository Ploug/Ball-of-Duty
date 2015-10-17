package application;

public class Vector2 
{
	private double x;
	private double y;
	
	public Vector2(double x, double y)
	{
	    this.x = x;
	    this.y = y;
	}
	
	public double getX()
	{
		return this.x;
	}
	public double getY()
	{
		return this.y;
	}
	public double getMagnitude()
	{
		return Math.sqrt(Math.pow(getX(), 2)+Math.pow(getY(), 2));
	}
	/**
	 *  Not implemented
	 * @param multiplier
	 */
	public double scalarProduct(Vector2 multiplier)
    {
	    return this.x * multiplier.getX()+this.y * multiplier.getY();
    }
	public Vector2 scalarMultiply(double multiplier)
    {
        this.x *= multiplier;
        this.y *=multiplier;
        return this;
    }
	public Vector2 minusVector(Vector2 vector)
    {
        this.x -= vector.getX();
        this.y -= vector.getY();
        return this;
    }
	public Vector2 addVector(Vector2 vector)
	{
	    this.x += vector.getX();
	    this.y += vector.getY();
        return this;
	}
	public static Vector2 getSubstitedVectors(Vector2 vector1, Vector2 vector2)
    {
        return new Vector2(vector1.getX()-vector2.getX(),vector1.getY()-vector2.getY());
    }
	public static Vector2 getAddedVectors(Vector2 vector1, Vector2 vector2)
	{
	    return new Vector2(vector1.getX()+vector2.getX(),vector1.getY()+vector2.getY());
	}

    @Override
    public String toString()
    {
        return "Vector2 [x=" + x + ", y=" + y + "]";
    }
    public static Vector2 Normalize(Vector2 vector)
    {
        double length = vector.getMagnitude();
        return new Vector2(vector.getX()/length,vector.getY()/length);
        
    }
	
	
	

}

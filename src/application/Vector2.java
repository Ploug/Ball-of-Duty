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
    public void setX(double x)
    {
        this.x = x;;
    }

    public void rotateDegrees(double degrees)
    {
        double radians = Math.toRadians(degrees);
        double px = x*Math.cos(radians)-y*Math.sin(radians);
        double py = x*Math.sin(radians)+y*Math.cos(radians);
        x = px;
        y = py;
        
    }
    public void setY(double y)
    {
        this.y = y;;
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
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
    }

    public double getAngle()
    {
        return Math.toDegrees(Math.atan2(y, x));
    }

    /**
     * Not implemented
     * 
     * @param multiplier
     */
    public double scalarProduct(Vector2 multiplier)
    {
        return this.x * multiplier.getX() + this.y * multiplier.getY();
    }

    public Vector2 scalarMultiply(double multiplier)
    {
        this.x *= multiplier;
        this.y *= multiplier;
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
        return new Vector2(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    public static Vector2 getAddedVectors(Vector2 vector1, Vector2 vector2)
    {
        return new Vector2(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    public void normalize()
    {
        double length = this.getMagnitude();
        if (!(x == 0 && y == 0))
        {
            x /= length;
            y /= length;
        }

    }

    public static Vector2 normalize(Vector2 vector)
    {
        double length = vector.getMagnitude();
        return new Vector2(vector.getX() / length, vector.getY() / length);

    }

    @Override
    public String toString()
    {
        return "Vector2 [x=" + x + ", y=" + y + ", angle=" + this.getAngle() + ", magnitude=" + this.getMagnitude() + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Vector2 other = (Vector2) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) return false;
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) return false;
        return true;
    }

    public static Vector2 averageVector(Vector2[] array)
    {
        double xTotal = 0;
        double yTotal = 0;
        for (Vector2 vector : array)
        {
            xTotal += vector.getX();
            yTotal += vector.getY();
        }
        if (!(xTotal == 0 && yTotal == 0))
        {
            xTotal /= array.length;
            yTotal /= array.length;
        }

        return new Vector2(xTotal, yTotal);
    }

    public Vector2 setMagnitude(double i)
    {
        this.normalize();
        this.scalarMultiply(i);
        return this;
    }

}

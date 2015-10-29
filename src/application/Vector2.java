package application;

public class Vector2
{
    private double x;
    private double y;

    /**
     * Creates a vector, with x and y making the end point of the vector.
     * @param x
     * @param y
     */
    public Vector2(double x, double y)
    {
        this.x = x;
        this.y = y;
    }
    /**
     * Sets x of the vector
     * @param x
     */
    public void setX(double x)
    {
        this.x = x;;
    }

    /**
     * Rotates the vector counter clockwise.
     * @param degrees
     */
    public void rotateDegrees(double degrees)
    {
        double radians = Math.toRadians(degrees);
        double px = x*Math.cos(radians)-y*Math.sin(radians);
        double py = x*Math.sin(radians)+y*Math.cos(radians);
        x = px;
        y = py;
        
    }
    /**
     * Sets y of the vector
     * @param y
     */
    public void setY(double y)
    {
        this.y = y;;
    }
    /**
     * @return x of the vector
     */
    public double getX()
    {
        return this.x;
    }

    /**
     * @return y of the vector
     */
    public double getY()
    {
        return this.y;
    }

    /**
     * @return The length of the vector. (Speed of the velocity)
     */
    public double getMagnitude()
    {
        return Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2));
    }

    /**
     * The degrees goes like this: 
     * 0/360 at the right/east.
     * 90 at the top/north.
     * 180 at the left/vest.
     * 270 at the bottom/south.
     * (Meaning the degrees goes up counter clockwise)
     * @return The angle of the vector.
     */
    public double getAngle()
    {
        return Math.toDegrees(Math.atan2(y, x));
    }


    /**
     * calculates and returns the scalar product of the vector
     * the method is called on and the vector given as parameter.
     * @param vector
     * @return The scalar product of the two vectors.
     */
    public double scalarProduct(Vector2 vector)
    {
        return this.x * vector.getX() + this.y * vector.getY();
    }

    /**
     * Takes a multiplier used to scale the vector
     * @param multiplier
     * @return The scaled vector.
     */
    public Vector2 scalarMultiply(double multiplier)
    {
        this.x *= multiplier;
        this.y *= multiplier;
        return this;
    }

    /**
     * Subtracts the vector the method is called on with the vector given as parameter.
     * @param vector
     * @return The subtracted vector.
     */
    public Vector2 minusVector(Vector2 vector)
    {
        this.x -= vector.getX();
        this.y -= vector.getY();
        return this;
    }

    /**
     * Adds to the vector the method is called on with the vector given as parameter.
     * @param vector
     * @return the vector added with the given vector
     */
    public Vector2 addVector(Vector2 vector)
    {
        this.x += vector.getX();
        this.y += vector.getY();
        return this;
    }

    /**
     * Takes two vectors of type Vector2 and creates a new
     * vector of the type Vector2. The new vector is created
     * by subtracting vector2 from vector1
     * @param vector1
     * @param vector2
     * @return A NEW Vector2 created from the subtracted vectors.
     */
    public static Vector2 getSubtractedVectors(Vector2 vector1, Vector2 vector2)
    {
        return new Vector2(vector1.getX() - vector2.getX(), vector1.getY() - vector2.getY());
    }

    /**
     * Takes two vectors of type Vector2 and creates a new
     * vector of the type Vector2. The new vector is created
     * by adding vector1 and vector2 together.
     * @param vector1
     * @param vector2
     * @return A NEW Vector2 created from the added vectors.
     */
    public static Vector2 getAddedVectors(Vector2 vector1, Vector2 vector2)
    {
        return new Vector2(vector1.getX() + vector2.getX(), vector1.getY() + vector2.getY());
    }

    /**
     * Normalizes the vector. Normalizing a vector means
     * setting its length to a standard (1) WITHOUT changing
     * its direction.
     */
    public void normalize()
    {
        double length = this.getMagnitude();
        if (!(x == 0 && y == 0))
        {
            x /= length;
            y /= length;
        }

    }

    /**
     * Creates a NEW vector, that is the normalized vector
     * of the given vector.
     * @param vector
     * @return A NEW normalized vector.
     */
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

    /**
     * Creates a new vector of the type Vector2 that 
     * is the average vector of the given vector2Array
     * @param vector2Array
     * @return A NEW vector that is the average vector
     */
    public static Vector2 averageVector(Vector2[] vector2Array)
    {
        double xTotal = 0;
        double yTotal = 0;
        for (Vector2 vector : vector2Array)
        {
            xTotal += vector.getX();
            yTotal += vector.getY();
        }
        if (!(xTotal == 0 && yTotal == 0))
        {
            xTotal /= vector2Array.length;
            yTotal /= vector2Array.length;
        }

        return new Vector2(xTotal, yTotal);
    }

    /**
     * Sets the magnitude of the vector, by first normalizing
     * the vector and then scaling it, using the parameter i as
     * multiplier.
     * @param i
     * @return The vector, now with length = parameter i.
     */
    public Vector2 setMagnitude(double i)
    {
        this.normalize();
        this.scalarMultiply(i);
        return this;
    }

}

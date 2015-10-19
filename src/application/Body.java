package application;

public class Body
{
    public GameObject gameObject;
    private double x;
    private double y;

    public Body(GameObject gO)
    {
        this.gameObject = gO;
    }

    public void setX(double input)
    {
        x = input;
    }

    public void setY(double input)
    {
        y = input;
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public void collidesWith(GameObject gO)
    {

    }
}

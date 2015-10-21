package application;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain.ServerBody;
import org.datacontract.schemas._2004._07.System_Windows.Point;

public class Body extends ServerBody
{
    public GameObject gameObject;
    private Point position;
    private double orientation;

    public Body(GameObject gO, Point position)
    {
        this.position = position;
        this.gameObject = gO;
        this.orientation = 0;
    }

    public void collidesWith(GameObject gO)
    {

    }

    public Point getPosition()
    {
        return position;
    }

    public double getOrientation()
    {
        return orientation;
    }

    public void setOrientation(double orientation)
    {
        this.orientation = orientation;
    }

    public void increasePosition(double xIncrease, double yIncrease)
    {
        position.set_x(position.get_x() + xIncrease);
        position.set_y(position.get_y() + yIncrease);
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }
}

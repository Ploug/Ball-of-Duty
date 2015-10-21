package application;

import org.datacontract.schemas._2004._07.ball_of_duty_server.ServerBody;
import org.datacontract.schemas._2004._07.system.Point;

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
        position.setX(position.getX() + xIncrease);
        position.setY(position.getY() + yIncrease);
    }

    public void setPosition(Point position)
    {
        this.position = position;
    }
}

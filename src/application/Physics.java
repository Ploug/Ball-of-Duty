package application;

import java.util.ArrayList;
import java.util.HashSet;

public class Physics
{
    public GameObject gameObject;
    private double drag;
    private Vector2 velocity;
    private int speed = 0;
    private HashSet<Vector2> directionVectors;
    private Timer timer;

    public Physics(GameObject gO, int speed)
    {
        this.speed = speed;
        this.velocity = new Vector2(0, 0);
        this.gameObject = gO;
        directionVectors = new HashSet<>();
        timer = new Timer();
        timer.start();
    }

    public void update()
    {
        double secondsSinceLast = timer.getDuration()/1000;// compensating for lag
        gameObject.body.setX(gameObject.body.getX()+velocity.getX()*secondsSinceLast);
        gameObject.body.setY(gameObject.body.getY()+velocity.getY()*secondsSinceLast);
        timer.reset();

    }
    
    public Vector2 getVelolicity()
    {
        return velocity;
    }

    public void updateVelocity()
    {
        velocity = Vector2.averageVector(directionVectors.toArray(new Vector2[directionVectors.size()]));
        velocity.setMagnitude(speed);
    }

    public void setSpeed(int amount)
    {
        speed = amount;
    }
    public void addDirection(Vector2 direction)
    {
        if (!directionVectors.contains(direction))
        {
            directionVectors.add(direction);
            updateVelocity();
        }

    }

    public void removeDirection(Vector2 direction)
    {
        directionVectors.remove(direction);
        if (directionVectors.isEmpty())
        {
            velocity.setMagnitude(0);
        }
        updateVelocity();
    }

    // public void addVelocity(Vector2 inputVelocity)
    // {
    //
    // if (Vector2.getAddedVectors(velocity, inputVelocity).getMagnitude() < maxSpeed)
    // {
    // velocity.addVector(inputVelocity);
    // }
    //
    // }
}

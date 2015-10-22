package application;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Physics
{
    public GameObject gameObject;
    private Vector2 velocity;
    private int speed = 0;
    private HashSet<Vector2> directionVectors;
    private Timer timer;
    private List<CallBack> calculations;

    public Physics(GameObject gO, int speed)
    {
        this.speed = speed;
        this.velocity = new Vector2(0, 0);
        this.gameObject = gO;
        this.calculations = new ArrayList<>();
        directionVectors = new HashSet<>();
        timer = new Timer();
        timer.start();
    }

    public void update()
    {
        double secondsSinceLast = timer.getDuration() / 1000;// compensating for
                                                             // lag
        gameObject.body.increasePosition(velocity.getX() * secondsSinceLast, velocity.getY() * secondsSinceLast);
        
        timer.reset();

        for (CallBack cb : calculations)
        {
            cb.call();
        }

    }

   
    public void addCalculation(CallBack cb)
    {
        calculations.add(cb);
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

    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Physics other = (Physics) obj;
        if (calculations == null)
        {
            if (other.calculations != null) return false;
        }
        else if (!calculations.equals(other.calculations)) return false;
        if (directionVectors == null)
        {
            if (other.directionVectors != null) return false;
        }
        else if (!directionVectors.equals(other.directionVectors)) return false;
        if (speed != other.speed) return false;
        if (velocity == null)
        {
            if (other.velocity != null) return false;
        }
        else if (!velocity.equals(other.velocity)) return false;
        return true;
    }
    
    // public void addVelocity(Vector2 inputVelocity)
    // {
    //
    // if (Vector2.getAddedVectors(velocity, inputVelocity).getMagnitude() <
    // maxSpeed)
    // {
    // velocity.addVector(inputVelocity);
    // }
    //
    // }
}

package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Physics
{
    public GameObject gameObject;
    private Vector2 velocity;
    private int topspeed;
    private HashSet<Vector2> directionVectors;
    private Timer timer;
    private List<CallBack> calculations;
    boolean colliding = false;

    public Physics(GameObject gO, int topspeed)
    {
        this.gameObject = gO;
        this.topspeed = topspeed;
        this.velocity = new Vector2(0, 0);
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

    public void update(HashMap<Integer, GameObject> gameobjects, ArrayList<Wall> walls)
    {
        double secondsSinceLast = timer.getDuration() / 1000;// compensating for lag
        timer.reset();

        // System.out.println(colliding);
        // gameObject.body.setPosition(new Point2D(400, 400));
        GameObject temp = new GameObject(gameObject);
        temp.body.increasePosition(velocity.getX() * secondsSinceLast, velocity.getY() * secondsSinceLast);
        int collidedWith = -1;
        boolean collision = false;
        for (GameObject obj : gameobjects.values()) // Checks if it collides with anything.
        {
            if (obj.getId() != temp.getId())
            {
                if (CollisionHandler.testCollision(temp, obj))
                {
                    collidedWith = obj.getId();
                    temp.body.setPosition(CollisionHandler.collisionResponse(temp, obj)); // Gives new position so it doesnt collide
                    collision = true; // with the collided object anymore.
                    break;

                }
            }
        }
        if (!collision)
        {
            gameObject.body.increasePosition(velocity.getX() * secondsSinceLast, velocity.getY() * secondsSinceLast);
        }
        else
        {
            collision = false;
            for (GameObject obj : gameobjects.values()) // Checks if the new position collides with anything else.
            {
                if (obj.getId() == temp.getId() || obj.getId() == collidedWith)
                {
                    continue;
                }
                if (CollisionHandler.testCollision(temp, obj))
                {
                    collision = true;
                    break;

                }
            }
            if (!collision)
            {
                gameObject.body.setPosition(temp.body.getPosition()); // if it doesnt collide with a second object on the new position it
                                                                      // will set gameObject position to this.
            }
        }

        for (Wall wall : walls)
        {
            // System.out.println(wall.getID() + " " + wall.getCenter().getX() +
            // "," + wall.getCenter().getY() + " "
            // + wall.getBody().getPosition().getX() + "," +
            // wall.getBody().getPosition().getY() + "" +
            // wall.getBody().getHeight() + "" + wall.getBody().getLength());a
            if (CollisionHandler.testCollision(gameObject, wall))
            {
                collision = true;
            }
        }

        for (CallBack cb : calculations)
        {
            cb.call();
        }

    }

    public void addCalculation(CallBack cb)
    {
        calculations.add(cb);
    }

    public Vector2 getVelocity()
    {
        return velocity;
    }

    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;

    }

    public void updateVelocity()
    {
        if (!colliding)
        {
            velocity = Vector2.averageVector(directionVectors.toArray(new Vector2[directionVectors.size()]));
            velocity.setMagnitude(topspeed);
        }

    }

    public void setTopSpeed(int amount)
    {
        topspeed = amount;
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
        if (topspeed != other.topspeed) return false;
        if (velocity == null)
        {
            if (other.velocity != null) return false;
        }
        else if (!velocity.equals(other.velocity)) return false;
        return true;
    }

    public int getTopspeed()
    {
        return this.topspeed;
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

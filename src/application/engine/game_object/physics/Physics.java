package application.engine.game_object.physics;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ConcurrentMap;

import application.engine.entities.Bullet;
import application.engine.game_object.GameObject;
import application.util.CallBack;
import application.util.Vector2;

/**
 * Handles physics related to game objects.
 * 
 * @author Gruppe6
 *
 */
public class Physics
{
    private GameObject gameObject;
    private Vector2 velocity;
    private double topspeed;
    private HashSet<Vector2> directionVectors;
    private List<CallBack> callbacks;

    /**
     * Creates the physical property of the object describing its topspeed.
     * 
     * @param gameObject
     *            The game object this physics is partly composing.
     * @param topspeed
     *            The topspeed of the object.
     */
    public Physics(GameObject gameObject, double topspeed)
    {
        this.gameObject = gameObject;
        this.topspeed = topspeed;
        this.velocity = new Vector2(0, 0);
        this.callbacks = new ArrayList<>();
        directionVectors = new HashSet<>();

    }

    /**
     * Updates the position of the object based on the physics calculations.
     */
    public void update(double secondsSinceLastUpdate)
    {
        gameObject.getBody().getPosition().add(velocity.getX() * secondsSinceLastUpdate,
                velocity.getY() * secondsSinceLastUpdate);

        for (CallBack cb : callbacks)
        {
            cb.call();
        }
    }

    /**
     * Updates the position of the object based on physics calculation and the collision with other objects.
     * 
     * @param gameobjects
     *            The objects which need to be checked for collision.
     */
    public void updateWithCollision(double secondsSinceLastUpdate, ConcurrentMap<Integer, GameObject> gameobjects)
    {
        GameObject temp = new GameObject(gameObject);

        temp.getBody().increasePosition(velocity.getX() * secondsSinceLastUpdate,
                velocity.getY() * secondsSinceLastUpdate);
        boolean collision = false;

        for (GameObject obj : gameobjects.values()) // Checks if it collides with anything. only O(n^3) on very very rare occasions.
        {
            if (obj.getId() == temp.getId() || obj instanceof Bullet)
            {
                continue;
            }

            if (CollisionHandler.isColliding(temp.getBody(), obj.getBody()))
            {

                temp.getBody().setPosition(CollisionHandler.collisionResponse(temp.getBody(), obj.getBody())); // Gives new position so it doesnt collide
                for (GameObject obj2 : gameobjects.values()) // Checks if it collides with anything.
                {
                    if (obj2.getId() == temp.getId() || obj2.getId() == obj.getId() || obj2 instanceof Bullet)
                    {
                        continue;
                    }
                    if (CollisionHandler.isColliding(temp.getBody(), obj2.getBody()))
                    {
                        for (GameObject obj3 : gameobjects.values())
                        {
                            if (obj3.getId() == temp.getId() || obj3.getId() == obj.getId()
                                    || obj3.getId() == obj2.getId() || obj3 instanceof Bullet)
                            {
                                continue;
                            }
                            if (CollisionHandler.isColliding(temp.getBody(), obj3.getBody()))
                            {
                                collision = true;
                                break;
                            }

                        }
                        if (!collision)
                        {
                            // if it doesnt collide with a third object, put it to the secondcalculated position.
                            gameObject.getBody().setPosition(CollisionHandler.collisionResponse(temp.getBody(), obj2.getBody()));
                        }
                        collision = true;
                        break;
                    }

                }
                if (!collision) // if it doesnt collide with a second object, put it to the first calculated position.
                {
                    gameObject.getBody().setPosition(temp.getBody().getPosition());
                }
                collision = true; // with the collided object anymore.
                break;

            }

        }

        if (!collision)
        {
            gameObject.getBody().increasePosition(velocity.getX() * secondsSinceLastUpdate,
                    velocity.getY() * secondsSinceLastUpdate);
        }

        for (CallBack cb : callbacks)
        {
            cb.call();
        }

    }

    /**
     * Adds a method that needs to be called back every update.
     * 
     * @param cb
     *            The method to be called back.
     */
    public void addMethod(CallBack cb)
    {
        callbacks.add(cb);
    }

    /**
     * Returns the velocity of the game object as a vector.
     * 
     * @return The velocity of the object as a vector.
     */
    public Vector2 getVelocity()
    {
        return velocity;
    }

    /**
     * Sets the velocity of the game object as a vector.
     * 
     * @param velocity
     *            The velocity of the game object as a vector.
     */
    public void setVelocity(Vector2 velocity)
    {
        this.velocity = velocity;

    }

    /**
     * Updates the velocity based on all the direction vectors.
     */
    private void updateVelocity()
    {
        velocity = Vector2.averageVector(directionVectors.toArray(new Vector2[directionVectors.size()]));
        velocity.setMagnitude(topspeed);
    }

    /**
     * Sets the top speed of the game object. i.e 400 would be 400 units per second, where units is pixels on the default display.
     * 
     * @param topSpeed
     *            The top speed of the game object, i.e 400 would be 400 units per second, where units is pixels on the default display.
     */
    public void setTopSpeed(double topSpeed)
    {
        topspeed = topSpeed;
    }

    /**
     * Adds a direction to the game objects velocity, this direction will be averaged in with all the other directions requested on the velocity.
     * 
     * @param direction
     *            The direction to be added.
     */
    public void addDirection(Vector2 direction)
    {
        if (!directionVectors.contains(direction))
        {
            directionVectors.add(direction);
            updateVelocity();
        }

    }

    /**
     * Removes a direction from the game objects velocity, a direction can only be removed if it was added beforehand.
     * 
     * @param direction
     *            The direction to be removed.
     */
    public void removeDirection(Vector2 direction)
    {

        directionVectors.remove(direction);

        updateVelocity();
    }

    @Override
    public String toString()
    {
        return String.format("Physics [velocity=%s, topspeed=%s]", velocity, topspeed);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Physics other = (Physics)obj;
        if (directionVectors == null)
        {
            if (other.directionVectors != null)
                return false;
        }
        else if (!directionVectors.equals(other.directionVectors))
            return false;
        if (topspeed != other.topspeed)
            return false;
        if (velocity == null)
        {
            if (other.velocity != null)
                return false;
        }
        else if (!velocity.equals(other.velocity))
            return false;
        return true;
    }

    /**
     * Gets the top speed of the game object.
     * 
     * @return The top speed of the game object.
     */
    public double getTopspeed()
    {
        return this.topspeed;
    }

}

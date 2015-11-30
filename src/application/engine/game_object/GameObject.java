package application.engine.game_object;

import java.util.Observable;
import java.util.concurrent.ConcurrentMap;

import application.engine.game_object.physics.Physics;
import application.util.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * A game object is all objects that can be in a game. A ClientMap usually handles game objects without knowing who might be extending the
 * game object.
 * 
 * @author Gruppe6
 *
 */
public class GameObject extends Observable
{

    protected Body body;
    protected Physics physics;
    protected View view;
    protected Health health;
    protected Weapon weapon;
    private int id;
    private boolean destroyed;

  

    /**
     * Creates a copy of a game object based on another game object.
     *
     * @param gameObject
     *            The game object to be copied.
     */
    public GameObject(GameObject gameObject)
    {
        this.id = gameObject.getId();
        if (gameObject.getPhysics() != null)
        {
            this.setPhysics(new Physics(this, gameObject.getPhysics().getTopspeed()));
            this.getPhysics().setVelocity(new Vector2(gameObject.getPhysics().getVelocity().getX(), gameObject.getPhysics().getVelocity().getY()));
        }
        if (gameObject.getBody() != null)
        {
            this.setBody(new Body(this, gameObject.getBody().getPosition(), gameObject.getBody().getHeight(), gameObject.getBody().getWidth(),
                    gameObject.getBody().getType()));
        }
    }

    

    /**
     * Creates a dummy game object with a id.
     * @param id The id of the dummy game object.
     */
    public GameObject(int id)
    {
        this.id = id;
    }

    /**
     * Gets the id of the game object.
     * @return The id of the game object.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Updates the game object, such as its movement and how its rendered on the graphics context.
     * @param gc The graphics context the game object is being rendered on.
     */
    public void update(GraphicsContext gc)
    {
        if (view != null)
        {
            view.draw(gc); 
        }
        if (getPhysics() != null) // TODO Figure out why there is visual bug on bullets when character is moving, if the view is updated after physics.
        {
            getPhysics().update();
        }
    }

    /**
     * Updates the game object, such as its movement and how its rendred on the grpaihcs context. In this update collisions are taking into consideration. 
     * @param gc The graphics context the game object is being rendered on.
     * @param objects The possible objects the game object could be colliding with.
     */
    public void updateWithCollision(GraphicsContext gc, ConcurrentMap<Integer, GameObject> objects)
    {
        if(view != null)
        {
            view.draw(gc);
        }
        if (physics != null)
        {
            getPhysics().updateWithCollision(objects);
        }
    }
    
    

    /**
     * Checks if the game object is destroyed. This would for example result in it no longer being drawn etc.
     * @return Returns true if the game object is destroyed.
     */
    public boolean isDestroyed()
    {
        return destroyed; 
    }

    /**
     * Destroys the object resulting in other components handling this object knowing its destroyed.
     */
    public void destroy()
    {
        if(destroyed)
        {
            return;
        }
        destroyed = true; 
        setChanged();
        notifyObservers();
    }

    /**
     * Gets the current weapon of the game object.
     * @return The weapon of the game object.
     */
    public Weapon getWeapon()
    {
        return weapon;
    }

    /**
     * Sets the weapon for the game object.
     * @param weapon The weapon to be set for the game object.
     */
    public void setWeapon(Weapon weapon)
    {
        this.weapon = weapon;
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        GameObject other = (GameObject)obj;
        if (id != other.id) return false;
        return true;
    }

    /**
     * Gets the current physics of the game object.
     * @return The physics of the game object.
     */
    public Physics getPhysics()
    {
        return physics;
    }

    /**
     * Sets the physics for the game object.
     * @param physics The physics to be set for the game object.
     */
    public void setPhysics(Physics physics)
    {
        this.physics = physics;
    }
    
    /**
     * Gets the current body of the game object.
     * @return The body of the game object.
     */
    public Body getBody()
    {
        return body;
    }

    /**
     * Sets the body for the game object.
     * @param body The body to be set for the game object.
     */
    public void setBody(Body body)
    {
        this.body = body;
    }
    /**
     * Gets the current view of the game object.
     * @return The view of the game object.
     */
    public View getView()
    {
        return view;
    }

    /**
     * Sets the view for the game object.
     * @param view The view to be set for the game object.
     */
    public void setView(View view)
    {
        this.view = view;
    }
    /**
     * Gets the current health of the game object.
     * @return The health of the game object.
     */
    public Health getHealth()
    {
        return health;
    }

    /**
     * Sets the health for the game object.
     * @param health The health to be set for the game object.
     */
    public void setHealth(Health health)
    {
        this.health = health;
    }


    /**
     * Sets the id for the game object.
     * @param id The id for the game object.
     */
    public void setId(int id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return String.format("GameObject [body=%s, physics=%s, health=%s, weapon=%s, id=%s, destroyed=%s]", body, physics,  health,
                weapon, id, destroyed);
    }

    
    
    


}

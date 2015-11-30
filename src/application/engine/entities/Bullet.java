package application.engine.entities;

import java.util.HashMap;
import java.util.Map;

import application.engine.game_object.Body;
import application.engine.game_object.GameObject;
import application.engine.game_object.View;
import application.engine.game_object.physics.Physics;
import application.engine.rendering.TranslatedPoint;
import application.util.Timer;
import application.util.Vector2;
import javafx.scene.image.Image;

/**
 * A bullet can damage other objects with health.
 * 
 * @author Gruppe6
 *
 */
public class Bullet extends GameObject
{

    public enum Type
    {
        RIFLE, PISTOL;

        private static Map<Integer, Type> values = new HashMap<>();

        static
        {

            values.put(PISTOL.ordinal(), PISTOL);
            values.put(RIFLE.ordinal(), RIFLE);
        }

        /**
         * Returns a bullet type based on an int.
         * 
         * @param x
         *            The integer the bullet type is based on.
         * @return A bullet type based on an int.
         */
        public static Type fromInteger(int x)
        {

            return values.get(x);
        }

    }

    private final Type type;
    private int damage;
    private Timer timer;
    private int lifeTime;
    public static final Body.Geometry BODYTYPE = Body.Geometry.CIRCLE;
    private int ownerId;

    /**
     * Creates a bullet with a body, physics, view and damage.
     * 
     * @param id
     *            The id of the bullet.
     * @param position
     *            The starting position of the bullet.
     * @param height
     *            The height of the bullet.
     * @param width
     *            The width of the bullet.
     * @param velocity
     *            The velocity of the bullet.
     * @param damage
     *            The amount of health reduced on another object if this bullet collides with the object.
     */
    public Bullet(int id, TranslatedPoint position, double radius, Vector2 velocity, int damage, Type type, Image image, int ownerId)
    {
        super(id);
        this.ownerId = ownerId;
        this.type = type;
        this.damage = damage;
        this.setBody(new Body(this, position, radius, radius, BODYTYPE));
        this.setPhysics(new Physics(this, velocity.getMagnitude()));
        getPhysics().setVelocity(velocity);
        this.view = new View(this, image);
        lifeTime = 5;
        new Thread(() ->
        {
            timer = new Timer();
            timer.start();
            while ((timer.getDuration() < 1000 * lifeTime))
            {
                try
                {
                    Thread.sleep(20);
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            setChanged();
            notifyObservers();
        }).start();
        ;

    }

    /**
     * Gets the damage of this bullet. I.e The amount of health reduced on another object if this bullet collides with the object.
     * 
     * @return
     */
    public int getDamage()
    {
        return damage;
    }

    public int getOwnerId()
    {
        return ownerId;
    }

    /**
     * Gets the type of bullet.
     * 
     * @return The type of bullet.
     */
    public Type getType()
    {
        return this.type;
    }
}

package application.engine.game_object;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import application.engine.entities.Bullet;
import application.engine.entities.Bullet.Type;
import application.util.Timer;
import application.util.Vector2;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * The weapon takes care of spawning bullets.
 * @author Gruppe6
 *
 */
public class Weapon extends Observable implements Observer
{

    private double firerate;
    private int magazineSize;
    private GameObject gameObject;
    private double damage;
    private boolean shooting = false;
    private Set<Bullet> activeBullets;
    Timer timer;
    private static Map<Type, Image> bulletImages;
    static
    {
        bulletImages = new HashMap<>();
        bulletImages.put(Type.RIFLE, new Image("images/silver_ball.png"));
    }

    /**
     * Creates a weapon for the game object.
     * @param gameObject The game object this weapon belongs to.
     * @param firerate
     *            How many bullets the weapon shoots per second.
     * @param magazineSize The size of the magazine. Example: Value of 30 would result in a need to reload every time 30 bullets was spawned.
     * @param damage
     *            Damage per bullet.
     */
    public Weapon(GameObject gameObject, double firerate, int magazineSize, double damage)
    {
        activeBullets = new HashSet<Bullet>();
        timer = new Timer();
        timer.start();
        this.gameObject = gameObject;
        this.firerate = firerate;
        this.damage = damage;
        this.magazineSize = magazineSize;
    }

    int bulletsCreated = 500; // FIXME need better way of handling ID.

    /**
     * The weapons starts shooting bullets by its firerate.
     */
    public void startShooting()
    {

        if(timer.getDuration()<(1000 / firerate))
        {
            return;
        }
        timer.reset();
        
        shooting = true;
        new Thread(() ->
        {
            while (shooting)
            {

                Vector2 orientation = gameObject.getBody().getOrientation().setMagnitude(gameObject.getBody().getHeight() / 2);
                Point2D position = new Point2D(gameObject.getBody().getCenter().getX() + orientation.getX(),
                        gameObject.getBody().getCenter().getY() + orientation.getY());
                Vector2 velocity = new Vector2(gameObject.getBody().getOrientation());
                velocity.setMagnitude(300); // bullet speed magic number atm
               
                Bullet bullet = new Bullet(bulletsCreated++, position, 10, 10, velocity, damage, Bullet.Type.RIFLE,bulletImages.get(Bullet.Type.RIFLE));
                setChanged();
                notifyObservers(bullet); // Det her skal ske med noget server shit.
                System.out.println(this.countObservers());
                activeBullets.add(bullet);
                bullet.addObserver(this);
               
                
                try
                {
                    Thread.sleep((long) (1000 / firerate));
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }).start();

    }

    /**
     * The weapon stops shooting bullets.
     */
    public void stopShooting()
    {
        shooting = false;
    }
    /**
     * The current active bullets of the weapon. I.e the bullets that are still in the air that this weapon has created.
     * @return Returns the active bullets this weapon has created.
     */
    public Set<Bullet> getActiveBullets()
    {
        return activeBullets;
    }


    
   
    @Override
    public String toString()
    {
        return String.format("Weapon [firerate=%s, magazineSize=%s, gameObject=%s, damage=%s, shooting=%s, activeBullets=%s]", firerate, magazineSize,
                gameObject, damage, shooting, activeBullets);
    }

    @Override
    public void update(Observable observable, Object args)
    {
        if(observable instanceof Bullet)
        {
            activeBullets.remove(observable);
        }
        
    }
}

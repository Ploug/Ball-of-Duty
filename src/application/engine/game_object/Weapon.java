package application.engine.game_object;

import java.util.HashMap;
import java.util.Map;

import application.engine.entities.Bullet;
import application.engine.entities.Bullet.Type;
import application.engine.rendering.TranslatedPoint;
import application.util.Observable;
import application.util.Observation;
import application.util.Timer;
import application.util.Vector2;
import javafx.scene.image.Image;

/**
 * The weapon takes care of spawning bullets.
 * 
 * @author Gruppe6
 *
 */
public class Weapon extends Observable
{

    private double fireRate;
    private double bulletSpeed;
    private int bulletDiameter;
    private int reloadSpeed;
    private int magazineMaxSize;
    private int magazineSize;
    private int damage;
    private GameObject gameObject;

    private boolean reloading = false;
    private boolean shooting = false;
    Timer timer;
    private static Map<Type, Image> bulletImages;

    static
    {
        bulletImages = new HashMap<>();
        bulletImages.put(Type.RIFLE, new Image("images/ball_black.png"));
    }

    /**
     * Creates a weapon for the game object.
     * 
     * @param gameObject
     *            The game object this weapon belongs to.
     * @param firerate
     *            How many bullets the weapon shoots per second.
     * @param magazineSize
     *            The size of the magazine. Example: Value of 30 would result in
     *            a need to reload every time 30 bullets was spawned.
     * @param damage
     *            Damage per bullet.
     */
    public Weapon(GameObject gameObject, double firerate, int magazineMaxSize, int damage, double bulletSpeed,
            int reloadSpeed, int bulletDiameter)
    {
        timer = new Timer();
        timer.start();
        this.gameObject = gameObject;
        this.fireRate = firerate;
        this.damage = damage;
        this.bulletSpeed = bulletSpeed;
        this.reloadSpeed = reloadSpeed;
        this.magazineMaxSize = magazineMaxSize;
        this.magazineSize = magazineMaxSize;
        this.bulletDiameter = bulletDiameter;
    }

    /**
     * The weapons starts shooting bullets by its firerate.
     */
    public void startShooting()
    {
        if (timer.getDuration() < (1000 / fireRate) || shooting)
        {
            return;
        }

        shooting = true;
        timer.reset();
        new Thread(() ->
        {

            while (shooting)
            {
                if (!reloading)
                {
                    Vector2 orientation = gameObject.getBody().getOrientation()
                            .setMagnitude(gameObject.getBody().getHeight() / 2);
                    TranslatedPoint position = new TranslatedPoint(
                            gameObject.getBody().getCenter().getX() + orientation.getX(),
                            gameObject.getBody().getCenter().getY() + orientation.getY());
                    Vector2 velocity = new Vector2(gameObject.getBody().getOrientation());
                    velocity.setMagnitude(bulletSpeed);
                    Bullet bullet = new Bullet(0, position, bulletDiameter, velocity, damage, Bullet.Type.RIFLE,
                            bulletImages.get(Bullet.Type.RIFLE), gameObject.getId());
                    bullet.getBody().setCenter(position);
                    notifyObservers(Observation.SPAWNING, bullet);
                    magazineSize--;
                }
                try
                {
                    Thread.sleep((long)(1000 / fireRate));
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                if (magazineSize < 1)
                {
                    reload();
                }

            }
        }).start();

    }

    public void reload()
    {
        if (!reloading&&magazineSize < magazineMaxSize )
        {
            reloading = true;
            new Thread(() ->
            {
                try
                {
                    Thread.sleep(reloadSpeed);
                }
                catch (InterruptedException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                magazineSize = magazineMaxSize;
                reloading = false;
            }).start();
        }
    }

    /**
     * The weapon stops shooting bullets.
     */
    public void stopShooting()
    {
        shooting = false;
    }

    public static Map getBulletImages()
    {
        return bulletImages;
    }

    /**
     * The current active bullets of the weapon. I.e the bullets that are still
     * in the air that this weapon has created.
     * 
     * @return Returns the active bullets this weapon has created.
     */

    public void setDamage(int damage)
    {
        this.damage = damage;
    }

    public void setFireRate(double fireRate)
    {
        this.fireRate = fireRate;
    }

    public void setBulletSpeed(double bulletSpeed)
    {
        this.bulletSpeed = bulletSpeed;
    }

    public void setMagazineMaxSize(int magazineMaxSize)
    {
        this.magazineMaxSize = magazineMaxSize;
    }

    public int getMagazineMaxSize()
    {
        return this.magazineMaxSize;
    }

    public int getMagazineSize()
    {
        return this.magazineSize;
    }

    public boolean getReloading()
    {
        return this.reloading;
    }

    public void setReloadSpeed(int reloadSpeed)
    {
        this.reloadSpeed = reloadSpeed;
    }

    @Override
    public String toString()
    {
        return String.format("Weapon [firerate=%s, magazineSize=%s, gameObject=%s, damage=%s, shooting=%s,]", fireRate,
                magazineSize, gameObject, damage, shooting);
    }

}

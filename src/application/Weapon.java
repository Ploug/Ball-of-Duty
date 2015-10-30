package application;

import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Point2D;

public class Weapon implements Observable, Observer
{

    private double firerate;
    private Bullet bullet;
    private int magazineSize;
    private GameObject gameObject;
    private double damage;
    private boolean shooting = false;
    private Set<Bullet> activeBullets;
    Timer timer;
    private Set<Observer> observers;

    /**
     * 
     * @param gameObject
     * @param firerate
     *            How many bullets the weapon shoots per second.
     * @param bullet
     * @param magazineSize
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
        observers = new HashSet<>();
    }

    int bulletsCreated = 500;

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

                Vector2 orientation = gameObject.body.getOrientation().setMagnitude(gameObject.body.getHeight() / 2);
                Point2D position = new Point2D(gameObject.body.getCenter().getX() + orientation.getX(),
                        gameObject.body.getCenter().getY() + orientation.getY());
                Vector2 velocity = new Vector2(gameObject.body.getOrientation());
                velocity.setMagnitude(300); // bullet speed magic number atm
               
                Bullet bullet = new Bullet(bulletsCreated++, position, 10, 10, velocity, damage);
                notifyObservers(bullet); // Det her skal ske med noget server shit.
                activeBullets.add(bullet);
                bullet.registerObserver(this);
               
                
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

    public void stopShooting()
    {
        shooting = false;
    }
    public Set<Bullet> getActiveBullets()
    {
        return activeBullets;
    }

    @Override
    public void notifyObservers(Object arg)
    {
        for (Observer obs : observers)
        {
            obs.update(this, arg);
        }

    }

    @Override
    public void notifyObservers()
    {
        for (Observer obs : observers)
        {
            obs.update(this, null);
        }

    }

    @Override
    public void registerObserver(Observer obs)
    {
        observers.add(obs);

    }

    @Override
    public void unregisterObserver(Observer obs)
    {
        observers.remove(obs);

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

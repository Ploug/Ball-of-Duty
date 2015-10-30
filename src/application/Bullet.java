package application;

import javafx.geometry.Point2D;

public class Bullet extends GameObject
{

    double damage;
    private Timer timer;
    private int lifeTime;

    public Bullet(int id, Point2D position, double length, double width, Vector2 velocity, double damage)
    {
        super(id, Body.Type.CIRCLE);
        this.damage = damage;
        this.body = new Body(this, position, length, width, Body.Type.CIRCLE);
        this.physics = new Physics(this, (int) velocity.getMagnitude());
        physics.setVelocity(velocity);
        this.view = new View(this);
        lifeTime = 5;
        new Thread(()->
        {
            timer = new Timer();
            timer.start();
            while((timer.getDuration()<1000*lifeTime))
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
            System.out.println("removed");
            notifyObservers();
        }).start();;
        
    }

    public double getDamage()
    {
        return damage;
    }
    

}

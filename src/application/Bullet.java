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
        timer = new Timer();
        timer.start();
    }
    public boolean livedTooLong()
    {
        return(timer.getDuration()>1000*lifeTime);
    }

    public double getDamage()
    {
        return damage;
    }

}

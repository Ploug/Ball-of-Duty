package application;

import java.awt.geom.Point2D;

public class Bullet extends GameObject
{

    public Bullet(int id, Point2D.Double position, double length, double width, int speed)
    {
        super(id, Body.Type.CIRCLE);
        this.body = new Body(this, position, length, width,Body.Type.CIRCLE);
        this.physics = new Physics(this, speed);
        this.view = new View(this);
    }

}

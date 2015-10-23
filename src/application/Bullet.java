package application;

import java.awt.geom.Point2D;

public class Bullet extends GameObject {

	public Bullet(int id, Point2D.Double position, double length, double width, int speed)
    {
        super(id, "Bullet");
        this.body = new Body(this, position, length, width);
        this.physics = new Physics(this, speed);
        this.view = new View(this);
    }

}

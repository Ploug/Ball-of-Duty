package application;

import javafx.geometry.Point2D;

public class Wall extends GameObject {

	public Wall(int id, Point2D position, double length, double width, int speed)
    {
        super(id, Body.Type.RECTANGLE);
        this.body = new Body(this, position, length, width, Body.Type.RECTANGLE);
        this.physics = new Physics(this, speed);
        this.view = new View(this);
    }
}

package application;

import java.awt.geom.Point2D;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

public class BoDCharacter extends GameObject
{
    public MousePosition mousePosition;

    public BoDCharacter(GameObjectDTO sgo)
    {
        super(sgo, Body.Type.CIRCLE);
        this.body = new Body(this, new Point2D.Double(50, 50), 50, 50,Body.Type.CIRCLE);
        this.physics = new Physics(this, 200);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

    public BoDCharacter(int id, Point2D.Double position, double length, double width, int speed)
    {
        super(id, Body.Type.CIRCLE);
        this.body = new Body(this, position, length, width,Body.Type.CIRCLE);
        this.physics = new Physics(this, speed);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

    public BoDCharacter(int id)
    {
        super(id, Body.Type.CIRCLE);
        this.body = new Body(this, new Point2D.Double(10, 10), 50, 50,Body.Type.CIRCLE);
        this.physics = new Physics(this, 200);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

}

package application;

import java.awt.geom.Point2D;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

public class BoDCharacter extends GameObject
{
    int speed = 200;
    Point2D.Double startPosition;
    public MousePosition mousePosition;

    public BoDCharacter(GameObjectDTO sgo)
    {
        super(sgo);
        startPosition = new Point2D.Double(50,50);
        this.physics = new Physics(this, speed);
        this.body = new Body(this, startPosition);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }
    public BoDCharacter(int id)
    {
        super(id);
        startPosition = new Point2D.Double(50,50);
        this.physics = new Physics(this, speed);
        this.body = new Body(this, startPosition);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

}

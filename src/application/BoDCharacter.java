package application;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import javafx.geometry.Point2D;

public class BoDCharacter extends GameObject
{
    public MousePosition mousePosition;

    public BoDCharacter(GameObjectDTO sgo)
    {
        super(sgo.getId());
        this.body = new Body(this, new Point2D(50, 50), 50, 50, Body.Type.CIRCLE);
        this.physics = new Physics(this, 200);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

    public BoDCharacter(int id, Point2D position, double length, double width, int speed)
    {
        super(id);
        this.body = new Body(this, position, length, width, Body.Type.CIRCLE);
        this.physics = new Physics(this, speed);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
        this.weapon = new Weapon(this,5, 20, 10); // Default weapon would be created per spec
        this.health = new Health(100); 
        
        
    }

    public BoDCharacter(int id)
    {
        super(id);
        this.body = new Body(this, new Point2D(10, 10), 50, 50, Body.Type.CIRCLE);
        this.physics = new Physics(this, 200);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

}

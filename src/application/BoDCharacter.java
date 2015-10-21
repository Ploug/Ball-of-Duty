package application;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Domain.ServerGameObject;
import org.datacontract.schemas._2004._07.System_Windows.Point;

public class BoDCharacter extends GameObject
{
    int speed = 200;
    Point startPosition;
    public MousePosition mousePosition;

    public BoDCharacter(ServerGameObject sgo)
    {
        super(sgo);
        startPosition = new Point();
        startPosition.set_x(50);
        startPosition.set_y(50);
        this.physics = new Physics(this, speed);
        this.body = new Body(this, startPosition);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }
    public BoDCharacter(int id)
    {
        super(id);
        startPosition = new Point();
        startPosition.set_x(50);
        startPosition.set_y(50);
        this.physics = new Physics(this, speed);
        this.body = new Body(this, startPosition);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

}

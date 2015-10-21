package application;

import org.datacontract.schemas._2004._07.ball_of_duty_server.ServerGameObject;
import org.datacontract.schemas._2004._07.system.Point;

public class BoDCharacter extends GameObject
{
    int speed = 200;
    Point startPosition;
    public MousePosition mousePosition;

    public BoDCharacter(ServerGameObject sgo)
    {
        super(sgo);
    }
    public BoDCharacter(int id)
    {
        super(id);
        startPosition = new Point();
        startPosition.setX(50);
        startPosition.setY(50);
        this.physics = new Physics(this, speed);
        this.body = new Body(this, startPosition);
        this.view = new View(this);
        this.mousePosition = new MousePosition();
    }

}

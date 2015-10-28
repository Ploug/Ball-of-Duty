package application;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;

import javafx.geometry.Point2D;

public class Wall extends GameObject {

    public Wall(GameObjectDTO sgo)
    {
        super(sgo.getId(), Body.Type.RECTANGLE);
        Point2D position = new Point2D(sgo.getBody().get_point().getX(),sgo.getBody().get_point().getY());
        this.body = new Body(this, position, sgo.getBody().get_width(),sgo.getBody().get_height(), Body.Type.RECTANGLE);
        this.view = new View(this);
        
    }
	public Wall(int id, Point2D position, double length, double width, int speed)
    {
        super(id, Body.Type.RECTANGLE);
        this.body = new Body(this, position, length, width, Body.Type.RECTANGLE);
        this.view = new View(this);
    }
}

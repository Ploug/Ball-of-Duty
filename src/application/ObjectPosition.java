package application;

import javafx.geometry.Point2D;

public class ObjectPosition {
	public final int Id;
	public final Point2D Position;
	
	public ObjectPosition(int id, Point2D position) 
	{
		this.Id = id;
		this.Position = position;
	}
	
	public int getId() 
	{
		return Id;
	}
	
	public Point2D getPosition()
	{
		return Position;
	}
	
}

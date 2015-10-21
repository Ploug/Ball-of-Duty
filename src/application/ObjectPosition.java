package application;
import java.awt.geom.Point2D;

import org.datacontract.schemas._2004._07.system.Point;

public class ObjectPosition {
	public final int Id;
	public final Point Position;
	
	public ObjectPosition(int id, Point position) 
	{
		this.Id = id;
		this.Position = position;
	}
	
	public int getId() 
	{
		return Id;
	}
	
	public Point getPosition()
	{
		return Position;
	}
	
}

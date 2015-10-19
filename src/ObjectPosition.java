import java.awt.geom.Point2D;

public class ObjectPosition {
	public final int Id;
	public final Point2D.Double Position;
	
	public ObjectPosition(int id, Point2D.Double position) 
	{
		this.Id = id;
		this.Position = position;
	}
	
	public int getId() 
	{
		return Id;
	}
	
	public Point2D.Double getPosition()
	{
		return Position;
	}
	
}

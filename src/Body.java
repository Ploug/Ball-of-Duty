import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Body
{
    public GameObject gameObject;
    private int x;
    private int y;
    private Point2D.Double position;

    public Body(GameObject gO)
    {
        this.gameObject = gO;
    }
    
    public void collidesWith(GameObject gO)
    {
        
    }
    
    public Point2D.Double getPosition()
    {
    	return position;
    }
    
    public void setPosition(Point2D.Double position)
    {
    	this.position = position;
    }
}

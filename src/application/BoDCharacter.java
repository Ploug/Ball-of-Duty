package application;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.HashSet;

public class BoDCharacter extends GameObject
{
    HashSet<Integer> movementKeys;
	public BoDCharacter(Graphics2D g2d)
	{
	    movementKeys = new HashSet<>();
	    movementKeys.add(Keys.A);
        movementKeys.add(Keys.S);
        movementKeys.add(Keys.D);
        movementKeys.add(Keys.W);
        movementKeys.add(Keys.UP);
        movementKeys.add(Keys.DOWN);
        movementKeys.add(Keys.LEFT);
        movementKeys.add(Keys.RIGHT);
        
		this.physics = new Physics(this);
		this.body = new Body(this);
		this.view = new View(this, g2d);
	}
	public void KeyPressed(KeyEvent e)
	{
	    int code = e.getKeyCode();
		if(code == Keys.W|| code == Keys.UP)
		{
			physics.changeDirection(new Vector2(0,-1));
		}
		else if(code == Keys.S|| code == Keys.DOWN)
        {
		    physics.changeDirection(new Vector2(0,1));
        }
		else if(code == Keys.A|| code == Keys.LEFT)
        {
		    physics.changeDirection(new Vector2(-1,0));
        }
		else if(code == Keys.D|| code == Keys.RIGHT)
        {
		    physics.changeDirection(new Vector2(1,0));
        }
	}
	public void KeyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        if(movementKeys.contains(code))
        {
            physics.changeDirection(new Vector2(0,0)); // Or maybe do something else if this breaks movement.
        }
    }
}

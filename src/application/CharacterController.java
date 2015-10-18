package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class CharacterController implements KeyListener
{
    private BoDCharacter character;
    private static final Vector2 UP_VECTOR = new Vector2(0, -1);
    private static final Vector2 DOWN_VECTOR = new Vector2(0, 1);
    private static final Vector2 RIGHT_VECTOR = new Vector2(1, 0);
    private static final Vector2 LEFT_VECTOR = new Vector2(-1, 0);

    public CharacterController(BoDCharacter inputChar)
    {
        character = inputChar;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code == Keys.W || code == Keys.UP)
        {
            character.physics.addDirection(UP_VECTOR);
        }
        else if (code == Keys.S || code == Keys.DOWN)
        {
            character.physics.addDirection(DOWN_VECTOR);
        }
        else if (code == Keys.A || code == Keys.LEFT)
        {
            character.physics.addDirection(LEFT_VECTOR);
        }
        else if (code == Keys.D || code == Keys.RIGHT)
        {
            character.physics.addDirection(RIGHT_VECTOR);
        }

    }

    @Override
    public void keyReleased(KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code == Keys.W || code == Keys.UP)
        {
            character.physics.removeDirection(UP_VECTOR);
        }
        else if (code == Keys.S || code == Keys.DOWN)
        {
            character.physics.removeDirection(DOWN_VECTOR);
        }
        else if (code == Keys.A || code == Keys.LEFT)
        {
            character.physics.removeDirection(LEFT_VECTOR);
        }
        else if (code == Keys.D || code == Keys.RIGHT)
        {
            character.physics.removeDirection(RIGHT_VECTOR);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        // TODO Auto-generated method stub

    }
}

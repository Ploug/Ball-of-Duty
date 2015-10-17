package application;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player
{
    public BoDCharacter character;
    private int id;
    private String IP;
    private boolean clientControlled;
    private KeyListener listener;

    public Player(boolean clientControlled, int id)
    {
        this.id = id;
        this.clientControlled = clientControlled;
        if (clientControlled)
        {
            listener = new KeyListener()
            {

                @Override
                public void keyPressed(KeyEvent e)
                {
                    if (character != null)
                    {
                        character.KeyPressed(e);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e)
                {

                    if (character != null)
                    {
                        character.KeyReleased(e);
                    }
                }

                @Override
                public void keyTyped(KeyEvent e)
                {

                }

            };

        }

    }

    public KeyListener getKeyListener()
    {
        return listener;
    }

    public void setCharacter(BoDCharacter character)
    {
        this.character = character;
    }
}

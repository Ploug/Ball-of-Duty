package application;

import java.util.HashMap;

/**
 * Handles keybindings and keyrelated logic.
 * 
 * @author Frederik
 *
 */
public class KeyHandler
{
    public enum Action
    {
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT,SHOOT;
    }

    public HashMap<Integer, Action> keyMap;

    /**
     * Implements default keybindings, should possibly be in a config file.
     */
    public KeyHandler()
    {
        keyMap.put(Keys.W, Action.MOVE_UP);
        keyMap.put(Keys.UP, Action.MOVE_UP);
        keyMap.put(Keys.S, Action.MOVE_DOWN);
        keyMap.put(Keys.DOWN, Action.MOVE_DOWN);
        keyMap.put(Keys.A, Action.MOVE_LEFT);
        keyMap.put(Keys.LEFT, Action.MOVE_LEFT);
        keyMap.put(Keys.D, Action.MOVE_RIGHT);
        keyMap.put(Keys.RIGHT, Action.MOVE_RIGHT);
    }
}

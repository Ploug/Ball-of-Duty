package application.input;

import java.util.HashMap;

import javafx.scene.input.KeyCode;

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
        MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, SHOOT, BLINK, RELOAD, PAUSE_MENU;
    }

    public HashMap<KeyCode, Action> keyMap;

    /**
     * Implements default keybindings, should possibly be in a config file.
     */
    public KeyHandler()
    {
        keyMap = new HashMap<>();
        keyMap.put(KeyCode.R, Action.RELOAD);
        keyMap.put(KeyCode.SPACE, Action.BLINK);
        keyMap.put(KeyCode.W, Action.MOVE_UP);
        keyMap.put(KeyCode.UP, Action.MOVE_UP);
        keyMap.put(KeyCode.S, Action.MOVE_DOWN);
        keyMap.put(KeyCode.DOWN, Action.MOVE_DOWN);
        keyMap.put(KeyCode.A, Action.MOVE_LEFT);
        keyMap.put(KeyCode.LEFT, Action.MOVE_LEFT);
        keyMap.put(KeyCode.D, Action.MOVE_RIGHT);
        keyMap.put(KeyCode.RIGHT, Action.MOVE_RIGHT);
        keyMap.put(KeyCode.ESCAPE, Action.PAUSE_MENU);
    }

    /**
     * Gets the action based on a key code. For example KeyCode.W would result in action MOVE_UP.
     * 
     * @param code
     *            The keycode of which an action should be connected with.
     * @return The action the key was related to.
     */
    public Action getAction(KeyCode code)
    {
        return keyMap.get(code);
    }
}

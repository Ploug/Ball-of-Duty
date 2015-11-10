package application.communication;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Handles operation codes for communication between client and server.
 * 
 * @author Gruppe 6
 *
 */
public enum Opcodes
{

    BROADCAST_POSITION_UPDATE(1), POSITION_UPDATE(2), REQUEST_BULLET(3), NEW_PLAYER(4), DISCONNECTED_PLAYER(5), BROADCAST_SCORE_UPDATE(6), BROADCAST_HEALTH_UPDATE(7);

    private int value;

    private static Map<Integer, Opcodes> values = new HashMap<>();

    static
    {

        values.put(1, BROADCAST_POSITION_UPDATE);
        values.put(2, POSITION_UPDATE);
        values.put(3, REQUEST_BULLET);
        values.put(4, NEW_PLAYER);
        values.put(5, DISCONNECTED_PLAYER);
        values.put(6, BROADCAST_SCORE_UPDATE);
        values.put(7, BROADCAST_HEALTH_UPDATE);
    }

    /**
     * For constructing a enum with specific value.
     * 
     * @param value
     */
    private Opcodes(int value)
    {
        this.value = value;
    }

    public static Opcodes fromInteger(int x) // Better for performance
    {

        return values.get(x);
    }

    /**
     * Returns the int value of an enum.
     * 
     * @return The int value of an enum.
     */
    public int getValue()
    {
        return value;
    }

}

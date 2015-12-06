package application.communication;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles operation codes for communication between client and server.
 * 
 * @author Gruppe 6
 *
 */
public enum Opcodes
{
    BROADCAST_POSITION_UPDATE(1),
    POSITION_UPDATE(2),
    REQUEST_BULLET(3),
    NEW_PLAYER(4),
    DISCONNECTED_PLAYER(5),
    BROADCAST_SCORE_UPDATE(6),
    BROADCAST_HEALTH_UPDATE(7),
    KILL_NOTIFICATION(8),
    OBJECT_DESTRUCTION(9),
    PING(10),
    UDP_CONNECT(11);

    private int value;

    private static Map<Integer, Opcodes> values = new HashMap<>();

    static
    {
        values.put(BROADCAST_POSITION_UPDATE.value, BROADCAST_POSITION_UPDATE);
        values.put(POSITION_UPDATE.value, POSITION_UPDATE);
        values.put(REQUEST_BULLET.value, REQUEST_BULLET);
        values.put(NEW_PLAYER.value, NEW_PLAYER);
        values.put(DISCONNECTED_PLAYER.value, DISCONNECTED_PLAYER);
        values.put(BROADCAST_SCORE_UPDATE.value, BROADCAST_SCORE_UPDATE);
        values.put(BROADCAST_HEALTH_UPDATE.value, BROADCAST_HEALTH_UPDATE);
        values.put(KILL_NOTIFICATION.value, KILL_NOTIFICATION);
        values.put(OBJECT_DESTRUCTION.value, OBJECT_DESTRUCTION);
        values.put(PING.value, PING);
        values.put(UDP_CONNECT.value, UDP_CONNECT);
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

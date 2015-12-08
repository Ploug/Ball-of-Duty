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
    REQUEST_BULLET(4),
    NEW_PLAYER(8),
    DISCONNECTED_PLAYER(16),
    BROADCAST_CHARACTER_STAT_UPDATE(32),
    KILL_NOTIFICATION(64),
    OBJECT_DESTRUCTION(128),
    PING(256),
    UDP_CONNECT(512),
    SERVER_MESSAGE(1024);

    private int value;

    private static Map<Integer, Opcodes> values = new HashMap<>();

    static
    {
        values.put(BROADCAST_POSITION_UPDATE.value, BROADCAST_POSITION_UPDATE);
        values.put(POSITION_UPDATE.value, POSITION_UPDATE);
        values.put(REQUEST_BULLET.value, REQUEST_BULLET);
        values.put(NEW_PLAYER.value, NEW_PLAYER);
        values.put(DISCONNECTED_PLAYER.value, DISCONNECTED_PLAYER);
        values.put(BROADCAST_CHARACTER_STAT_UPDATE.value, BROADCAST_CHARACTER_STAT_UPDATE);
        values.put(KILL_NOTIFICATION.value, KILL_NOTIFICATION);
        values.put(OBJECT_DESTRUCTION.value, OBJECT_DESTRUCTION);
        values.put(PING.value, PING);
        values.put(UDP_CONNECT.value, UDP_CONNECT);
        values.put(SERVER_MESSAGE.value,  SERVER_MESSAGE);
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

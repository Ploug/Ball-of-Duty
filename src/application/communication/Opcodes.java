package application.communication;

/**
 * Handles operation codes for communication between client and server.
 * 
 * @author Gruppe 6
 *
 */
public enum Opcodes 
{

    BROADCAST_POSITION_UPDATE(1), POSITION_UPDATE(2), REQUEST_BULLET(3); 

    private int _code;

    /**
     * For constructing a enum with specific value.
     * 
     * @param code
     */
    private Opcodes(int code)
    {
        this._code = code;
    }

    /**
     * Returns the int value of an enum.
     * 
     * @return The int value of an enum.
     */
    public int getCode()
    {
        return _code;
    }

}

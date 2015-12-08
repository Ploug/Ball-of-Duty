package Exceptions;
public class BadVersionException extends Exception
{
    /**
     * 
     */
    private static final long serialVersionUID = 918429155337007659L;

    // Parameterless Constructor
    public BadVersionException()
    {
    }

    // Constructor that accepts a message
    public BadVersionException(String message)
    {
        super(message);
    }
}
package Exceptions;

public class WrongPasswordException extends Exception
{
   

    /**
     * 
     */
    private static final long serialVersionUID = -7345727606614543302L;

    // Parameterless Constructor
    public  WrongPasswordException()
    {
    }

    // Constructor that accepts a message
    public  WrongPasswordException(String message)
    {
        super(message);
    }
}

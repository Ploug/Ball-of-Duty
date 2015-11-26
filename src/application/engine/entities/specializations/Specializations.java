package application.engine.entities.specializations;

import java.util.HashMap;
import java.util.Map;

public enum Specializations
{
    ROLLER(1), BLASTER(2), HEAVY(3);
    private int value;
 
    private static Map<Integer, Specializations> values = new HashMap<>();
 
    static
    {
 
        values.put(1, ROLLER);
        values.put(2, BLASTER);
        values.put(3, HEAVY);
    }
 
    /**
     * For constructing a enum with specific value.
     *
     * @param value
     */
    private Specializations(int value)
    {
        this.value = value;
    }
 
    public static Specializations fromInteger(int x) // Better for performance
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
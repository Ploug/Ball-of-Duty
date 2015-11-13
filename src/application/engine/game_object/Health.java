package application.engine.game_object;

/**
 * Handles everything related to the characters health
 * 
 * @author Frederik
 *
 */
public class Health
{
    private int maxHealth;
    private int healthValue;
    private GameObject gameObject;

    /**
     * Creates health for a game object defining the max health.
     * 
     * @param maxHealth
     *            The max health of the game object.
     */
    public Health(int maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    public void setMax(int max)
    {
        maxHealth = max;
    }
    public int getMax()
    {
        return maxHealth;
    }
    public int getValue()
    {
        return healthValue;
    }
    public void setValue(int value)
    {
        healthValue = value;
    }

    @Override
    public String toString()
    {
        return String.format("Health [maxHealth=%s]", maxHealth);
    }

   

}

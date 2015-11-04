package application.engine.game_object;

/**
 * Handles everything related to the characters health
 * 
 * @author Frederik
 *
 */
public class Health
{
    public double maxHealth;
    public GameObject gameObject;

    /**
     * Creates health for a game object defining the max health.
     * 
     * @param maxHealth
     *            The max health of the game object.
     */
    public Health(double maxHealth)
    {
        this.maxHealth = maxHealth;
    }

    /**
     * Does damage to the health.
     * 
     * @param amount
     *            The amount of damage to take from the health.
     */
    public void takeDamage(double amount)
    {
        maxHealth -= amount;
        if (maxHealth < 1)
        {
            gameObject.destroy();
        }
    }

    @Override
    public String toString()
    {
        return String.format("Health [maxHealth=%s]", maxHealth);
    }

   

}

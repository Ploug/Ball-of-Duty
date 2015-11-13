package application.engine.game_object;

/**
 * Handles everything related to the characters health
 * 
 * @author Frederik
 *
 */
public class Health
{
    private int _maxHealth;
    private int _healthValue;
    private GameObject _gameObject;

    /**
     * Creates health for a game object defining the max health.
     * 
     * @param maxHealth
     *            The max health of the game object.
     */
    public Health(int maxHealth)
    {
        this._maxHealth = maxHealth;
    }

    public void setMax(int max)
    {
        _maxHealth = max;
    }
        
    public int getMax()
    {
        return _maxHealth;
    }
    
    public int getValue()
    {
        return _healthValue;
    }
    
    public void setValue(int value)
    {
        _healthValue = value;
    }

    @Override
    public String toString()
    {
        return String.format("Health [maxHealth=%s]", _maxHealth);
    }

   

}

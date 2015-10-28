package application;

public class Health
{

    public double maxHealth;
    public GameObject gameObject;
    public Health(double maxHealth)
    {
        this.maxHealth = maxHealth;
    }
    
    public void takeDamage(double amount)
    {
        maxHealth-=amount;
        if(maxHealth<1)
        {
            gameObject.destroy();
        }
    }
}

package application;

public class Body
{
    public GameObject gameObject;
    protected int x;
    protected int y;

    public Body(GameObject gO)
    {
        this.gameObject = gO;
    }
    
    public void collidesWith(GameObject gO)
    {
        
    }
}

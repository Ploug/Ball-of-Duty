package application;


public class Physics
{
    public GameObject gameObject;
    private double drag;
    private Vector2 velocity;
    private int maxVelocity = 100;

    public Physics(GameObject gO)
    {
        this.velocity = new Vector2(0, 0);
        this.gameObject = gO;
    }

    public void update()
    {
        gameObject.body.x += velocity.getX();
        gameObject.body.y += velocity.getY();
        
        
    }

    public void changeDirection(Vector2 direction) // http://paulbourke.net/geometry/reflected/
    {
        velocity = direction;
//        if(velocity.getMagnitude()==0)
//        {
//            velocity = Vector2.Normalize(direction).scalarMultiply(maxVelocity);
//        }
//        double scalarProduct = direction.scalarProduct(velocity);
//        direction.scalarMultiply(2);
//        direction.scalarMultiply(scalarProduct);
//        velocity = velocity.minusVector(direction);
//        
        
        System.out.println("Velocity vector: "+velocity);
    }
    
    public void addVelocity(Vector2 inputVelocity)
    {
            
        if (Vector2.getAddedVectors(velocity, inputVelocity).getMagnitude() < maxVelocity)
        {
            velocity.addVector(inputVelocity);
        }

    }
}

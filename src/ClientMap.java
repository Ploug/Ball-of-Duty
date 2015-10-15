import java.util.List;
import java.util.Observable;

public class ClientMap extends Observable
{
    public List<GameObject> characters;

    private Broker broker;
    
    public ClientMap(IMap serverMap, Broker broker)
    {
        this.broker = broker;
    }

    public void activate()
    {
        
    }

    public void deactivate()
    {
        
    }
}

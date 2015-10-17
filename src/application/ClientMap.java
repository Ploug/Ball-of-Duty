package application;

import java.awt.Graphics2D;
import java.util.List;
import java.util.Observable;

public class ClientMap extends Observable
{
    public List<GameObject> characters;

    private Broker broker;

    public ClientMap(IMap serverMap, Broker broker)
    {
        this.broker = broker;
        if(serverMap.getCharacters() != null)
        {
            this.characters = serverMap.getCharacters();
        }
        
    }

    public void activate()
    {
        while (true)
        {
            for (GameObject gameChar : characters)
            {
                gameChar.update();
            }
        }
    }

    public void deactivate()
    {

    }
}

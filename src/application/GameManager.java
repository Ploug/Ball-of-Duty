package application;

import java.awt.Graphics2D;
import java.util.List;

public class GameManager
{
    private static volatile GameManager instance;

    public Broker broker;
    public ClientMap cMap;
    public List<Player> enemyPlayers;
    public Player clientPlayer;

    private GameManager()
    {
        
    }

    public static GameManager getInstance()
    {
        if (instance == null)
        {
            synchronized (GameManager.class)
            {
                if (instance == null)
                {
                    instance = new GameManager();
                }
            }
        }

        return instance;
    }

    public void setClientPlayer(Player player)
    {
        clientPlayer = player;
    }

    public void setEnemyPlayers(List<Player> enemyPlayers)
    {
        this.enemyPlayers = enemyPlayers;
    }

    public void setClientMap(IMap serverMap)
    {
        this.cMap = new ClientMap(serverMap, new Broker("Server IP")); // Ved ikke hvordan GameManager skal have fat i IP endnu
    }

}
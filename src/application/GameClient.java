package application;

import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.tempuri.BoDServerLocator;
import org.tempuri.IBoDServer;

import javafx.scene.layout.BorderPane;

public class GameClient
{

    public ClientMap cMap;
    public List<Player> enemyPlayers;
    public Player clientPlayer;
    public CharacterController characterController;
    IBoDServer ibs;

    public GameClient()
    {
        
        
        BoDServerLocator server1 = new BoDServerLocator();

        try
        {
            ibs = server1.getBasicHttpBinding_IBoDServer();
            clientPlayer = new Player(ibs.newGuest());
           
        }
        catch (ServiceException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public ClientMap joinGame(BorderPane gameBox)
    {

        clientPlayer.createNewCharacter();

        try
        {
            this.cMap = new ClientMap(ibs.joinGame(clientPlayer), gameBox, clientPlayer.getCharacter());
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        characterController = new CharacterController(clientPlayer.getCharacter(), gameBox);

        cMap.activate();
        return cMap;
    }

    public void quitGame()
    {

    }

    public CharacterController getCharacterController()
    {
        return characterController;

    }

    public void setEnemyPlayers(List<Player> enemyPlayers)
    {
        this.enemyPlayers = enemyPlayers;
    }

}

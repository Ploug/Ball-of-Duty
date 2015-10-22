package application;

import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.tempuri.BoDServiceLocator;
import org.tempuri.IBoDService;

import javafx.scene.layout.BorderPane;

public class GameClient
{

    public ClientMap cMap;
    public List<Player> enemyPlayers;
    public Player clientPlayer;
    public CharacterController characterController;
    IBoDService ibs;

    public GameClient()
    {
        
        
        BoDServiceLocator server1 = new BoDServiceLocator();

        try
        {
            ibs = server1.getBasicHttpBinding_IBoDService();
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
            this.cMap = new ClientMap(ibs.joinGame(clientPlayer.getId()), gameBox, clientPlayer.getCharacter());
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
        try
        {
            ibs.quitGame(clientPlayer.getId());
        } catch (RemoteException e)
        {
            System.out.println("I was unable to quit the game " + e);
            e.printStackTrace();
        }
        cMap.deactivate();
        //cMap = null;
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

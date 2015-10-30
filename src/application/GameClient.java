package application;

import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.MapDTO;
import org.tempuri.BoDServiceLocator;
import org.tempuri.IBoDService;

import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;

public class GameClient
{

    public ClientMap cMap;
    public List<Player> enemyPlayers;
    public Player clientPlayer;
    public CharacterController characterController;
    public Point2D sceneAbsoluteLocation;
    IBoDService ibs;

    public GameClient(Point2D windowAbsoluteLocation)
    {

        this.sceneAbsoluteLocation = windowAbsoluteLocation;
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

    public void setSceneAbsoluteLocation(Point2D sceneAbsoluteLocation)
    {
        this.sceneAbsoluteLocation = sceneAbsoluteLocation;
        if (characterController != null)
        {
            characterController.setCanvasAbsoluteLocation(sceneAbsoluteLocation);
        }

    }

    public void joinGame(BorderPane gameBox)
    {
        System.out.println("trying to join game");
        clientPlayer.createNewCharacter();
        try
        {
            Broker broker = new Broker();
            MapDTO map = ibs.joinGame(clientPlayer.getId(), broker.getPort());
            cMap = new ClientMap(map, gameBox, broker, clientPlayer.getCharacter());

        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        characterController = new CharacterController(clientPlayer.getCharacter(), gameBox, sceneAbsoluteLocation);
        cMap.activate();

    }

    public void quitGame()
    {
        try
        {
            ibs.quitGame(clientPlayer.getId());
        }
        catch (RemoteException e)
        {
            System.out.println("I was unable to quit the game " + e);
            e.printStackTrace();
        }
        if (cMap != null)
        {
            cMap.deactivate();
        }

        // cMap = null;
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

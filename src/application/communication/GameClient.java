package application.communication;

import java.rmi.RemoteException;
import java.util.List;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameDTO;
import org.tempuri.BoDServiceLocator;
import org.tempuri.IBoDService;

import application.account.Player;
import application.engine.rendering.ClientMap;
import application.input.CharacterController;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;

/**
 * Communicates with the server via webservices and is the main facade for a game.
 * 
 * @author Gruppe6
 *
 */
public class GameClient
{

    public ClientMap cMap;
    public List<Player> enemyPlayers;
    public Player clientPlayer;
    public CharacterController characterController;
    public Point2D sceneRelativeLocation;
    IBoDService ibs;

    /**
     * Creates a game client with the current relative location of the window. The relative location is based on how the scene's is located
     * relative to the operating system.
     * 
     * @param windowRelativeLocation
     *            The current relative location of the window. The relative location is based on how the scene's is located relative to the
     *            operating system,
     */
    public GameClient(Point2D windowRelativeLocation)
    {

        this.sceneRelativeLocation = windowRelativeLocation;
        BoDServiceLocator server1 = new BoDServiceLocator();

        try
        {
            ibs = server1.getBasicHttpBinding_IBoDService();
            clientPlayer = new Player(ibs.newGuest("Guest"));// TODO Ask for nickname

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

    /**
     * Sets the scenes relative location. The relative location is based on how the scene's is located relative to the operating system.
     * 
     * @param sceneRelativeLocation
     *            The scenes relative location. he relative location is based on how the scene's is located relative to the operating
     *            system.
     */
    public void setSceneRelativeLocation(Point2D sceneRelativeLocation)
    {
        this.sceneRelativeLocation = sceneRelativeLocation;
        if (characterController != null)
        {
            characterController.setCanvasRelativeLocation(sceneRelativeLocation);
        }

    }

    /**
     * Tries to join a game. The game graphics and UI is handled in a BorderPane called game box.
     * 
     * @param gameBox
     *            The BorderPane where the game graphics and UI is handled.
     */
    public void joinGame(BorderPane gameBox)
    {
        System.out.println("trying to join game");
        try
        {
            Broker broker = new Broker(ibs);
            GameDTO map = ibs.joinGame(clientPlayer.getId(), broker.getPort());
            clientPlayer.createNewCharacter(map.getCharacterId());
            cMap = new ClientMap(map, gameBox, broker, clientPlayer.getCharacter());

        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        characterController = new CharacterController(clientPlayer.getCharacter(), gameBox, sceneRelativeLocation);
        cMap.activate();

    }

    /**
     * Tries to quit the current game.
     */
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

    /**
     * Gets the current CharacterController controlling the client character.
     * 
     * @return Returns the current CharacterController controlling the client character.
     */
    public CharacterController getCharacterController()
    {
        return characterController;

    }

    /**
     * Sets the enemy players in the game.
     * 
     * @param enemyPlayers
     *            The enemy players in the game.
     */
    public void setEnemyPlayers(List<Player> enemyPlayers)
    {
        this.enemyPlayers = enemyPlayers;
    }

}

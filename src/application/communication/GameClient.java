package application.communication;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.AccountDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameDTO;
import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.GameObjectDTO;
import org.tempuri.BoDServiceLocator;
import org.tempuri.IBoDService;

import application.account.Account;
import application.account.Player;
import application.engine.entities.specializations.Specializations;
import application.engine.rendering.ClientMap;
import application.input.CharacterController;
import javafx.geometry.Point2D;
import javafx.scene.layout.BorderPane;

/**
 * Communicates with the server via webservices and is the main facade for a
 * game.
 * 
 * @author Gruppe6
 *
 */
public class GameClient
{

    public ClientMap cMap;
    public Player clientPlayer;
    public Account account;
    public CharacterController characterController;
    public Point2D sceneRelativeLocation;
    IBoDService ibs;

    /**
     * Creates a game client with the current relative location of the window.
     * The relative location is based on how the scene's is located relative to
     * the operating system.
     * 
     * @param windowRelativeLocation
     *            The current relative location of the window. The relative
     *            location is based on how the scene's is located relative to
     *            the operating system,
     */
    public GameClient(Point2D windowRelativeLocation)
    {

        this.sceneRelativeLocation = windowRelativeLocation;
        BoDServiceLocator server1 = new BoDServiceLocator();

        try
        {
            ibs = server1.getBasicHttpBinding_IBoDService();

        }
        catch (ServiceException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void joinAsGuest(BorderPane gameBox, String nickname, Specializations spec)
    {
        try
        {
            clientPlayer = new Player(ibs.newGuest(nickname));
            joinGame(gameBox, spec);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void createAccount(String username, String nickname, char[] password, char[] passwordConfirmation)
    {
        for (int i = 0; i < password.length; i++)
        {
            if (password[i] != passwordConfirmation[i])
            {
                throw new IllegalArgumentException("Password not correct");
            }
        }

        account = new Account(username, password);
        int id = 0;

        if (clientPlayer != null)
        {
            id = clientPlayer.getId();
        }
        try
        {
            AccountDTO verifiedAccount = ibs.newAccount(username, nickname, id, account.getSalt(), account.getHash());
            if (verifiedAccount.getId() != 0)
            {
                System.out.println("Account created, with id: " + verifiedAccount.getId());
            }
            else
            {
                System.out.println("Error!");
            }
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Sets the scenes relative location. The relative location is based on how
     * the scene's is located relative to the operating system.
     * 
     * @param sceneRelativeLocation
     *            The scenes relative location. he relative location is based on
     *            how the scene's is located relative to the operating system.
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
     * Tries to join a game. The game graphics and UI is handled in a BorderPane
     * called game box.
     * 
     * @param gameBox
     *            The BorderPane where the game graphics and UI is handled.
     */
    private void joinGame(BorderPane gameBox, Specializations spec)
    {
        System.out.println("trying to join game");
        try
        {
            Broker broker = new Broker();
            GameDTO map = ibs.joinGame(clientPlayer.getId(), broker.getUdpPort(), broker.getTcpPort(), spec.getValue());
            clientPlayer.createNewCharacter(map.getCharacterId(), spec);
            cMap = new ClientMap(map, gameBox, broker, clientPlayer.getCharacter());

        }
        catch (RemoteException e)
        {
            e.printStackTrace();
        }

        characterController = new CharacterController(clientPlayer.getCharacter(), gameBox, sceneRelativeLocation);
        cMap.activate();

    }

    public void respawn(BorderPane gameBox, Specializations spec)
    {
        try
        {
            GameObjectDTO goDTO = ibs.respawn(clientPlayer.getId(), spec.getValue());
            clientPlayer.createNewCharacter(goDTO.getId(), spec);
            cMap.setCharacter(clientPlayer.getCharacter());

            characterController = new CharacterController(clientPlayer.getCharacter(), gameBox, sceneRelativeLocation);
            cMap.setChoosing(false);
        }
        catch (RemoteException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Tries to quit the current game.
     */
    public void quitGame()
    {
        try
        {
            if (ibs != null && clientPlayer != null)
            {
                ibs.quitGame(clientPlayer.getId());
            }

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
     * @return Returns the current CharacterController controlling the client
     *         character.
     */
    public CharacterController getCharacterController()
    {
        return characterController;

    }

}

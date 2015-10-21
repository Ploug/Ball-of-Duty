package application;

import java.util.List;

import org.tempuri.BoDServer;
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
        BoDServer server1 = new BoDServer();

        ibs = server1.getBasicHttpBindingIBoDServer();
        clientPlayer = new Player(ibs.newGuest());

    }

    public ClientMap joinGame(BorderPane gameBox)
    {

        clientPlayer.createNewCharacter();

        this.cMap = new ClientMap(ibs.joinGame(clientPlayer).getServerGameObject(), gameBox, clientPlayer.getCharacter());

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

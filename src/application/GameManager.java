package application;

import java.util.List;

import javafx.scene.layout.BorderPane;
import test.ServerMap;

public class GameManager
{

    public ClientMap cMap;
    public List<Player> enemyPlayers;
    public Player clientPlayer;
    public CharacterController characterController;
    public GameManager(Player clientPlayer)
    {
        this.clientPlayer = clientPlayer;
        
    }
    
    public ClientMap joinGame(BorderPane gameBox)
    {
        BoDCharacter character = new BoDCharacter();// Creation of character should be done serverside.
        clientPlayer.setCharacter(character);
        
       
        setClientMap(new ServerMap(character), gameBox);  // Gets from server normally
        characterController = new CharacterController(character,gameBox); 
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

    public void setClientMap(IMap serverMap, BorderPane gameBox)
    {
        this.cMap = new ClientMap(serverMap, new Broker("localhost"), gameBox); // Ved ikke hvordan GameManager skal have fat i IP endnu
    }

}
package application;

import java.util.List;

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
    
    public ClientMap joinGame()
    {
        BoDCharacter character = new BoDCharacter();// Creation of character should be done serverside.
        clientPlayer.setCharacter(character);
        
       
        setClientMap(new ServerMap(character));  // Gets from server normally
        characterController = new CharacterController(character); 
        
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

    public void setClientMap(IMap serverMap)
    {
        this.cMap = new ClientMap(serverMap, new Broker("localhost")); // Ved ikke hvordan GameManager skal have fat i IP endnu
    }

}
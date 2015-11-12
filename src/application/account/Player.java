package application.account;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO;

import application.engine.entities.BoDCharacter;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

/**
 * Keeps information about a player such as nickname, highscore, gold etc.
 * 
 * @author Gruppe6
 *
 */
public class Player
{

    public BoDCharacter character;
    private String nickname;
    private int id;

    /**
     * Creates a player based on a DTO from the serve (Players are always created server side)
     * @param pdto The DTO received from the server.
     */
    public Player(PlayerDTO pdto)
    {
        this.id = pdto.getId();
        this.nickname = pdto.getNickname();
    }

    /**
     * Gets the current character the player is controlling.
     * @return The current character the player is controlling.
     */
    public BoDCharacter getCharacter()
    {
        return character;
    }

    /**
     * Creates a new character for the player to control.
     */
    public void createNewCharacter(int id)
    {
        this.character = new BoDCharacter(id, new Point2D(100 + (int) Math.random() * 900, 100 + (int) Math.random() * 400), 50, 50,
                400, new Image("images/ball_blue.png")); // TODO image would be dynamic if player has different cosmetics
    }

    /**
     * Gets the identification for the player.
     * @return The identification for the player.
     */
    public int getId()
    {
        return id;
    }
    
    public String getNickname()
    {
        return nickname;
    }

    @Override
    public String toString()
    {
        return "Player [name=" + this.nickname + ", id=" + this.id + ", character=" + this.character + "]";
    }
}

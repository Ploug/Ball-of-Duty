package application;

import java.io.Serializable;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_DTO.PlayerDTO;

import javafx.geometry.Point2D;


public class Player implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 2413398156139415369L;

    public BoDCharacter character;
    private String IP;
    private String nickname;
    private int id;

    public Player(PlayerDTO pdto)
    {
        this.id = pdto.getId();
        this.nickname = pdto.getNickname();
    }

    public BoDCharacter getCharacter()
    {
        return character;
    }

    public void createNewCharacter()
    {
        this.character = new BoDCharacter(this.id, new Point2D(10,10),50,50,200);
    }

    public int getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "Player [name=" + this.nickname + ", id=" + this.id + ", character=" + this.character + ", IP=" + this.IP + "]";
    }
}

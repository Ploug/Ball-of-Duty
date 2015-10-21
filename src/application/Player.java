package application;

import java.io.Serializable;

import org.datacontract.schemas._2004._07.ball_of_duty_server.ServerPlayer;


public class Player extends ServerPlayer implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 2413398156139415369L;
    
    public BoDCharacter character;
    private ServerPlayer serverPlayer;
    private String IP;

    public Player(ServerPlayer sp)
    {
        serverPlayer = sp;
        this.setX003CIdX003EKBackingField(sp.getX003CIdX003EKBackingField()); // I dont even know what to say... Anyways properties not allowed on serialized C# classes.
        this.setX003CNicknameX003EKBackingField(sp.getX003CNicknameX003EKBackingField());
    }

    public BoDCharacter getCharacter()
    {
        return character;
    }

    public void createNewCharacter()
    {
        this.character = new BoDCharacter(this.getID());
    }

    public int getID()
    {
        return this.getX003CIdX003EKBackingField();
    }

    @Override
    public String toString()
    {
        return "Player [name=" + this.getX003CNicknameX003EKBackingField() + ", id=" + getID() + ", character=" + character + ", IP=" + IP + "]";
    }
}

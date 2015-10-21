package application;

import java.io.Serializable;

import org.datacontract.schemas._2004._07.Ball_of_Duty_Server_Persistence.ServerPlayer;



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
        this.set_x003C_Id_x003E_k__BackingField(sp.get_x003C_Id_x003E_k__BackingField()); // I dont even know what to say... Anyways properties not allowed on serialized C# classes.
        
        this.set_x003C_Nickname_x003E_k__BackingField(sp.get_x003C_Nickname_x003E_k__BackingField());
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
        return this.get_x003C_Id_x003E_k__BackingField();
    }

    @Override
    public String toString()
    {
        return "Player [name=" + this.get_x003C_Nickname_x003E_k__BackingField() + ", id=" + getID() + ", character=" + character + ", IP=" + IP + "]";
    }
}

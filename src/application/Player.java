package application;

public class Player
{
    public BoDCharacter character;
    private int id;
    private String IP;

    public Player(int id)
    {
        this.id = id;

    }

    public BoDCharacter getCharacter()
    {
        return character;
    }

    public void setCharacter(BoDCharacter character)
    {
        this.character = character;
    }
}

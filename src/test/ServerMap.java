package test;

import java.util.ArrayList;
import java.util.List;

import application.BoDCharacter;
import application.GameObject;
import application.IMap;

public class ServerMap implements IMap
{

    ArrayList<GameObject> characters;

    @Override
    public List<GameObject> getCharacters()
    {
        return characters;
    }

    public ServerMap(BoDCharacter clientCharacter) // remember.. TEST CONSTRUCTOR!
    {
        characters = new ArrayList<>();
        characters.add(clientCharacter);
    }

}

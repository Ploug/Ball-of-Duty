package application.gui;

import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.engine.entities.BoDCharacter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Leaderboard extends ListView<BoDCharacter>
{
    ObservableList<BoDCharacter> characters;
    Map<Integer, BoDCharacter> addedCharacters;


    public Leaderboard()
    {
        characters = FXCollections.observableArrayList();
        addedCharacters = new ConcurrentHashMap<>();
        setItems(characters);
    }

    @Override
    public void refresh()
    {
        characters.clear();
        for(BoDCharacter c : addedCharacters.values())
        {
            characters.add(c);
        }
        characters.sort((c1, c2) -> Double.compare(c2.getScore(), c1.getScore()));

        super.refresh();
    }

    public void addCharacter(BoDCharacter character)
    {
        addedCharacters.put(character.getId(), character);
    }

    public void remove(BoDCharacter character)
    {
        addedCharacters.remove(character.getId());
    }
}

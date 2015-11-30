package application.gui;

import java.util.HashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.engine.entities.BoDCharacter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class Leaderboard extends ListView<BoDCharacter>
{
    ObservableList<BoDCharacter> characters;
    Set<BoDCharacter> addedCharacters;

    Queue<BoDCharacter> removeQueue;
    Queue<BoDCharacter> addQueue;

    public Leaderboard()
    {
        removeQueue = new ConcurrentLinkedQueue<>();
        addQueue = new ConcurrentLinkedQueue<>();
        characters = FXCollections.observableArrayList();
        addedCharacters = new HashSet<>();
        setItems(characters);
    }

    public void refresh()
    {

        while (addQueue.peek() != null)
        {
            characters.add(addQueue.poll());

        }
        while (removeQueue.peek() != null)
        {
            characters.remove(removeQueue.poll());

        }
        characters.sort((c1, c2) -> Double.compare(c2.getScore(), c1.getScore()));
    }

    public void addCharacter(BoDCharacter character)
    {
        if (!addedCharacters.contains(character))
        {
            addQueue.add(character);
            addedCharacters.add(character);
        }

    }

    public void remove(BoDCharacter character)
    {
        removeQueue.add(character);
        addedCharacters.remove(character);
    }
}

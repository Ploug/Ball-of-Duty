package application.gui;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Queue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import application.gui.HighscoreLeaderboard.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class HighscoreLeaderboard extends ListView<HighscoreLeaderboard.Entry>
{
    private ObservableList<Entry> entries;
    private SortedSet<Entry> sortedEntries;

    public static final int SCORE_LIMIT = 100;

    public class Entry
    {
        public String nickName;
        public double score;
        public int id;
        public int position;

        public Entry(String name, int id, double score)
        {
            this.nickName = name;
            this.id = id;
            this.score = score;
        }

        @Override
        public int hashCode()
        {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + id;
            result = prime * result + ((nickName == null) ? 0 : nickName.hashCode());
            long temp;
            temp = Double.doubleToLongBits(score);
            result = prime * result + (int)(temp ^ (temp >>> 32));
            return result;
        }

        public String toString()
        {
            String startText = String.format("#%s: %s [%s]", position, nickName, id);
            return String.format("%s     | Score: %s", startText, score);
        }

        private HighscoreLeaderboard getOuterType()
        {
            return HighscoreLeaderboard.this;
        }
    }

    public HighscoreLeaderboard()
    {
        entries = FXCollections.observableArrayList();
        sortedEntries = Collections.synchronizedSortedSet(new TreeSet<Entry>((c1, c2) -> Double.compare(c2.score, c1.score)));
        setItems(entries);
        setStyle("-fx-font: 20 arial; ");
    }

    public void refresh()
    {
        entries.clear();
        int i = 0;
        for(Entry e : sortedEntries)
        {
            i++;
            entries.add(e);
            e.position = i;
        }
    }

    public void addEntry(String nickname, int id, double score)
    {

        sortedEntries.add(new Entry(nickname, id, score));

    }

    public void remove(int id)
    {
       sortedEntries.removeIf((e)-> e.id == id);
    }
}

package application;
public interface IGame
{
 

    public IMap getMap();

    void addPlayer(Player player);
    void quitGame(Player player);
}

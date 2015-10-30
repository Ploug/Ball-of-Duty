package application;

public interface Observable
{
    void notifyObservers(Object arg);
    void notifyObservers();
    void registerObserver(Observer obs);
    void unregisterObserver(Observer obs);
}

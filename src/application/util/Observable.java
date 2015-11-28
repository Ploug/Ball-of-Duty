package application.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;

public class Observable
{
    private static final Observation[] _observations = Observation.values();

    private final ConcurrentHashMap<Observation, ConcurrentHashMap<Object, BiConsumer<Observable, Object>>> _observers;

    public Observable()
    {
        _observers = new ConcurrentHashMap<Observation, ConcurrentHashMap<Object, BiConsumer<Observable, Object>>>();
        for (Observation o : _observations)
        {
            _observers.put(o, new ConcurrentHashMap<Object, BiConsumer<Observable, Object>>());
        }

    }

    public void register(Observation observation, Object observer, BiConsumer<Observable, Object> action)
    {
        _observers.get(observation).put(observer, action);
    }

    public void unregister(Observation observation, Object observer)
    {
        _observers.get(observation).remove(observer);
    }

    public void unregisterAll(Object observer)
    {
        for (ConcurrentHashMap<Object, BiConsumer<Observable, Object>> pair : _observers.values())
        {
            pair.remove(observer);
        }
    }

    public void notifyObservers(Observation observation, Object data)
    {
        for (BiConsumer<Observable, Object> action : _observers.get(observation).values())
        {
            action.accept(this, data);
        }
    }

    public void notifyObservers(Observation observation)
    {
        notifyObservers(observation, null);
    }
}

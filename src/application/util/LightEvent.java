package application.util;

public class LightEvent
{
    private final long _interval;
    private long _currentTime;
    private Event _timedEvent;

    public LightEvent(long interval, Event timedEvent)
    {
        _interval = interval;
        _timedEvent = timedEvent;
    }

    public void update(long deltaTime)
    {
        _currentTime += deltaTime;
        if (_currentTime >= _interval)
        {
            _timedEvent.execute();
            _currentTime = 0;
        }
    }

    public void reset()
    {
        _currentTime = 0; // TODO consider locking here.
    }

    public interface Event
    {
        void execute();
    }
}

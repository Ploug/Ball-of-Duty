package application.util;

/**
 * The timer handles timekeeping. 
 * @author Frederik
 *
 */
public class Timer
{
    private double lastTime = 0;
    private double timeSincePause = 0;

    private boolean paused = false;
    private boolean activated = false;

    /**
     * Starts the timer.
     */
    public void start()
    {
        if (!paused && !activated)
        {
            activated = true;
            lastTime = (System.nanoTime() / 10000);
        }
        else if (paused && activated)
        {
            paused = false;
            lastTime += ((System.nanoTime() / 10000) - timeSincePause);
        }
    }

   
    private long lastCalled = 0;
    
    /**
     * Sleeps as long as inputted minus the time since this method was last called.
     * 
     * @param ms The minimum time that needs to have passed since this method was last called.
     */
    public void catchupSleep(long ms)
    {
        long sleepDuration = 0;
        if (lastCalled == 0)
        {
            try
            {
                Thread.sleep(ms);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            sleepDuration = (int) (ms - (System.currentTimeMillis() - lastCalled));
            if (sleepDuration < 1)
            {
                return;
            }
            try
            {
                Thread.sleep(sleepDuration);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }

        lastCalled = System.currentTimeMillis();

    }

    /**
     * Stops the timer.
     */
    public void stop()
    {
        activated = false;
    }

    /**
     * Pauses the timer.
     */
    public void pause()
    {
        if (!paused)
        {
            paused = true;
            timeSincePause = (System.nanoTime() / 10000);
        }
    }

    /**
     * Resets the timer.
     */
    public void reset()
    {
        lastTime = (System.nanoTime() / 10000);
    }

    /**
     * Gets the duration in milliseconds since the timer was started or resetted. Calculated for any pauses there might have been.
     * @return The duration in milliseconds since the timer was started or resetted. Calculated for any pauses there might have been.
     */
    public double getDuration()
    {
        if (!activated)
        {
            return 0;
        }
        else
        {
            double dfs = (((System.nanoTime() / 10000) - lastTime)); // duration from start
            if (paused)
            {
                return (timeSincePause - lastTime) / 100;
            }
            else
            {
                return dfs / 100;
            }

        }
    }
}
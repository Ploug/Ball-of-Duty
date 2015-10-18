package application;


public class Timer
{
    private double lastTime = 0;
    private double timeSincePause = 0;

    private boolean paused = false;
    private boolean activated = false;

    void start()
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

    /**
     * Sleep as long as inputted minus the time since this method was last called..
     * 
     * @param ms
     */
    private long lastCalled = 0;

    void catchupSleep(long ms)
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
            sleepDuration = (int) (ms-(System.currentTimeMillis()-lastCalled));
            if(sleepDuration<1)
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

    void stop()
    {
        activated = false;
    }

    void pause()
    {
        if (!paused)
        {
            paused = true;
            timeSincePause = (System.nanoTime() / 10000);
        }
    }

    void reset()
    {
        lastTime = (System.nanoTime() / 10000);
    }

    double getDuration()
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
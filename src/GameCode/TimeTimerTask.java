/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.util.TimerTask;


/**
 *
 * @author Lior
 */
public class TimeTimerTask extends TimerTask
{
    private int TimeCounter = 0;
    private boolean isRunning;
    
   /* public TimeTimerTask()
    {
    }*/
    public void StartMe()
    {
       isRunning = true; 
    }
    public void StopMe()
    {
        isRunning = false;
        this.cancel();
    }
    public void PauseMe()
    {
        isRunning = false;
    }
    public void run()
    {
        if(isRunning)
        {
            TimeCounter++;
        }
    }

    /**
     * @return the TimeCounter
     */
    public int getTimeCounter()
    {
        return TimeCounter;
    }

    /**
     * @param TimeCounter the TimeCounter to set
     */
    public void setTimeCounter(int TimeCounter)
    {
        this.TimeCounter = TimeCounter;
    }
        
}

    

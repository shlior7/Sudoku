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
public class FlickerTimerTask extends TimerTask
{
    private FlickerElement PAK;
    private int counter = 0;
    private boolean isRunning;
    private boolean Endless;
    
    public FlickerTimerTask(FlickerElement PAK,boolean Endless)
    {
        this.PAK = PAK;
        this.Endless = Endless;
    }
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
            if(counter%2 == 0)
            {
                PAK.setVisible(false);
            }
            else
            {
                PAK.setVisible(true);
            }
            counter++;
            if(Endless == false)
            {
                if(counter == 40)
                {
                    counter = 0;
                    PAK.setVisible(false);
                    PauseMe();
                }
            }
        }
    }

    /**
     * @param counter the counter to set
     */
    public void setCounter(int counter)
    {
        this.counter = counter;
    }
        
}

    

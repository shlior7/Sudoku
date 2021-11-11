/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.util.TimerTask;
import javax.microedition.lcdui.game.Sprite;


/**
 *
 * @author Lior
 */
public class WinTimerTask extends TimerTask
{
    private Sprite Win;
    private boolean isRunning;
    
    public WinTimerTask(Sprite Win)
    {
        this.Win = Win;
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
            Win.nextFrame();
        }
    }
        
}

    

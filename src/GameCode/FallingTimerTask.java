/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.util.Timer;
import java.util.TimerTask;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author class3
 */
public class FallingTimerTask extends TimerTask// מבצע את אפקט הנפילה כל עוד אין מתחתיו רצפה
{

    private MySprite sprite;
    private TiledLayer TLayer;
    private TiledLayer Cloud;
    private Timer myTimer = new Timer();
    private int SpeedFall = 2;
    private boolean isRunning;

    public FallingTimerTask(MySprite sprite, TiledLayer TLayer)
    {
        this.sprite = sprite;
        this.TLayer = TLayer;
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
        if (isRunning)
        {
            sprite.move(0, SpeedFall);//עושה את התזוזה
            sprite.setIsFalling(true);//מגדיר זאת כדי שהמשתמש לא יוכל לקפוץ בזמן שהוא נופל
            if (sprite.collidesWith(TLayer, true))
            {
                sprite.move(0, -SpeedFall * 2);//זז חזרה למעלה אם מתנגש 
                sprite.setIsFalling(false);
                PauseMe();
            }
        }

    }

/**
 * @return the SpeedFall
 */
public int getSpeedFall()
    {
        return SpeedFall;
    }

    /**
     * @param SpeedFall the SpeedFall to set
     */
    public void setSpeedFall(int SpeedFall)
    {
        this.SpeedFall = SpeedFall;
    }

    
    
}

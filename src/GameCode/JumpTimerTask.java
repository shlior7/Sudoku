/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.util.TimerTask;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author Lior
 */
public class JumpTimerTask extends TimerTask
{

    private MySprite sprite;
    private TiledLayer TLayer;
    private int counter = 1;
    private boolean isRunning;

    public JumpTimerTask(MySprite sprite, TiledLayer TLayer)
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
            if (counter > 21)
            {
                sprite.move(0, 2);
                sprite.defineCollisionRectangle(5, 15, 12, 5);
                if (sprite.collidesWith(TLayer, false))// אם מתנגש עם הרגליים לפני ההגעה לגובה המקורי
                {
                    sprite.move(0, -2);
                    counter = 42;
                }
                if (counter == 42)
                {
                    sprite.setIsJumping(false);
                    counter = 1;
                    PauseMe();
                }
            }
            else
            {
                sprite.move(0, -2);
                sprite.defineCollisionRectangle(5, 0, 12, 5);
                if (sprite.collidesWith(TLayer, false))//אם נתקע עם הראש בזמן הקפיצה עצמה מסיים את הקפיצה ומתחיל את השלב השני שהוא הירידה
                {
                    counter = 21;
                    sprite.move(0, 2);
                    sprite.defineCollisionRectangle(5, 0, 12, 20);
                }
                
            }
            counter++;
        }
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.io.IOException;
import java.util.Timer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author Lior
 */
public class MySprite extends MySuperSprite
{

    private int[] seqUp =
    {
        9, 10, 11
    }, seqRight =
    {
        6, 7, 8
    }, seqDown =
    {
        0, 1, 2
    }, seqLeft =
    {
        3, 4, 5
    };
    private boolean isFalling = false;
    private boolean isJumping = false;
    private JumpTimerTask myTask;
    private FallingTimerTask myFallTask;
    private Timer myTimer;
    private Timer NewTimer;

    public MySprite(String ImgPath, int FrameWidth, int FrameHeight) throws IOException
    {
        super(ImgPath, FrameWidth, FrameHeight, 5);
        this.setFrameSequence(seqUp);
        myTimer = new Timer();
        this.defineCollisionRectangle(5, 0, 12, 20);
    }

    public void reactToDir(int keyStates, int ScreenWidth, int ScreenHeight, TiledLayer Tlayer)
    {
        /*if ((keyStates & GameCanvas.DOWN_PRESSED) != 0)
         {
         goDown(Tlayer);
         this.nextFrame();
         }
         if ((keyStates & GameCanvas.UP_PRESSED) != 0)
         {
         goUp(Tlayer);  
         this.nextFrame();
         }*/
        if ((keyStates & GameCanvas.LEFT_PRESSED) != 0)
        {
            goLeft(Tlayer);
            this.nextFrame();
        }
        if ((keyStates & GameCanvas.RIGHT_PRESSED) != 0)
        {
            goRight(Tlayer);
            this.nextFrame();
        }
        if ((keyStates & GameCanvas.UP_PRESSED) != 0 && isFalling == false)
        {
            isJumping = true;
            jump(Tlayer);
        }

    }

    private void goDown(TiledLayer TLayer)
    {
        if (prevDir != seqDown[0])
        {
            this.setFrameSequence(seqDown);
        }
        prevDir = seqDown[0];
        if (this.collidesWith(TLayer, true) == false)
        {
            this.move(0, step);
        }
    }

    private void goUp(TiledLayer TLayer)
    {
        if (prevDir != seqUp[0])
        {
            this.setFrameSequence(seqUp);
        }
        prevDir = seqUp[0];
        if (this.collidesWith(TLayer, false) == false && this.getY() > 0)
        {
            this.move(0, -step);
        }

    }

    private void goLeft(TiledLayer TLayer)
    {
        if (prevDir != GameCanvas.LEFT_PRESSED)
        {
            this.setFrameSequence(seqLeft);
        }
        prevDir = GameCanvas.LEFT_PRESSED;
        this.move(-1, 0);
        if (this.collidesWith(TLayer, false) || this.getX() < 0)
        {
            this.move(1, 0);
        }
        else
        {
            this.move(1, 0);
            this.move(-step, 0);
        }

    }

    private void goRight(TiledLayer TLayer)
    {
        if (prevDir != GameCanvas.RIGHT_PRESSED)
        {
            this.setFrameSequence(seqRight);
        }
        prevDir = GameCanvas.RIGHT_PRESSED;
        this.move(1, 0);
        if (this.collidesWith(TLayer, false) || this.getX() + this.getWidth() >=TLayer.getWidth())
        {
            this.move(-1, 0);
        }
        else
        {
            this.move(-1, 0);
            this.move(step, 0);
        }

    }

    public void jump(TiledLayer TLayer)
    {
        StartMyTask(myTimer, TLayer);
    }

    /**
     * @return the CurrentFrame
     */
    public void StartMyTask(Timer timer, TiledLayer TLayer)//תחילת הקפיצה
    {
        if (myTask == null)
        {
            this.myTimer = timer;
            myTask = new JumpTimerTask(this, TLayer);
            myTask.StartMe();
            myTimer.schedule(myTask, 0, 10);
        }
        else
        {
            myTask.StartMe();
        }
    }

    public void StartMyFallingTask(Timer timer, TiledLayer TLayer)//תחילת הנפילה
    {
        if (myFallTask == null)
        {
            this.NewTimer = timer;
            myFallTask = new FallingTimerTask(this, TLayer);
            myFallTask.StartMe();
            NewTimer.schedule(myFallTask, 0, 10);
        }
        else
        {
            myFallTask.StartMe();
        }
    }
    /**
     * @param isJumping the isJumping to set
     */
    public void setIsFalling(boolean isFalling)
    {
        this.isFalling = isFalling;
    }

    public boolean IsFalling()
    {
        return isFalling;
    }

    /**
     * @return the isJumping
     */
    public boolean IsJumping()
    {
        return isJumping;
    }

    /**
     * @param isJumping the isJumping to set
     */
    public void setIsJumping(boolean isJumping)
    {
        this.isJumping = isJumping;
    }
}

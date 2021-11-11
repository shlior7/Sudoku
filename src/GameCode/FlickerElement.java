/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.util.Timer;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author Lior
 */
public class FlickerElement extends GameItem
{

    private int Hint;
    private Timer myTimer;
    private FlickerTimerTask myTask;
    private boolean Visible = true;

    public FlickerElement(int x, int y, int width, int height, int Hint)
    {
        super(x, y, width, height);
        this.Hint = Hint;
    }

    public void drawMe(Graphics g)
    {
        if (Visible)
        {
            if (Hint == -1)
            {
                g.drawString("Press Any Key To Continue", x, y, Graphics.TOP | Graphics.LEFT);
            }
            else
            {
                g.setColor(255,255,0);
                g.fillRect(x, y, width, height);
                g.setColor(0,0,0);
            }
        }
    }

    public void StartMyTask(Timer timer)
    {

        if (myTask == null)
        {
            this.myTimer = timer;
            myTask = new FlickerTimerTask(this,Hint == -1);
            myTask.StartMe();
            myTimer.schedule(myTask, 0, 600);
        }
        else
        {
            myTask.setCounter(0);
            myTask.StartMe();
        }
    }

    public void Stop()
    {
        myTask.StopMe();
    }

    /**
     * @return the Visible
     */
    public boolean isVisible()
    {
        return Visible;
    }

    /**
     * @param Visible the Visible to set
     */
    public void setVisible(boolean Visible)
    {
        this.Visible = Visible;
    }

    /**
     * @return the Hint
     */
    public int getHint()
    {
        return Hint;
    }

    /**
     * @param Hint the Hint to set
     */
    public void setHint(int Hint)
    {
        this.Hint = Hint;
    }

    /**
     * @return the myTask
     */
    public FlickerTimerTask getMyTask()
    {
        return myTask;
    }
}

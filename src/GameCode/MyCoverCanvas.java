/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import hello.Midlet;
import java.io.IOException;
import java.util.Timer;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Lior
 */
public class MyCoverCanvas extends Canvas implements Runnable
{

    private int width;
    private int height;
    private Image cover;
    private Timer myTimer;
    private FlickerElement PAK;
    private Midlet midlet;
    private Thread myThread;
    private boolean isRunning;

    public MyCoverCanvas() throws IOException
    {
        width = this.getWidth();
        height = this.getHeight();
        init();
        PAK.StartMyTask(myTimer);
        StartThread();
    }

    public void setMidlet(Midlet midlet)
    {
        this.midlet = midlet;
    }

    protected void paint(Graphics g)
    {
        g.setColor(0, 0, 0);
        g.fillRect(0, 0, width, height);
        g.drawImage(cover, 0, 0, Graphics.TOP | Graphics.LEFT);
        PAK.drawMe(g);
    }

    public void init() throws IOException
    {
        myTimer = new Timer();
        cover = Image.createImage("images/Cover.png");
        PAK = new FlickerElement(width / 2 - 70, height - 15,0,0,-1);

    }

    protected void keyPressed(int keyCode)
    {
        int gameCode = this.getGameAction(keyCode);
        switch (gameCode)
        {
            case GameCanvas.DOWN:
            case GameCanvas.UP:
            case GameCanvas.LEFT:
            case GameCanvas.RIGHT: 
            case GameCanvas.FIRE:
                PAK.Stop();
                midlet.EpusCoverCanvas();
                midlet.switchDisplayable(null, midlet.getListCanvas());
                break;
        }


    }
    public void StartThread()
    {
        if (myThread == null)
        {
            myThread = new Thread(this);
            isRunning = true;
            myThread.start();
        }
    }

    public void StopThread()
    {
        isRunning = false;
        myThread = null;
    }

    public void run()
    {
        while(isRunning)
        {
            repaint();
            try
            {
                Thread.sleep(100);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}

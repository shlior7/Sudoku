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
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.TiledLayer;

/**
 *
 * @author Lior
 */
public class BoredCanvas extends GameCanvas implements Runnable
{

    private int width, height, x, y, gap;
    private Graphics g;
    private Thread myThread;
    private boolean isRunning;
    private MySprite Player;
    private MySprite Princes;
    private MySprite BadGuy;
    private Timer myTimer;
    private TiledLayer Earth;
    private Image Backround;
    private GameDesign gameDesign;
    private LayerManager LayerMng;
    private boolean temp = false;
    private BoubleText Saying;

    public BoredCanvas() throws IOException
    {
        super(true);
        width = this.getWidth();
        height = this.getHeight();
        x = 0;
        y = 0;
        gap = 100;
        g = getGraphics();
        init();
        StartThread();
    }

    public void init() throws IOException
    {
        myTimer = new Timer();
        LayerMng = new LayerManager();
        gameDesign = new GameDesign();
        gameDesign.updateLayerManagerForLevel1(LayerMng);
        Earth = gameDesign.getEarth();
        Player = new MySprite("images/Guy.png", 22, 23);
        Backround = Image.createImage("images/StartBackRound.png");
        Player.setPosition(0, 259);
        Saying = new BoubleText(300, 245, 140, 2, "Hello Player I Will" + "\n" + "Revenge My Parents Death^if You Want To See Your Girlfriend\nYou Will Need To Pass Al My Tests!!");
        LayerMng.insert(Player,0);
    }

    public void DrawAll()
    {
        g.setColor(0x00ff00);
        LayerMng.paint(g, 0, 0);
        Saying.drawMe(g);
        flushGraphics();
    }

    public void adjustViewScreen()
    {
        int dy = 0, dx = 0;
        if (Player.getPrevDir() == GameCanvas.RIGHT_PRESSED) // the lion goes right
        {
            if (Player.getX() + Player.getWidth() + Player.getStep() < x + width - gap)
            {
                return;
            }
            if (Player.getX() + Player.getWidth() + Player.getStep() + gap >= Earth.getWidth()-50)
            {
                return;
            }
            dx = Player.getStep();
        }

        if (Player.getPrevDir() == GameCanvas.LEFT_PRESSED) // the lion goes left
        {
            if (Player.getX() > x + gap)
            {
                return;
            }
            if (Player.getX() < 0 || x <= 0)
            {
                return;
            }
            dx = -Player.getStep();
        }

        x += dx;
        y += dy;
        LayerMng.setViewWindow(x, y, width, height);
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
        while (isRunning)
        {
            DrawAll();
            int keyStates = getKeyStates();
            Player.reactToDir(keyStates, width, height, Earth);
            adjustViewScreen();
            Saying.Fire(keyStates);
            if (Player.IsJumping() == false && temp == false)
            {
                Player.StartMyFallingTask(myTimer, Earth);
            }
            try
            {
                Thread.sleep(50);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
}
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

/**
 *
 * @author Lior
 */
public class ListCanvas extends Canvas
{

    private int width;
    private int height;
    private Image Backround1;
    private Image Backround2;
    private Image Backround3;
    private Image Backround4;
    private Image Backround5;
    private int IndexChoose = 1;
    private Midlet midlet;

    public ListCanvas() throws IOException
    {
        width = this.getWidth();
        height = this.getHeight();
        init();
        
    }

    public void setMidlet(Midlet midlet)
    {
        this.midlet = midlet;
    }

    protected void paint(Graphics g)
    {

        g.setColor(0, 0, 0);
        g.fillRect(0, 0, width, height);
        switch (IndexChoose)
        {
            case 1:
                g.drawImage(Backround1, 0, 0, Graphics.TOP | Graphics.LEFT);
                break;
            case 2:
                g.drawImage(Backround2, 0, 0, Graphics.TOP | Graphics.LEFT);
                break;
            case 3:
                g.drawImage(Backround3, 0, 0, Graphics.TOP | Graphics.LEFT);
                break;
            case 4:
                g.drawImage(Backround4, 0, 0, Graphics.TOP | Graphics.LEFT);
                break;
            case 5:
                g.drawImage(Backround5, 0, 0, Graphics.TOP | Graphics.LEFT);
                break;
        }
    }

    protected void keyPressed(int keyCode)
    {
        int gameCode = this.getGameAction(keyCode);
        switch (gameCode)
        {
            case Canvas.DOWN:
                if (IndexChoose < 5)
                {
                    IndexChoose++;
                }
                break;
            case Canvas.UP:
                if (IndexChoose > 1)
                {
                    IndexChoose--;
                }
                break;
            case Canvas.FIRE:
                ListChange();
        }
        repaint();
    }
    public void ListChange()
    {
        switch (IndexChoose)
        {
            case 1:
                midlet.switchDisplayable(null, midlet.getPlayCanvas());
                break;
            case 2:
                midlet.switchDisplayable(null, midlet.getInputNPlayCanvas());
                break;
            case 4:
                midlet.switchDisplayable(null, midlet.getLevel());
                break;
            case 5:
                midlet.switchDisplayable(null, midlet.getBoredCanvas());
                break;
        }



    }

    public void init() throws IOException
    {
        Backround1 = Image.createImage("images/Backround1.png");
        Backround2 = Image.createImage("images/Backround2.png");
        Backround3 = Image.createImage("images/Backround3.png");
        Backround4 = Image.createImage("images/Backround4.png");
        Backround5 = Image.createImage("images/Backround5.png");
    }

    /**
     * @return the IndexChoose
     */
    public int getIndexChoose()
    {
        return IndexChoose;
    }

    /**
     * @param IndexChoose the IndexChoose to set
     */
    public void setIndexChoose(int IndexChoose)
    {
        this.IndexChoose = IndexChoose;
    }

}

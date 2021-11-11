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
public class TutrialCanvas extends Canvas
{

    private int width;
    private int height;
    Image Tutrial1;
    Image Tutrial2;
    Image Tutrial3;
    Image Tutrial4;
    Image Tutrial5;

    public TutrialCanvas() throws IOException
    {
        width = this.getWidth();
        height = this.getHeight();
        init();
    }

   

    protected void paint(Graphics g)
    {

        g.setColor(0, 0, 0);
        g.fillRect(0, 0, width, height);
        
    }

    protected void keyPressed(int keyCode)
    {
        int gameCode = this.getGameAction(keyCode);
        
        repaint();
    }
    

    public void init() throws IOException
    {
        Tutrial1 = Image.createImage("images/tutrial1.png");
        Tutrial2 = Image.createImage("images/Backround2.png");
        Tutrial3 = Image.createImage("images/Backround3.png");
        Tutrial4 = Image.createImage("images/Backround4.png");
        Tutrial5 = Image.createImage("images/Backround5.png");
    }

    

}

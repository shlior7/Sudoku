/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.io.IOException;
import java.util.Calendar;
import java.util.Timer;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

public class Selector extends GameItem
{

    private int prevDir;
    
    public Selector(int x, int y, int width, int height)
    {
        super(x, y, width, height);
    }

    public void drawMe(Graphics g,boolean Quit)
    {
        if (y < 255 && Quit == false)
        {
            g.setColor(0, 255, 0);
            g.fillRect(x + 1, y + 1, width - 1, height - 1);
        }
        g.setColor(255, 0, 0);
        g.drawRect(x, y, width, height);
    }

    public void MoveMe(int dir, int ScreenWidth, int ScreenHeight, boolean Quit)
    {
        int dx = 0;
        int dy = 0;
        switch (dir)
        {
            case Canvas.LEFT:
                if (y < 255)
                {
                    if (x - width >= 0)
                    {
                        dx = -width;
                    }
                    if (Quit)
                    {
                        if (prevDir == Canvas.RIGHT)
                        {
                            dx = -70;
                            prevDir = Canvas.LEFT;
                        }
                        else
                        {
                            dx = 0;
                        }
                    }
                }
                break;

            case Canvas.RIGHT:
                if (x + width * 2 <= ScreenWidth)
                {
                    dx = width;
                }
                if (Quit)
                {
                    if (prevDir == Canvas.LEFT)
                    {
                        dx = 70;
                        prevDir = Canvas.RIGHT;
                    }
                    else
                    {
                        dx = 0;
                    }
                }
                break;

            case Canvas.UP:
                if (y - height >= 0 && Quit == false)
                {
                    dy = -height;
                }
                if (y > 255 && Quit == false)
                {
                    width = 26;
                    height = 28;
                    x = 185;
                    y = 249;
                }
                if(y == 3 && Quit == false)
                {
                    width = 100;
                    height = 22;
                    x = ScreenWidth / 2;
                    y = 295;
                }
                break;

            case Canvas.DOWN:
                if (y + height * 2 <= 255 && Quit == false)
                {
                    dy = height;
                }
                if(width == 100)
                {
                    width = 26;
                    height = 28;
                    x = 185;
                    y = 3;
                }
                if (y + height * 2 > 255 && Quit == false)
                {
                    width = 100;
                    height = 22;
                    x = ScreenWidth / 2;
                    y = 295;
                }
                
                break;
        }
        x += dx;
        y += dy;
    }

    /**
     * @param prevDir the prevDir to set
     */
    public void setPrevDir(int prevDir)
    {
        this.prevDir = prevDir;
    }
}

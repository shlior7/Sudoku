/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.io.IOException;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Lior
 */
public abstract class MySuperSprite extends Sprite
{
    protected int prevDir = 0;
    protected int step;
    protected Image img;
    
    public MySuperSprite(String ImgPath, int FrameWidth, int FrameHeight,int step) throws IOException
    {
        super(Image.createImage(ImgPath), FrameWidth, FrameHeight);
        img = Image.createImage(ImgPath);
        this.step = step;
    }

    /**
     * @return the prevDir
     */
    public int getPrevDir()
    {
        return prevDir;
    }

    /**
     * @param prevDir the prevDir to set
     */
    public void setPrevDir(int prevDir)
    {
        this.prevDir = prevDir;
    }

    /**
     * @return the step
     */
    public int getStep()
    {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(int step)
    {
        this.step = step;
    }
    
}

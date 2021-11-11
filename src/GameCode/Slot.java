package GameCode;

import javax.microedition.lcdui.Graphics;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lior
 */
public class Slot extends GameItem
{

    private int Value;
    private boolean problam = false;
    private boolean inherent;
    private boolean EditMode;
    private boolean Hint;

    public Slot(int x, int y, int width, int height, int Value)
    {
        super(x, y, width, height);
        this.Value = Value;
    }

    public void drawMe(Graphics g)
    {
        int prev = g.getColor();
        g.setColor(0, 0, 0);
        if (problam)
        {
            g.setColor(255, 0, 0);
        }
        g.drawRect(x, y, width, height);
        if (inherent == false)
        {
            g.setColor(0, 0, 255);
            if (problam)
            {
                g.setColor(255, 0, 0);
            }
            if(EditMode)
            {
                g.setColor(0,0,0);
            }
            if(Hint)
            {
                g.setColor(0x9400d3);
            }
        }
        if (Value != 0)
        {
            g.drawString(String.valueOf(Value), x + width / 2, y + height / 2 - 5, Graphics.TOP | Graphics.LEFT);
        }
        g.setColor(prev);
    }

    /**
     * @return the x
     */
    public int getX()
    {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x)
    {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY()
    {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y)
    {
        this.y = y;
    }

    /**
     * @return the width
     */
    public int getWidth()
    {
        return width;
    }

    /**
     * @param width the width to set
     */
    public void setWidth(int width)
    {
        this.width = width;
    }

    /**
     * @return the height
     */
    public int getHeight()
    {
        return height;
    }

    /**
     * @param height the height to set
     */
    public void setHeight(int height)
    {
        this.height = height;
    }

    /**
     * @return the Value
     */
    public int getValue()
    {
        return Value;
    }

    /**
     * @param Value the Value to set
     */
    public void setValue(int Value)
    {
        this.Value = Value;
    }

    /**
     * @return the problam
     */
    public boolean isProblam()
    {
        return problam;
    }

    /**
     * @param problam the problam to set
     */
    public void setProblam(boolean problam)
    {
        this.problam = problam;
    }

    /**
     * @return the inherent
     */
    public boolean isInherent()
    {
        return inherent;
    }

    /**
     * @param inherent the inherent to set
     */
    public void setInherent(boolean inherent)
    {
        this.inherent = inherent;
    }

    /**
     * @return the EditMode
     */
    public boolean isEditMode()
    {
        return EditMode;
    }

    /**
     * @param EditMode the EditMode to set
     */
    public void setEditMode(boolean EditMode)
    {
        this.EditMode = EditMode;
    }
    public void setHint(boolean Hint)
    {
        this.Hint = Hint;
    }
    public boolean getHint()
    {
        return Hint;
    }
}

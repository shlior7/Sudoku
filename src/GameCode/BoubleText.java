/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import java.util.Vector;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.game.GameCanvas;

/**
 *
 * @author Lior
 */
public class BoubleText extends GameItem
{

    private String text;
    private Vector Texts;
    private Font font;
    private int numRows;
    private int currentText = 0;

    public BoubleText(int x, int y, int width, int numRows, String text)
    {
        super(x, y, width, 0);
        this.text = text;
        this.numRows = numRows;
        Texts = new Vector();
        TextAnlyzer();
        font = Font.getFont(Font.FACE_PROPORTIONAL, Font.STYLE_PLAIN, Font.SIZE_SMALL);
    }

    public void drawMe(Graphics g)
    {
        g.setColor(0, 0, 0);
        int StringWidth = font.stringWidth(text);
        int StringHeight = font.getHeight();
        String current = (String) Texts.elementAt(currentText);
        g.setFont(font);
        if (numRows == 1)
        {

            g.drawRoundRect(x - StringWidth - 4, y - StringHeight - 4, StringWidth + 4, StringHeight + 4, 10, 10);
            g.drawString(current, x - StringWidth - 2, y - StringHeight - 2, Graphics.TOP | Graphics.LEFT);
        }
        else
        {
            g.drawRoundRect(x - width - 4, y - StringHeight * numRows - 4, width, StringHeight * numRows, 10, 10);
            g.drawString(current, x - width - 2, y - StringHeight * numRows - 2, Graphics.TOP | Graphics.LEFT);
        }
    }

    public void TextAnlyzer()
    {
        String temp = "";
        for (int i = 0; i < text.length(); i++)
        {
            if (text.charAt(i) != '^')
            {
                temp += text.charAt(i);
            }
            else
            {
                Texts.addElement(temp);
                temp = "";
            }
        }
        Texts.addElement(temp);
        for (int i = 0; i < Texts.size(); i++)
        {
            temp = (String) Texts.elementAt(i);
            System.out.println(temp);
            System.out.println("----------");
        }
    }

    /**
     * @return the currentText
     */
    public int getCurrentText()
    {
        return currentText;
    }

    /**
     * @param currentText the currentText to set
     */
    public void setCurrentText(int currentText)
    {
        this.currentText = currentText;
    }

    public void Fire(int keyStates)
    {
        if (keyStates == GameCanvas.FIRE_PRESSED && currentText + 1<Texts.size())
        {
            setCurrentText(currentText + 1);
        }
    }
}

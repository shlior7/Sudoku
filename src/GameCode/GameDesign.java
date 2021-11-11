/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;
import java.io.IOException;

/**
 * @author Lior
 */
public class GameDesign
{
    
//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Image Guy;
    private Image StartBackRound;
    private Image Layer;
    private TiledLayer Intro;
    private Image Woman;
    private Image BadGuy;
    private Image StandOn;
    private TiledLayer Earth;
    private Sprite Player;
    public int DownDelay = 200;
    public int[] Down =
    {
        0, 1, 2
    };
    public int LeftDelay = 200;
    public int[] Left =
    {
        3, 4, 5
    };
    public int RightDelay = 200;
    public int[] Right =
    {
        6, 7, 8
    };
    public int UpDelay = 150;
    public int[] Up =
    {
        9, 10, 11
    };
//</editor-fold>//GEN-END:|fields|0|
    
//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
//</editor-fold>//GEN-END:|methods|0|

    public Image getGuy() throws IOException//GEN-BEGIN:|1-getter|0|1-preInit
    {
        if (Guy == null)
        {//GEN-END:|1-getter|0|1-preInit
            // write pre-init user code here
            Guy = Image.createImage("/images/Guy.png");//GEN-BEGIN:|1-getter|1|1-postInit
        }//GEN-END:|1-getter|1|1-postInit
        // write post-init user code here
        return this.Guy;//GEN-BEGIN:|1-getter|2|
    }//GEN-END:|1-getter|2|



    public Image getLayer() throws IOException//GEN-BEGIN:|5-getter|0|5-preInit
    {
        if (Layer == null)
        {//GEN-END:|5-getter|0|5-preInit
            // write pre-init user code here
            Layer = Image.createImage("/images/Layer.png");//GEN-BEGIN:|5-getter|1|5-postInit
        }//GEN-END:|5-getter|1|5-postInit
        // write post-init user code here
        return this.Layer;//GEN-BEGIN:|5-getter|2|
    }//GEN-END:|5-getter|2|



    public Image getWoman() throws IOException//GEN-BEGIN:|7-getter|0|7-preInit
    {
        if (Woman == null)
        {//GEN-END:|7-getter|0|7-preInit
            // write pre-init user code here
            Woman = Image.createImage("/images/Woman.png");//GEN-BEGIN:|7-getter|1|7-postInit
        }//GEN-END:|7-getter|1|7-postInit
        // write post-init user code here
        return this.Woman;//GEN-BEGIN:|7-getter|2|
    }//GEN-END:|7-getter|2|



    public Image getBadGuy() throws IOException//GEN-BEGIN:|11-getter|0|11-preInit
    {
        if (BadGuy == null)
        {//GEN-END:|11-getter|0|11-preInit
            // write pre-init user code here
            BadGuy = Image.createImage("/images/BadGuy.png");//GEN-BEGIN:|11-getter|1|11-postInit
        }//GEN-END:|11-getter|1|11-postInit
        // write post-init user code here
        return this.BadGuy;//GEN-BEGIN:|11-getter|2|
    }//GEN-END:|11-getter|2|



    public Image getStandOn() throws IOException//GEN-BEGIN:|13-getter|0|13-preInit
    {
        if (StandOn == null)
        {//GEN-END:|13-getter|0|13-preInit
            // write pre-init user code here
            StandOn = Image.createImage("/images/StandOn.png");//GEN-BEGIN:|13-getter|1|13-postInit
        }//GEN-END:|13-getter|1|13-postInit
        // write post-init user code here
        return this.StandOn;//GEN-BEGIN:|13-getter|2|
    }//GEN-END:|13-getter|2|

    public TiledLayer getEarth() throws IOException//GEN-BEGIN:|14-getter|0|14-preInit
    {
        if (Earth == null)
        {//GEN-END:|14-getter|0|14-preInit
            // write pre-init user code here
            Earth = new TiledLayer(16, 2, getStandOn(), 40, 20);//GEN-BEGIN:|14-getter|1|14-midInit
            int[][] tiles =
            {
                {
                    1, 2, 3, 4, 5, 6, 7, 8, 1, 2, 3, 4, 5, 6, 7, 8
                },
                {
                    9, 10, 11, 12, 13, 14, 15, 16, 9, 10, 11, 12, 13, 14, 15, 16
                }
            };//GEN-END:|14-getter|1|14-midInit
            // write mid-init user code here
            for (int row = 0; row < 2; row++)//GEN-BEGIN:|14-getter|2|14-postInit
            {
                for (int col = 0; col < 16; col++)
                {
                    Earth.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|14-getter|2|14-postInit
        // write post-init user code here
        return Earth;//GEN-BEGIN:|14-getter|3|
    }//GEN-END:|14-getter|3|

    public void updateLayerManagerForLevel1(LayerManager lm) throws IOException//GEN-BEGIN:|15-updateLayerManager|0|15-preUpdate
    {//GEN-END:|15-updateLayerManager|0|15-preUpdate
        // write pre-update user code here
        getEarth().setPosition(0, 282);//GEN-BEGIN:|15-updateLayerManager|1|15-postUpdate
        getEarth().setVisible(true);
        lm.append(getEarth());
        getIntro().setPosition(0, 0);
        getIntro().setVisible(true);
        lm.append(getIntro());//GEN-END:|15-updateLayerManager|1|15-postUpdate

    }//GEN-LINE:|15-updateLayerManager|2|

    public Image getStartBackRound() throws IOException//GEN-BEGIN:|21-getter|0|21-preInit
    {
        if (StartBackRound == null)
        {//GEN-END:|21-getter|0|21-preInit
            // write pre-init user code here
            StartBackRound = Image.createImage("/images/StartBackRound.png");//GEN-BEGIN:|21-getter|1|21-postInit
        }//GEN-END:|21-getter|1|21-postInit
        // write post-init user code here
        return this.StartBackRound;//GEN-BEGIN:|21-getter|2|
    }//GEN-END:|21-getter|2|

    public TiledLayer getIntro() throws IOException//GEN-BEGIN:|22-getter|0|22-preInit
    {
        if (Intro == null)
        {//GEN-END:|22-getter|0|22-preInit
            // write pre-init user code here
            Intro = new TiledLayer(2, 1, getStartBackRound(), 320, 282);//GEN-BEGIN:|22-getter|1|22-midInit
            int[][] tiles =
            {
                {
                    1, 1
                }
            };//GEN-END:|22-getter|1|22-midInit
            // write mid-init user code here
            for (int row = 0; row < 1; row++)//GEN-BEGIN:|22-getter|2|22-postInit
            {
                for (int col = 0; col < 2; col++)
                {
                    Intro.setCell(col, row, tiles[row][col]);
                }
            }
        }//GEN-END:|22-getter|2|22-postInit
        // write post-init user code here
        return Intro;//GEN-BEGIN:|22-getter|3|
    }//GEN-END:|22-getter|3|

    public Sprite getPlayer() throws IOException//GEN-BEGIN:|51-getter|0|51-preInit
    {
        if (Player == null)
        {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            Player = new Sprite(getGuy(), 22, 23);//GEN-BEGIN:|51-getter|1|51-postInit
            Player.setFrameSequence(Right);//GEN-END:|51-getter|1|51-postInit
            // write post-init user code here
        }//GEN-BEGIN:|51-getter|2|
        return Player;
    }//GEN-END:|51-getter|2|


    
}

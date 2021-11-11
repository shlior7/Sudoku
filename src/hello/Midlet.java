/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hello;

import GameCode.BoredCanvas;
import GameCode.InputNPlayCanvas;
import GameCode.ListCanvas;
import GameCode.MyCoverCanvas;
import GameCode.PlayCanvas;
import java.io.IOException;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.GameCanvas;

/**
 * @author Lior
 */
public class Midlet extends MIDlet implements CommandListener
{

    private boolean midletPaused = false;
//<editor-fold defaultstate="collapsed" desc=" Generated Fields ">
    private Command exitCommand;
    private Command Next;
    private Command Back;
    private InputNPlayCanvas inputNPlayCanvas;
    private BoredCanvas boredCanvas;
    private MyCoverCanvas myCoverCanvas;
    private ListCanvas listCanvas;
    private PlayCanvas playCanvas;
    private List Level;
    private Font font;
//</editor-fold>
    private int IndexOfList;
    private int levelInt;

    /**
     * The HelloMIDlet constructor.
     */
    public Midlet()
    {
    }

//<editor-fold defaultstate="collapsed" desc=" Generated Methods ">
//</editor-fold>
//<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">
    /**
     * Initializes the application. It is called only once when the MIDlet is
     * started. The method is called before the
     * <code>startMIDlet</code> method.
     */
    private void initialize()
    {
        // write pre-initialize user code here
 
        // write post-initialize user code here
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet()
    {
        // write pre-action user code here
        switchDisplayable(null, getMyCoverCanvas());
        // write post-action user code here
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet()
    {
        // write pre-action user code here
 
        // write post-action user code here
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">
    /**
     * Switches a current displayable in a display. The
     * <code>display</code> instance is taken from
     * <code>getDisplay</code> method. This method is used by all actions in the
     * design for switching displayable.
     *
     * @param alert the Alert which is temporarily set to the display;
     * if <code>null</code>, then <code>nextDisplayable</code> is set
     * immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable)
    {

        Display display = getDisplay();
        if (alert == null)
        {
            display.setCurrent(nextDisplayable);
        }
        else
        {
            display.setCurrent(alert, nextDisplayable);
        }
        // write post-switch user code here
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">
    /**
     * Returns an initialized instance of exitCommand component.
     *
     * @return the initialized component instance
     */
    public Command getExitCommand()
    {
        if (exitCommand == null)
        {
            // write pre-init user code here
            exitCommand = new Command("Exit", Command.EXIT, 0);
            // write post-init user code here
        }
        return exitCommand;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: myCoverCanvas ">
    /**
     * Returns an initialized instance of myCoverCanvas component.
     *
     * @return the initialized component instance
     */
    public MyCoverCanvas getMyCoverCanvas()
    {
        if (myCoverCanvas == null)
        {
            try
            {
                myCoverCanvas = new MyCoverCanvas();
//                myCoverCanvas.setTitle("myCoverCanvas");
                myCoverCanvas.setMidlet(this);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return myCoverCanvas;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: listCanvas ">
    /**
     * Returns an initialized instance of listCanvas component.
     *
     * @return the initialized component instance
     */
    public ListCanvas getListCanvas()
    {
        if (listCanvas == null)
        {
            try
            {
                listCanvas = new ListCanvas();
//                listCanvas.setTitle("listCanvas");
                listCanvas.setMidlet(this);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return listCanvas;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Next ">
    /**
     * Returns an initialized instance of Next component.
     *
     * @return the initialized component instance
     */
    public Command getNext()
    {
        if (Next == null)
        {
            // write pre-init user code here
            Next = new Command("Ok", Command.OK, 0);
            // write post-init user code here
        }
        return Next;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: playCanvas ">
    /**
     * Returns an initialized instance of playCanvas component.
     *
     * @return the initialized component instance
     */
    public PlayCanvas getPlayCanvas()
    {
        if (playCanvas == null)
        {
            try
            {
                playCanvas = new PlayCanvas(this);
//                playCanvas.setTitle("playCanvas");
                playCanvas.addCommand(getBack());
                playCanvas.setCommandListener(this);
                playCanvas.setMidlet(this);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return playCanvas;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">
    /**
     * Called by a system to indicated that a command has been invoked on a
     * particular displayable.
     *
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable)
    {
        // write pre-action user code here
        if (displayable == Level)
        {
            if (command == Back)
            {
                // write pre-action user code here
                switchDisplayable(null, getListCanvas());
                // write post-action user code here
            }
            else if (command == List.SELECT_COMMAND)
            {
                // write pre-action user code here
                LevelAction();
                // write post-action user code here
            }
        }
        else if (displayable == boredCanvas)
        {
            if (command == Back)
            {
                // write pre-action user code here
                switchDisplayable(null, getListCanvas());
                // write post-action user code here
            }
        }
        else if (displayable == inputNPlayCanvas)
        {
            if (command == Back)
            {
                inputNPlayCanvas.setIWantToQuit();
 
                
            }
        }
        else if (displayable == playCanvas)
        {
            if (command == Back)
            {
                playCanvas.setIWantToQuit();
 

            }
        }
        // write post-action user code here
    }
//</editor-fold>



//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Back ">
    /**
     * Returns an initialized instance of Back component.
     *
     * @return the initialized component instance
     */
    public Command getBack()
    {
        if (Back == null)
        {
            // write pre-init user code here
            Back = new Command("Back", Command.BACK, 0);
            // write post-init user code here
        }
        return Back;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: inputNPlayCanvas ">
    /**
     * Returns an initialized instance of inputNPlayCanvas component.
     *
     * @return the initialized component instance
     */
    public InputNPlayCanvas getInputNPlayCanvas()
    {
        if (inputNPlayCanvas == null)
        {
            try
            {
                inputNPlayCanvas = new InputNPlayCanvas();
//                inputNPlayCanvas.setTitle("inputNPlayCanvas");
                inputNPlayCanvas.addCommand(getBack());
                inputNPlayCanvas.setCommandListener(this);
                inputNPlayCanvas.setMidlet(this);
            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return inputNPlayCanvas;
    }
//</editor-fold>





//<editor-fold defaultstate="collapsed" desc=" Generated Getter: font ">
    /**
     * Returns an initialized instance of font component.
     *
     * @return the initialized component instance
     */
    public Font getFont()
    {
        if (font == null)
        {
            // write pre-init user code here
            font = Font.getDefaultFont();
            // write post-init user code here
        }
        return font;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: boredCanvas ">
    /**
     * Returns an initialized instance of boredCanvas component.
     *
     * @return the initialized component instance
     */
    public BoredCanvas getBoredCanvas()
    {
        if (boredCanvas == null)
        {
            try
            {
                boredCanvas = new BoredCanvas();
//                boredCanvas.setTitle("boredCanvas");
                boredCanvas.addCommand(getBack());
                boredCanvas.setCommandListener(this);

            }
            catch (IOException ex)
            {
                ex.printStackTrace();
            }
        }
        return boredCanvas;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Getter: Level ">
    /**
     * Returns an initialized instance of Level component.
     *
     * @return the initialized component instance
     */
    public List getLevel()
    {
        if (Level == null)
        {
            // write pre-init user code here
            Level = new List("Choose The Level", Choice.EXCLUSIVE);
            Level.append("Easy", null);
            Level.append("Medium", null);
            Level.append("Hard", null);
            Level.append("Impossible", null);
            Level.addCommand(getBack());
            Level.setCommandListener(this);
            Level.setSelectedFlags(new boolean[]
                    {
                        false, false, false, false
                    });
            // write post-init user code here
        }
        return Level;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc=" Generated Method: LevelAction ">
    /**
     * Performs an action assigned to the selected list element in the Level
     * component.
     */
    public void LevelAction()
    {
        // enter pre-action user code here
        String __selectedString = getLevel().getString(getLevel().getSelectedIndex());
        if (__selectedString != null)
        {
            if (__selectedString.equals("Easy"))
            {
                // write pre-action user code here
 
                // write post-action user code here
            }
            else if (__selectedString.equals("Medium"))
            {
                // write pre-action user code here
 
                // write post-action user code here
            }
            else if (__selectedString.equals("Hard"))
            {
                // write pre-action user code here
 
                // write post-action user code here
            }
            else if (__selectedString.equals("Impossible"))
            {
                // write pre-action user code here
 
                // write post-action user code here
            }
        }
        // enter post-action user code here
    }
//</editor-fold>


        // enter pre-action user code here

        // enter post-action user code here

    
    public void EpusPlayCanvas()
    {
        playCanvas = null;
    }
    public void EpusCoverCanvas()
    {
         myCoverCanvas = null;
    }
     public void EpusInputPlayCanvas()
    {
        inputNPlayCanvas = null;
    }
    public void setTheListInedx(int index)
    {
        this.IndexOfList = index;
    }

    public int getTheListInedx()
    {
        return IndexOfList;
    }

    /**
     * Returns a display instance.
     *
     * @return the display instance.
     */
    public Display getDisplay()
    {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet()
    {
        switchDisplayable(null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started. Checks whether the MIDlet have been
     * already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp()
    {
        if (midletPaused)
        {
            resumeMIDlet();
        }
        else
        {
            initialize();
            startMIDlet();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp()
    {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     *
     * @param unconditional if true, then the MIDlet has to be unconditionally
     * terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional)
    {
    }

    /**
     * @return the levelInt
     */
    public int getLevelInt()
    {
        return levelInt;
    }

    /**
     * @param levelInt the levelInt to set
     */
    public void setLevelInt(int levelInt)
    {
        this.levelInt = levelInt;
    }

   
}

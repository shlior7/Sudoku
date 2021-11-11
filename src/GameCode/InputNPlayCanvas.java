/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import hello.Midlet;
import java.io.IOException;
import java.util.Timer;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;

/**
 *
 * @author Lior
 */
public class InputNPlayCanvas extends GameCanvas implements Runnable
{

    private int width;
    private int height;
    private Graphics g;
    private Timer myTimer;
    private boolean isRunning;
    private Thread myThread;
    private Slot[][] Board;
    private int[][] Mat;
    private Selector selector;
    private Selector AreYouSure;
    private SudokuSolver SudSolve;
    private Sprite Win;
    private boolean Won = false;
    private boolean EditState = true;
    private boolean ValidSudoku = true;
    private int Finished;
    private int Remain = 81;
    private Image img;
    private TimeTimerTask myTask;
    private boolean Quit = false;
    private Midlet midlet;
    private int CounterHints = -1;
    private int HintX;
    private int HintY;
    private int HintLocation;
    private FlickerElement HintHelp;
    private WinTimerTask myWinTask;

    public InputNPlayCanvas() throws IOException
    {
        super(false);
        g = getGraphics();
        width = this.getWidth();
        height = this.getHeight();
        init();
        StartMyTask(myTimer);
        StartThread();
    }

    public void init() throws IOException
    {
        Win = new Sprite(Image.createImage("images/YouWon.png"), 238, 296);
        Win.setPosition(1, 1);
        Mat = new int[9][9];
        Board = new Slot[9][9];
        selector = new Selector(3, 3, 26, 28);
        img = Image.createImage("images/AreYouSure.png");
        HintHelp = new FlickerElement(0, 0, 0, 0, 0);
        AreYouSure = new Selector((width - img.getWidth()) / 2 + 18, (height - img.getHeight()) / 2 + 48, 44, 21);
        myTimer = new Timer();
        SudSolve = new SudokuSolver(Mat);
        for (int i = 0; i < Board.length; i++)
        {
            for (int j = 0; j < Board.length; j++)
            {
                Board[i][j] = new Slot(26 * j + 3, 28 * i + 3, 26, 28, 0);
                Board[i][j].setEditMode(true);
            }
        }
        StartMyWinTask(myTimer);
    }

    public void DrawAll() throws IOException
    {
        if (Quit == false)
        {
            g.setColor(0, 0, 0);
            g.fillRect(0, 0, width, height);
            g.drawImage(Image.createImage("images/Table.png"), 0, 0, Graphics.TOP | Graphics.LEFT);
            if (Won == false)
            {
                g.drawString("Remain : " + Remain, 10, 260, Graphics.TOP | Graphics.LEFT);
                g.drawString("Finished : " + Finished, width / 2, 260, Graphics.TOP | Graphics.LEFT);
                g.drawString("Time : " + myTask.getTimeCounter() / 60 + ":" + myTask.getTimeCounter() % 60, 10, 300, Graphics.TOP | Graphics.LEFT);
                g.drawImage(Image.createImage("images/Paper.png"), 1, 2, Graphics.TOP | Graphics.LEFT);
                g.setColor(0xcdcdc1);
                g.fillRect(width / 2, 295, 100, 22);
                g.setColor(0, 0, 0);
                g.drawString("Hint", width / 2 + 40, 300, Graphics.TOP | Graphics.LEFT);
                if (CounterHints >= 0)
                {
                    HintHelp.drawMe(g);
                }
                for (int i = 0; i <= CounterHints; i++)
                {
                    g.drawLine(width / 2 + (100 * i / 3), 295, width / 2 + (100 * i / 3) + 100 / 3, 295 + 22);
                    g.drawLine(width / 2 + (100 * i / 3), 295 + 22, width / 2 + (100 * i / 3) + 100 / 3, 295);
                }
                selector.drawMe(g, Quit);
                g.setColor(0, 0, 0);
                Vector tempSlot = new Vector();
                for (int i = 0; i < Board.length; i++)
                {
                    for (int j = 0; j < Board.length; j++)
                    {
                        if (Board[i][j].isProblam() == false)
                        {
                            Board[i][j].drawMe(g);
                        }
                        else
                        {
                            tempSlot.addElement(Board[i][j]);
                        }
                    }
                }
                g.drawLine(26 * 3 + 4, 3, 26 * 3 + 4, 28 * 9 + 3);
                g.drawLine(26 * 6 + 4, 3, 26 * 6 + 4, 28 * 9 + 3);
                g.drawLine(3, 28 * 3 + 4, 26 * 9 + 3, 28 * 3 + 4);
                g.drawLine(3, 28 * 6 + 4, 26 * 9 + 3, 28 * 6 + 4);

                for (int i = 0; i < tempSlot.size(); i++)
                {
                    Slot object = (Slot) tempSlot.elementAt(i);
                    object.drawMe(g);

                }

                if (ValidSudoku == false)
                {
                    g.drawString("Please Enter A Valid Sudoku", 50, 280, Graphics.TOP | Graphics.LEFT);
                }
            }
            else
            {
                Win.setVisible(true);
                Win.paint(g);
            }
        }
        else
        {
            g.drawImage(img, (width - img.getWidth()) / 2, (height - img.getHeight()) / 2, Graphics.TOP | Graphics.LEFT);
            AreYouSure.drawMe(g, Quit);
        }
        flushGraphics();
    }

    public void setMidlet(Midlet midlet)
    {
        this.midlet = midlet;
    }

    protected void keyPressed(int keyCode)
    {
        int gameCode = this.getGameAction(keyCode);
        if (gameCode == Canvas.FIRE)
        {
            if (Quit)
            {
                if (AreYouSure.getX() < width / 2 - 20)
                {
                    midlet.EpusPlayCanvas();
                    StopThread();
                    myWinTask.StopMe();
                    myTask.StopMe();
                    myWinTask = null;
                    myTask = null;
//                    midlet.switchDisplayable(null, midlet.getListCanvas());
                }
                else
                {
                    Quit = false;
                    myWinTask.StartMe();
                    myTask.StartMe();
                    if (HintHelp.getMyTask() != null)
                    {
                        HintHelp.getMyTask().StartMe();
                    }
                }
            }
            else
            {
                if (selector.getY() > 255 && CounterHints < 2)
                {
                    Hint(SudSolve);
                }
            }
        }
        if (Quit)
        {
            AreYouSure.MoveMe(gameCode, width, height, Quit);
        }
        else
        {
            selector.MoveMe(gameCode, width, height, false);
            switch (keyCode)
            {
                case Canvas.KEY_POUND:
                    if (EditState)
                    {
                        for (int i = 0; i < Board.length; i++)
                        {
                            for (int j = 0; j < Board.length; j++)
                            {
                                Mat[i][j] = Board[i][j].getValue();
                            }
                        }

                        SudSolve.setMat(Mat);
                        SudSolve.SolveSudoku(Mat);
                        if (SudSolve.isSuccess() == false)
                        {
                            ValidSudoku = false;
                        }
                        else
                        {
                            EditState = false;
                            for (int i = 0; i < Board.length; i++)
                            {
                                for (int j = 0; j < Board.length; j++)
                                {
                                    Board[i][j].setEditMode(false);
                                }
                            }
                            ValidSudoku = true;
                        }
                    }
                    break;
                case Canvas.KEY_STAR:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() != 0)
                            {
                                if (Board[i][j].isInherent() == false || EditState)
                                {
                                    if (Board[i][j].isProblam() == false)
                                    {
                                        if (EditState == false)
                                        {
                                            Finished--;
                                        }
                                        Remain++;
                                    }
                                    Board[i][j].setProblam(false);
                                    int[][] temp = SudSolve.getMat();
                                    temp[i][j] = 0;
                                    SudSolve.setMat(temp);
                                    int x = -1, y = -1;
                                    int counter = 0;
                                    for (int k = i - i % 3; k < (i - i % 3) + 3; k++)
                                    {
                                        for (int m = j - j % 3; m < (j - j % 3) + 3; m++)
                                        {
                                            System.out.println(temp[k][m] + "        " + Board[i][j].getValue());
                                            if (temp[k][m] == Board[i][j].getValue())
                                            {
                                                counter++;
                                                x = k;
                                                y = m;
                                            }
                                        }
                                    }
                                    if (counter == 1)
                                    {
                                        Board[x][y].setProblam(false);
                                    }
                                    counter = 0;
                                    for (int m = 0; m < 9; m++)//עובר על השורה שהמספר הנמצא ובודק אם הוא היחיד שיכול ליהיות שם
                                    {
                                        if (temp[i][m] == Board[i][j].getValue())
                                        {
                                            counter++;
                                            y = m;

                                        }
                                    }
                                    if (counter == 1)
                                    {
                                        Board[i][y].setProblam(false);
                                    }
                                    counter = 0;
                                    for (int m = 0; m < 9; m++)//עובר על עמודה שהמספר הנמצא ובודק אם הוא היחיד שיכול ליהיות שם
                                    {
                                        if (temp[m][j] == Board[i][j].getValue())
                                        {
                                            counter++;
                                            x = m;
                                        }
                                    }
                                    if (counter == 1)
                                    {
                                        Board[x][j].setProblam(false);
                                    }
                                    Board[i][j].setValue(0);
                                }

                            }
                        }
                    }
                    break;

                case Canvas.KEY_NUM1:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(1);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;

                case Canvas.KEY_NUM2:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(2);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;

                case Canvas.KEY_NUM3:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(3);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;

                case Canvas.KEY_NUM4:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(4);
                                Check(Board, i, j);
                            }
                        }
                    }
                    break;

                case Canvas.KEY_NUM5:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(5);
                                Check(Board, i, j);
                            }
                        }
                    }
                    break;
                case Canvas.KEY_NUM6:

                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(6);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;
                case Canvas.KEY_NUM7:

                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(7);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;

                case Canvas.KEY_NUM8:

                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(8);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;

                case Canvas.KEY_NUM9:

                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() == 0)
                            {
                                Board[i][j].setValue(9);
                                Check(Board, i, j);
                            }
                        }

                    }
                    break;
            }

        }
    }

    public void Check(Slot[][] Duo, int i, int j)
    {
        int[][] temp = SudSolve.getMat();
        temp[i][j] = Duo[i][j].getValue();
        SudSolve.setMat(temp);
        if (EditState)
        {
            Board[i][j].setInherent(true);
        }
        for (int k = i - i % 3; k < (i - i % 3) + 3; k++)
        {
            for (int m = j - j % 3; m < (j - j % 3) + 3; m++)
            {
                if (Duo[k][m].getValue() == Duo[i][j].getValue() && k != i && m != j)
                {
                    Duo[i][j].setProblam(true);
                    return;
                }
            }
        }

        for (int m = 0; m < 9; m++)//עובר על העמודה שהמספר הנמצא ובודק אם הוא היחיד שיכול ליהיות שם
        {
            if (Duo[i][m].getValue() == Duo[i][j].getValue() && m != j)
            {
                Duo[i][j].setProblam(true);
                return;
            }
        }
        for (int m = 0; m < 9; m++)//עובר על השורה שהמספר הנמצא ובודק אם הוא היחיד שיכול ליהיות שם
        {
            if (Duo[m][j].getValue() == Duo[i][j].getValue() && m != i)
            {
                Duo[i][j].setProblam(true);
                return;
            }
        }
        Duo[i][j].setProblam(false);
        if (EditState == false)
        {
            Finished++;
        }
        Remain--;
    }

    public boolean Win(Slot[][] Board)
    {
        for (int i = 0; i < Board.length; i++)
        {
            for (int j = 0; j < Board.length; j++)
            {
                if (Board[i][j].getValue() == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public void Hint(SudokuSolver SudSolve)
    {
        if (EditState == false)
        {
            String str = SudSolve.Hint();
            HintX = Integer.parseInt(str.charAt(0) + "");
            HintY = Integer.parseInt(str.charAt(1) + "");
            HintLocation = Integer.parseInt(str.charAt(2) + "");
            HintHelp.setHint(HintLocation);
            switch (HintLocation)
            {
                case 0:
                    HintHelp.setX(Board[HintX - HintX % 3][HintY - HintY % 3].getX());
                    HintHelp.setY(Board[HintX - HintX % 3][HintY - HintY % 3].getY());
                    HintHelp.setWidth(26 * 3);
                    HintHelp.setHeight(28 * 3);
                    break;
                case 1:
                    HintHelp.setX(Board[HintX][0].getX());
                    HintHelp.setY(Board[HintX][0].getY());
                    HintHelp.setWidth(26 * 9);
                    HintHelp.setHeight(28);
                    break;
                case 2:
                    HintHelp.setX(Board[0][HintY].getX());
                    HintHelp.setY(Board[0][HintY].getY());
                    HintHelp.setWidth(26);
                    HintHelp.setHeight(28 * 9);
                    break;
            }
            HintHelp.StartMyTask(myTimer);
            System.out.println(str + "     " + HintHelp.getX() + HintHelp.getY());
            CounterHints++;
        }
    }

    public void setIWantToQuit()
    {
        Quit = true;
        AreYouSure.setPrevDir(Canvas.LEFT);
        myTask.PauseMe();
        myWinTask.PauseMe();
        AreYouSure.setX((width - img.getWidth()) / 2 + 18);
    }

    public void StartMyTask(Timer timer)
    {
        if (myTask == null)
        {
            this.myTimer = timer;
            myTask = new TimeTimerTask();
            myTask.StartMe();
            myTimer.schedule(myTask, 0, 1000);
        }
    }

    public void StartMyWinTask(Timer timer)
    {
        if (myWinTask == null)
        {
            this.myTimer = timer;
            myWinTask = new WinTimerTask(Win);
            myWinTask.StartMe();
            myTimer.schedule(myWinTask, 0, 150);
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
        while (isRunning)
        {
            try
            {
                if (Quit == false)
                {
                    Won = (Remain == 0 && EditState == false);
                    if (Won)
                    {
                        Win.setVisible(true);
                    }
                    DrawAll();
                }
                else
                {
                    DrawAll();
                }
            }
            catch (IOException ex)
            {
                ex.printStackTrace();

            }
            try
            {
                Thread.sleep(90);
            }
            catch (InterruptedException ex)
            {
                ex.printStackTrace();
            }
        }
    }
    /**
     * @param midlet the midlet to set
     */
}

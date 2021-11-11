/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

import hello.Midlet;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.Vector;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.midlet.MIDlet;

/**
 *
 * @author Lior
 */
public class PlayCanvas extends GameCanvas implements Runnable
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
    private Vector Sudokoes;
    private boolean Won;
    private int Finished;
    private int Remain;
    private Midlet midlet;
    private boolean Quit = false;
    private Image img;
    private TimeTimerTask myTask;
    private int CounterHints = -1;
    private int HintX;
    private int HintY;
    private int HintLocation;
    private FlickerElement HintHelp;
    private WinTimerTask myWinTask;

    public PlayCanvas(Midlet midlet) throws IOException
    {
        super(false);
        g = getGraphics();
        this.midlet = midlet;
        width = this.getWidth();
        height = this.getHeight();
        init();
        SudSolve.SolveSudoku(Mat);
        StartMyTask(myTimer);
        StartThread();
    }

    public void init() throws IOException
    {
        Win = new Sprite(Image.createImage("images/YouWon.png"), 238, 296);
        Win.setPosition(1, 1);
        Mat = new int[9][9];
        Board = new Slot[9][9];
        Sudokoes = new Vector();
        HintHelp = new FlickerElement(0, 0, 0, 0, 0);
        img = Image.createImage("images/AreYouSure.png");
        SudokuCreator(Sudokoes);
        Mat = ChoosTheSudoku(midlet, Sudokoes);
        selector = new Selector(3, 3, 26, 28);
        AreYouSure = new Selector((width - img.getWidth()) / 2 + 18, (height - img.getHeight()) / 2 + 48, 44, 21);
        for (int i = 0; i < Board.length; i++)
        {
            for (int j = 0; j < Board.length; j++)
            {
                Board[i][j] = new Slot(26 * j + 3, 28 * i + 3, 26, 28, Mat[i][j]);
                if (Mat[i][j] != 0)
                {
                    Board[i][j].setInherent(true);
                }
                else
                {
                    Remain++;
                }
            }
        }
        SudSolve = new SudokuSolver(Mat);
        myTimer = new Timer();
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
                g.setColor(0, 0, 0);
                g.drawLine(26 * 3 + 4, 3, 26 * 3 + 4, 28 * 9 + 3);
                g.drawLine(26 * 6 + 4, 3, 26 * 6 + 4, 28 * 9 + 3);
                g.drawLine(3, 28 * 3 + 4, 26 * 9 + 3, 28 * 3 + 4);
                g.drawLine(3, 28 * 6 + 4, 26 * 9 + 3, 28 * 6 + 4);
                for (int i = 0; i < tempSlot.size(); i++)
                {
                    Slot object = (Slot) tempSlot.elementAt(i);
                    object.drawMe(g);

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
                    midlet.switchDisplayable(null, midlet.getListCanvas());
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
            selector.MoveMe(gameCode, width, height, Quit);
            switch (keyCode)
            {
                case Canvas.KEY_STAR:
                    for (int i = 0; i < Board.length; i++)
                    {
                        for (int j = 0; j < Board.length; j++)
                        {
                            if (Board[i][j].getX() == selector.getX() && Board[i][j].getY() == selector.getY() && Board[i][j].getValue() != 0)
                            {
                                if (Board[i][j].isInherent() == false)
                                {
                                    if (Board[i][j].isProblam() == false)
                                    {
                                        Remain++;
                                        Finished--;
                                    }
                                    Board[i][j].setProblam(false);
                                    int[][] temp = SudSolve.getMat();
                                    temp[i][j] = 0;
                                    SudSolve.setMat(temp);
                                    int counter = 0;
                                    for (int k = i - i % 3; k < (i - i % 3) + 3; k++)
                                    {
                                        for (int m = j - j % 3; m < (j - j % 3) + 3; m++)
                                        {
                                            System.out.println(temp[k][m] + "        " + Board[i][j].getValue());
                                            if (temp[k][m] == Board[i][j].getValue())
                                            {
                                                counter++;
                                                Board[k][m].setProblam(false);
                                                if (counter > 1)
                                                {
                                                    Board[k][m].setProblam(true);
                                                }
                                                System.out.println(counter);
                                            }
                                        }
                                    }
                                    counter = 0;
                                    for (int m = 0; m < 9; m++)//עובר על השורה שהמספר הנמצא ובודק אם הוא היחיד שיכול ליהיות שם
                                    {
                                        if (temp[i][m] == Board[i][j].getValue())
                                        {
                                            counter++;
                                            Board[i][m].setProblam(false);
                                            if (counter > 1)
                                            {
                                                Board[i][m].setProblam(true);
                                            }
                                        }
                                    }

                                    counter = 0;
                                    for (int m = 0; m < 9; m++)//עובר על עמודה שהמספר הנמצא ובודק אם הוא היחיד שיכול ליהיות שם
                                    {
                                         if (temp[m][i] == Board[i][j].getValue())
                                        {
                                            counter++;
                                            Board[m][i].setProblam(false);
                                            if (counter > 1)
                                            {
                                                Board[m][i].setProblam(true);
                                            }
                                        }
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

    public void SudokuCreator(Vector v)
    {
        int[][] easy1 =
        {
            {
                5, 3, 0, 0, 7, 0, 0, 0, 0
            },
            {
                6, 0, 0, 1, 9, 5, 0, 0, 0
            },
            {
                0, 9, 8, 0, 0, 0, 0, 6, 0
            },
            {
                8, 0, 0, 0, 6, 0, 0, 0, 3
            },
            {
                4, 0, 0, 8, 0, 3, 0, 0, 1
            },
            {
                7, 0, 0, 0, 2, 0, 0, 0, 6
            },
            {
                0, 6, 0, 0, 0, 0, 2, 8, 0
            },
            {
                0, 0, 0, 4, 1, 9, 0, 0, 5
            },
            {
                0, 0, 0, 0, 8, 0, 0, 7, 9
            }
        };
        v.addElement(easy1);
        v.addElement(RotateIllusion(easy1));
        easy1 = null;
        int[][] easy2 =
        {
            {
                3, 0, 0, 2, 4, 0, 0, 6, 0
            },
            {
                0, 4, 0, 0, 0, 0, 0, 5, 3
            },
            {
                1, 8, 9, 6, 3, 5, 4, 0, 0
            },
            {
                0, 0, 0, 0, 8, 0, 2, 0, 0
            },
            {
                0, 0, 7, 4, 9, 6, 8, 0, 1
            },
            {
                8, 9, 3, 1, 5, 0, 6, 0, 4
            },
            {
                0, 0, 1, 9, 2, 0, 5, 0, 0
            },
            {
                2, 0, 0, 3, 0, 0, 7, 4, 0
            },
            {
                9, 6, 0, 5, 0, 0, 3, 0, 2
            }
        };
        v.addElement(easy2);
        v.addElement(RotateIllusion(easy2));
        easy2 = null;
        int[][] medium1 =
        {
            {
                0, 0, 0, 9, 0, 7, 0, 0, 0
            },
            {
                9, 0, 0, 0, 0, 0, 0, 0, 8
            },
            {
                0, 3, 0, 4, 0, 5, 0, 2, 0
            },
            {
                3, 0, 7, 0, 4, 0, 2, 0, 6
            },
            {
                0, 0, 0, 5, 0, 9, 0, 0, 0
            },
            {
                8, 0, 9, 0, 2, 0, 1, 0, 3
            },
            {
                0, 7, 0, 6, 0, 4, 0, 3, 0
            },
            {
                2, 0, 0, 0, 0, 0, 0, 0, 9
            },
            {
                0, 0, 0, 1, 0, 2, 0, 0, 0
            }
        };
        v.addElement(medium1);
        v.addElement(RotateIllusion(medium1));
        medium1 = null;
        int[][] medium2 =
        {
            {
                0, 5, 0, 0, 0, 0, 0, 3, 0
            },
            {
                8, 0, 4, 0, 0, 7, 0, 0, 1
            },
            {
                0, 0, 0, 1, 0, 5, 0, 2, 0
            },
            {
                0, 9, 3, 8, 0, 6, 4, 0, 0
            },
            {
                0, 0, 0, 0, 4, 0, 0, 0, 0
            },
            {
                0, 0, 2, 5, 0, 1, 6, 8, 0
            },
            {
                0, 1, 0, 9, 0, 3, 0, 0, 0
            },
            {
                4, 0, 0, 6, 0, 0, 3, 0, 5
            },
            {
                0, 6, 0, 0, 0, 0, 0, 1, 0
            }
        };
        v.addElement(medium2);
        v.addElement(RotateIllusion(medium2));
        medium2 = null;
    }

    public void Check(Slot[][] Duo, int i, int j)
    {
        int[][] temp = SudSolve.getMat();
        temp[i][j] = Duo[i][j].getValue();
        SudSolve.setMat(temp);
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
        Finished++;
        Remain--;
    }

    public int[][] RotateIllusion(int[][] temp)
    {
        int[][] temp2 = new int[temp.length][temp.length];
        for (int i = 0; i < temp2.length; i++)
        {
            for (int j = 0; j < temp2.length; j++)
            {
                temp2[i][j] = temp[j][i];
            }
        }
        return temp2;
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

    public void setIWantToQuit()
    {
        Quit = true;
        AreYouSure.setPrevDir(Canvas.LEFT);
        myTask.PauseMe();
        myWinTask.PauseMe();
        if (HintHelp.getMyTask() != null)
        {
            HintHelp.getMyTask().PauseMe();
        }
        AreYouSure.setX((width - img.getWidth()) / 2 + 18);
    }

    public int[][] ChoosTheSudoku(Midlet midlet, Vector v)
    {
        Random rnd = new Random();
        if (midlet.getLevel().getSelectedIndex() == 0)
        {
            int[][] temp = (int[][]) v.elementAt(rnd.nextInt(4));
            return temp;
        }
        if (midlet.getLevel().isSelected(1))
        {
            int[][] temp = (int[][]) v.elementAt(rnd.nextInt(4) + 4);
            return temp;
        }
        return null;
    }

    public void Hint(SudokuSolver SudSolve)
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
        CounterHints++;
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
            myTimer.schedule(myWinTask, 0, 400);
        }
    }

    public void run()
    {
        while (isRunning)
        {

            try
            {
                if (Quit == false)
                {
                    Won = (Remain == 0);
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
        }
        try
        {
            Thread.sleep(100);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }
}

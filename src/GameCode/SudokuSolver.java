package GameCode;

import java.util.*;

public class SudokuSolver
{

    private static int[][] Mat;
    private static int[][][] luah;
    private boolean Success;
    private static Random rnd = new Random();

    public SudokuSolver(int[][] Mat)
    {
        luah = new int[9][9][10];
        this.Mat = Mat;
        input(luah, Mat);
    }

    public void SolveSudoku(int[][] Mat)
    {
        int counter = 0;
        while (win(luah) == false && counter != -1)
        {
            for (int i = 0; i < luah.length; i++)
            {
                for (int j = 0; j < luah[i].length; j++)
                {
                    if (luah[i][j][0] == 0)
                    {
                        sinun(luah, i, j);
                        epus(luah);
                        LastResort(luah, i, j);
                        OnlyOneOption(luah, i, j);
                    }
                    if (counter == 1000)
                    {
                        counter = -2;
                        Success = false;
                    }
                }
            }
            counter++;
        }

        if (counter != -1)
        {
            Success = true;
        }
        else
        {
            for (int i = 0; i < luah.length; i++)
            {
                for (int j = 0; j < luah[i].length; j++)
                {
                    Mat[i][j] = luah[i][j][0];
                }
            }
        }
        print(luah, 0);
        System.out.println(counter);
    }

    public static void input(int[][][] tlat, int[][] Duo)
    {
        for (int i = 0; i < Duo.length; i++)
        {
            for (int j = 0; j < Duo.length; j++)
            {
                tlat[i][j][0] = Duo[i][j];
                if (tlat[i][j][0] == 0)
                {
                    for (int k = 1; k < tlat[i][j].length; k++)
                    {
                        tlat[i][j][k] = k;
                    }
                }
            }

        }
    }

    public static void epus(int[][][] tlat)
    {
        for (int i = 0; i < tlat.length; i++)
        {
            for (int j = 0; j < tlat[i].length; j++)
            {
                if (tlat[i][j][0] != 0)
                {
                    for (int k = 1; k < tlat[i][j].length; k++)
                    {
                        tlat[i][j][k] = 0;
                    }
                }
            }
        }
    }

    public static void print(int[][][] tlat, int num)
    // המארך
    {
        for (int i = 0; i < tlat.length; i++)
        {
            for (int j = 0; j < tlat[i].length; j++)
            {
                if (tlat[i][j][num] != 0)
                {
                    System.out.print(" " + tlat[i][j][num] + " ");
                }
                else
                {
                    System.out.print("   ");
                }
                if (j % 3 == 2)
                {
                    System.out.print("||");
                }
                else
                {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i % 3 == 2)
            {
                for (int j = 0; j < 40; j++)
                {
                    System.out.print("=");
                }
            }
            else
            {
                for (int j = 0; j < 40; j++)
                {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public static boolean win(int[][][] tlat)
    {
        for (int i = 0; i < tlat.length; i++)
        {
            for (int j = 0; j < tlat[i].length; j++)
            {
                if (tlat[i][j][0] == 0)
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static void LastResort(int[][][] tlat, int i, int j)
    {
        int counter = 0;
        for (int k = 1; k < tlat[i][j].length; k++)
        {
            if (tlat[i][j][k] != 0)
            {
                counter++;
                tlat[i][j][0] = tlat[i][j][k];
            }
        }
        if (counter != 1)
        {
            tlat[i][j][0] = 0;
        }
    }

    public static int OnlyOneOption(int[][][] tlat, int i, int j)
    {
        int counter1 = 0;
        int HintLocation = -1;
        for (int l = 1; l < 10; l++)
        {
            for (int k = i - i % 3; k < (i - i % 3) + 3; k++)
            {
                for (int m = j - j % 3; m < (j - j % 3) + 3; m++)
                {
                    if (tlat[k][m][l] == 0)
                    {
                        counter1++;
                    }
                }
            }
            if (counter1 == 8)
            {
                tlat[i][j][0] = tlat[i][j][l];
                HintLocation = 0;
            }
            counter1 = 0;

            for (int m = 0; m < 9; m++)
            {
                if (tlat[i][m][l] == 0)
                {
                    counter1++;
                }
            }
            if (counter1 == 8)
            {
                tlat[i][j][0] = tlat[i][j][l];
                
                HintLocation = 1;
            }
            counter1 = 0;
            for (int m = 0; m < 9; m++)
            {
                if (tlat[m][j][l] == 0)
                {
                    counter1++;
                    
                }
            }
            if (counter1 == 8)
            {
                tlat[i][j][0] = tlat[i][j][l];
                HintLocation = 2;
            }
            counter1 = 0;
        }
        if(tlat[i][j][0] == 0)
            return -1;
        return HintLocation;
    }

    public static boolean isCoupled(int[][][] luah, int i, int j, int[] couples)
    {
        int counter = 0;
        for (int d = 1; d < 10; d++)
        {
            if (luah[i][j][d] != 0)
            {
                counter++;
                if (counter == 1)
                {
                    couples[0] = luah[i][j][d];
                }
                else
                {
                    if (counter == 2)
                    {
                        couples[1] = luah[i][j][d];
                    }
                    else
                    {
                        couples[0] = 0;
                        couples[1] = 0;
                        return false;
                    }
                }
            }
        }

        if (counter <= 1)
        {
            return false;
        }
        return true;
    }

    public static String Hint()
    {
        int[][][] Board = new int[9][9][10];
        int HintLocation = -1;
        input(Board, Mat);
        for (int i = 0; i < Board.length; i++)
        {
            for (int j = 0; j < Board.length; j++)
            {
                sinun(Board, i, j);
            }
        }
        int x = rnd.nextInt(9);
        int y = rnd.nextInt(9);
        while (Board[x][y][0] != 0)
        {
            x = rnd.nextInt(9);
            y = rnd.nextInt(9);
        }
        while (Board[x][y][0] == 0)
        {
            HintLocation = OnlyOneOption(Board, x, y);
            if (Board[x][y][0] == 0)
            {
                x = rnd.nextInt(9);
                y = rnd.nextInt(9);
                while (Board[x][y][0] != 0)
                {
                    x = rnd.nextInt(9);
                    y = rnd.nextInt(9);
                }

            }
        }
        Mat[x][y] = Board[x][y][0];
        return x + "" + y + "" + HintLocation;
    }

    public static void sinun(int[][][] luah, int i, int j)
    {
        int[] couples = new int[2];
        int[] secCouples = new int[2];
        boolean isCoupled = isCoupled(luah, i, j, couples);
        for (int k = 0; k < luah[i].length; k++)
        {
            if (luah[i][k][0] != 0)
            {
                luah[i][j][luah[i][k][0]] = 0;
            }
            if (isCoupled && isCoupled(luah, i, k, secCouples) && k != j)
            {
                if (couples[0] == secCouples[0] && couples[1] == secCouples[1] || couples[0] == secCouples[1] && couples[1] == secCouples[0])
                {
                    for (int n = 0; n < luah[i].length; n++)
                    {
                        if (n != j && n != k)
                        {
                            luah[i][n][couples[0]] = 0;
                            luah[i][n][couples[1]] = 0;
                        }
                    }
                }
            }
        }
        for (int k = 0; k < luah[i].length; k++)
        {
            if (luah[k][j][0] != 0)
            {
                luah[i][j][luah[k][j][0]] = 0;
            }
            if (isCoupled && isCoupled(luah, k, j, secCouples) && k != i)
            {
                if (couples[0] == secCouples[0] && couples[1] == secCouples[1] || couples[0] == secCouples[1] && couples[1] == secCouples[0])
                {
                    for (int n = 0; n < luah[i].length; n++)
                    {
                        if (n != i && n != k)
                        {
                            luah[n][j][couples[0]] = 0;
                            luah[n][j][couples[1]] = 0;
                        }
                    }
                }
            }
        }
        for (int k = i - i % 3; k < (i - i % 3) + 3; k++)
        {
            for (int m = j - j % 3; m < (j - j % 3) + 3; m++)
            {
                if (luah[k][m][0] != 0)
                {
                    luah[i][j][luah[k][m][0]] = 0;
                }
                if (isCoupled && isCoupled(luah, k, m, secCouples) && luah[k][m][0] == 0 && luah[i][j][0] == 0 && (k != i || m != j))
                {
                    if (couples[0] == secCouples[0] && couples[1] == secCouples[1] || couples[0] == secCouples[1] && couples[1] == secCouples[0])
                    {
                        for (int n = i - i % 3; n < (i - i % 3) + 3; n++)
                        {
                            for (int l = j - j % 3; l < (j - j % 3) + 3; l++)
                            {
                                if ((n != i || l != j) && (n != k || l != m))
                                {
                                    luah[n][l][couples[0]] = 0;
                                    luah[n][l][couples[1]] = 0;
                                }
                            }
                        }
                    }
                }

            }
        }
    }

    /**
     * @return the Mat
     */
    public int[][] getMat()
    {
        return Mat;
    }

    /**
     * @param Mat the Mat to set
     */
    public void setMat(int[][] Mat)
    {
        this.Mat = Mat;
        input(luah,Mat);
    }

    /**
     * @return the luah
     */
    public int getLuahNum(int i, int j)
    {
        return luah[i][j][0];
    }

    /**
     * @return the Success
     */
    public boolean isSuccess()
    {
        return Success;
    }

    /**
     * @param Success the Success to set
     */
    public void setSuccess(boolean Success)
    {
        this.Success = Success;
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GameCode;

/**
 *
 * @author Lior
 */
public class SudokuBoard
{
    private int Level;
    private int [][] Mat;

    public SudokuBoard(int Level, int[][] Mat)
    {
        this.Level = Level;
        this.Mat = Mat;
    }
    /**
     * @return the Level
     */
    public int getLevel()
    {
        return Level;
    }

    /**
     * @param Level the Level to set
     */
    public void setLevel(int Level)
    {
        this.Level = Level;
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
    }
    
    
}

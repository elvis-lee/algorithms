import java.lang.Math.*;
import java.util.Iterator;
import edu.princeton.cs.algs4.*;
    
public class Board {
    public Board(int[][] blocks)           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    {
        dimensionSize = blocks.length;
        boardBlocks = new int[dimensionSize][dimensionSize];
        //goalBlocks = new int[dimensionSize][dimensionSize];
        for (int i = 0; i < dimensionSize; i++)
            for (int j = 0; j < dimensionSize; j++)
                boardBlocks[i][j] = blocks[i][j];
        //initialize goal board
//        for (int i = 0; i < dimensionSize; i++)
//            for (int j = 0; j < dimensionSize; j++)
//                goalBlocks[i][j] = i * dimensionSize + j + 1;
//        goalBlocks[dimensionSize - 1][dimensionSize - 1] = 0;
        
        
    }
    private int[][] boardBlocks;
//    private int[][] goalBlocks;
    private int dimensionSize;
    public int dimension()                 // board dimension n
    { return dimensionSize; }
    private int hammingNum;
    public int hamming()                   // number of blocks out of place
    {
        hammingNum = 0;
        for (int i = 0; i < dimensionSize; i++)
            for (int j = 0; j < dimensionSize; j++)
                if (boardBlocks[i][j] != 0 && boardBlocks[i][j] != 1 + i * dimensionSize + j)
                    hammingNum++;
        return hammingNum;
    }
    private int mhtDistance(int i, int j,int value)
    {
        if (value == 0) return 0;//blank square has zero manhattan distance
        int ii,jj;
        ii = (value - 1) / dimensionSize;
        jj = (value - 1) % dimensionSize;
        return Math.abs(ii - i) + Math.abs(jj - j);
    }
    private int manhattanNum;
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        manhattanNum = 0;
        for (int i = 0; i < dimensionSize; i++)
            for (int j = 0; j < dimensionSize; j++)
                manhattanNum += mhtDistance(i,j,boardBlocks[i][j]);
        return manhattanNum;
    }
   
    private boolean isGoalReached = false;
    public boolean isGoal()                // is this board the goal board?
    {
        isGoalReached = true;
        for (int i = 0; i < dimensionSize; i++)
            for (int j = 0; j < dimensionSize; j++)
                if (boardBlocks[i][j] != (1 + i * dimensionSize + j) % (dimensionSize * dimensionSize))
                    isGoalReached = false;
        return isGoalReached;
    }
        
    public Board twin()                    // a board that is obtained by exchanging any pair of blocks
    {
        int i = 0;
        int j = 0;
        while (boardBlocks[i / dimensionSize][i % dimensionSize] == 0) i++;
        while (boardBlocks[j / dimensionSize][j % dimensionSize] == 0 || j == i) j++;
        return this.exch(i / dimensionSize, i % dimensionSize, j / dimensionSize, j % dimensionSize);
    }
    private Board exch(int m, int n, int mm, int nn)
    {
        int[][] newBlocks = new int[dimensionSize][dimensionSize];
        for (int i = 0; i < dimensionSize; i++)
            for (int j = 0; j < dimensionSize; j++)
                newBlocks[i][j] = boardBlocks[i][j];
        newBlocks[m][n] = boardBlocks[mm][nn];
        newBlocks[mm][nn] = boardBlocks[m][n];
        return new Board(newBlocks);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        boolean isEqual = true;
        if (that.dimension() != dimensionSize) return false;
        for (int i = 0; i < dimensionSize; i++)
            for (int j = 0; j < dimensionSize; j++)
                if (this.boardBlocks[i][j] != that.boardBlocks[i][j])
                    isEqual = false;
        return isEqual;
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Stack<Board> boardStack = new Stack<Board>();
        int i = 0;
        while (boardBlocks[i / dimensionSize][i % dimensionSize] != 0) i++;
        int zeroX = i / dimensionSize;
        int zeroY = i % dimensionSize;
        if (zeroX > 0) boardStack.push(this.exch(zeroX,zeroY,zeroX - 1,zeroY));
        if (zeroX < dimensionSize - 1) boardStack.push(this.exch(zeroX,zeroY,zeroX + 1,zeroY));
        if (zeroY > 0) boardStack.push(this.exch(zeroX,zeroY,zeroX,zeroY - 1));
        if (zeroY < dimensionSize - 1) boardStack.push(this.exch(zeroX,zeroY,zeroX,zeroY + 1));
        return boardStack;  
    }
    public String toString()               // string representation of this board (in the output format specified below)
    {
        StringBuilder s = new StringBuilder();
        s.append(dimensionSize + "\n");
        for (int i = 0; i < dimensionSize; i++)
        {
            for (int j = 0; j < dimensionSize; j++)
                s.append(String.format(" " + boardBlocks[i][j]) + " ");
            s.append("\n");
        }
            return s.toString();
    }


    public static void main(String[] args) // unit tests (not graded)
    {
////        int[][] t = new int[][]{{1,2,3},{4,5,6},{7,8,0}};
////        Board board = new Board(t);
////        System.out.println(board.dimension());
////        System.out.println(board.isGoal());
////        System.out.println(board.hamming());
////        System.out.println(board.manhattan());
////        Iterable neighbors = board.neighbors();
////        Iterator iterator = neighbors.iterator();
////        Board board2 = (Board)iterator.next();
////        String s = board2.toString();
////        System.out.println(s);
    }
}
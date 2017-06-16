import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.*;

public class Solver {
    private class SearchNode
    {
        private Board currentBoard; // why can be access outside? in calss HammingComparator
        private int moveNum;
        private SearchNode previousNode;
        public SearchNode(Board currentBoard,int moveNum,SearchNode previousNode)
        {
            this.currentBoard = currentBoard;
            this.moveNum = moveNum;
            this.previousNode = previousNode;
        }
    }
    private class HammingComparator implements Comparator<SearchNode>
    {
        public int compare(SearchNode n1, SearchNode n2)
        {
            if (n1.moveNum + n1.currentBoard.hamming() > n2.moveNum + n2.currentBoard.hamming()) return 1;
            else if (n1.moveNum + n1.currentBoard.hamming() < n2.moveNum + n2.currentBoard.hamming()) return -1;
            else return 0;
        }
    }
    private class ManhattanComparator implements Comparator<SearchNode>
    {
        public int compare(SearchNode n1, SearchNode n2)
        {
            if (n1.moveNum + n1.currentBoard.manhattan() > n2.moveNum + n2.currentBoard.manhattan()) return 1;
            else if (n1.moveNum + n1.currentBoard.manhattan() < n2.moveNum + n2.currentBoard.manhattan()) return -1;
            else return 0;
        }
    }
//    private MinPQ<SearchNode> solverPQ = new MinPQ<SearchNode>(new ManhattanComparator());
//    private MinPQ<SearchNode> solverPQTwin = new MinPQ<SearchNode>(new ManhattanComparator());
    private SearchNode finalNode;
    public Solver(Board initial)           // find a solution to the initial board (using the A* algorithm)
    {
        if (initial == null)
            throw new java.lang.NullPointerException();
        MinPQ<SearchNode> solverPQ = new MinPQ<SearchNode>(new ManhattanComparator());
        MinPQ<SearchNode> solverPQTwin = new MinPQ<SearchNode>(new ManhattanComparator());
        SearchNode initialNode = new SearchNode(initial, 0, null);
        SearchNode initialNodeTwin = new SearchNode(initial.twin(), 0, null);
        solverPQ.insert(initialNode);
        solverPQTwin.insert(initialNodeTwin);
        SearchNode dequeueNode = solverPQ.delMin();
        SearchNode dequeueNodeTwin = solverPQTwin.delMin();
        while (dequeueNode.currentBoard.isGoal() == false && dequeueNodeTwin.currentBoard.isGoal() == false)
        {
            Iterable<Board> neighbors = dequeueNode.currentBoard.neighbors();
            Iterator<Board> iterator = neighbors.iterator();
            while (iterator.hasNext() == true)
            {
                Board next = iterator.next();
                if (dequeueNode.previousNode == null || !next.equals(dequeueNode.previousNode.currentBoard))
                    solverPQ.insert(new SearchNode(next,dequeueNode.moveNum + 1, dequeueNode));
            }
            dequeueNode = solverPQ.delMin();
            
            Iterable<Board> neighborsTwin = dequeueNodeTwin.currentBoard.neighbors();
            Iterator<Board> iteratorTwin = neighborsTwin.iterator();
            while (iteratorTwin.hasNext() == true)
            {
                Board nextTwin = iteratorTwin.next();
                if (dequeueNodeTwin.previousNode == null || !nextTwin.equals(dequeueNodeTwin.previousNode.currentBoard))
                    solverPQTwin.insert(new SearchNode(nextTwin,dequeueNodeTwin.moveNum + 1, dequeueNodeTwin));
            }
            dequeueNodeTwin = solverPQTwin.delMin();
        }
        finalNode = dequeueNode;
    }

    public boolean isSolvable()            // is the initial board solvable?
    {
        return finalNode.currentBoard.isGoal();
    }
    public int moves()                     // min number of moves to solve initial board; -1 if unsolvable
    {
        if (finalNode.currentBoard.isGoal() == false) return -1;
        else return finalNode.moveNum;
    }
    public Iterable<Board> solution()      // sequence of boards in a shortest solution; null if unsolvable
    {
        if (finalNode.currentBoard.isGoal() == false) return null;
        Stack<Board> solutionStack = new Stack<Board>();
        SearchNode currentNode = finalNode;
        Board board = finalNode.currentBoard;
        do
        {
            solutionStack.push(currentNode.currentBoard);
            currentNode = currentNode.previousNode;
        }while(currentNode != null);
        return solutionStack;
    }

    public static void main(String[] args) 
    {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else 
        {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}
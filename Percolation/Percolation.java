import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut;

public class Percolation
{
   private int virtualTopIndex;
   private int virtualBottomIndex;
   private int gridLength;
   private int arraySize;
   private int numOpen;
   private WeightedQuickUnionUF percolation;
   private WeightedQuickUnionUF percolation_full;
   private boolean[] isOpen;
   private void checkBounds(int i, int j) 
   {
       if (i < 1 || i > gridLength) 
       {
           throw new IndexOutOfBoundsException("row index i out of bounds");
       }
       if (j < 1 || j > gridLength) 
       {
           throw new IndexOutOfBoundsException("row index i out of bounds");  
       }
   }
   
   private int siteIndex(int i, int j)
   {
       checkBounds(i,j);
       return (i-1)*gridLength+j;
   }
   
   // create n-by-n grid, with all sites blocked
   public Percolation(int n)  
   {
       gridLength = n;
       arraySize = n*n+2;
       virtualTopIndex = 0;
       virtualBottomIndex = arraySize-1;
       isOpen = new boolean[arraySize];
       numOpen = 0;
       
       int i;
       
       if (n <= 0)
       {
           throw new IllegalArgumentException();
       }
       percolation = new WeightedQuickUnionUF(arraySize);
       percolation_full = new WeightedQuickUnionUF(arraySize);
    }
   
   // open site (row, col) if it is not open already
   public void open(int row, int col)    
   {
       if (!isOpen[siteIndex(row, col)])
       {
           isOpen[siteIndex(row, col)] = true;
           numOpen++;
       }
       if (col > 1 && isOpen[siteIndex(row,col-1)])
       {
           percolation.union(siteIndex(row,col),siteIndex(row,col-1));
           percolation_full.union(siteIndex(row,col),siteIndex(row,col-1));
       }
       if (col < gridLength && isOpen[siteIndex(row,col+1)])
       {
           percolation.union(siteIndex(row,col),siteIndex(row,col+1));
           percolation_full.union(siteIndex(row,col),siteIndex(row,col+1));
       }
       if (row > 1 && isOpen[siteIndex(row-1,col)])
       {
           percolation.union(siteIndex(row,col),siteIndex(row-1,col));
           percolation_full.union(siteIndex(row,col),siteIndex(row-1,col));
       }
       if (row < gridLength && isOpen[siteIndex(row+1,col)])
       {
           percolation.union(siteIndex(row,col),siteIndex(row+1,col));
           percolation_full.union(siteIndex(row,col),siteIndex(row+1,col));
       }
       if (row == 1)
       {
           percolation.union(siteIndex(row,col),virtualTopIndex);
           percolation_full.union(siteIndex(row,col),virtualTopIndex);
       }
       if (row == gridLength)
       {
           percolation.union(siteIndex(row,col),virtualBottomIndex);
       }
   }
   
   // is site (row, col) open?
   public boolean isOpen(int row, int col)  
   {
       return isOpen[siteIndex(row,col)];
   }
       
   // is site (row, col) full?
   public boolean isFull(int row, int col)  
   {
       return percolation_full.connected(virtualTopIndex, siteIndex(row, col));
   }
   
   // number of open sites  
   public int numberOfOpenSites()  
   {
       return numOpen;
   }

   // does the system percolate?
   public boolean percolates()     
   {
       return percolation.connected(virtualTopIndex, virtualBottomIndex);
   }
       
   public static void main(String[] args)   
   {
       Percolation percolation = new Percolation(2);
       System.out.println("percolate: "+percolation.percolates());
       percolation.open(2,1);
       System.out.println("percolate: "+percolation.isOpen(1,1));
       System.out.println("percolate: "+percolation.percolates());
       System.out.println("open 1 1 ?"+percolation.isOpen(1,1));
       percolation.open(1,1);
       System.out.println("open 1 1 ?"+percolation.isOpen(1,1));
       System.out.println("percolate: "+percolation.percolates());
       
   }
}
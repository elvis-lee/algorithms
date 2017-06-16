import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.lang.Math;
import edu.princeton.cs.algs4.Stopwatch;
    
public class PercolationStats 
{
   private double[] means; 
   private int arraySize;
   private int gridLength;
   private int trialNum;
   private Percolation percolation;
       
   // perform trials independent experiments on an n-by-n grid
   public PercolationStats(int n, int trials)    
   {
       int i;
       int ri,rj;
       int ran;
       
       means = new double[trials];
       //arraySize = n*n+1;
       gridLength = n;
       trialNum = trials;
       
       if (n <= 0 || trials <= 0)
       {
           throw new IllegalArgumentException();
       }
       
       for (i = 0; i < trials; i++)
       {
           Percolation percolation = new Percolation(n);
           while( ! percolation.percolates())
           {
               ran = StdRandom.uniform(n,n*n+n);
               ri = ran / gridLength;
               rj = ran % gridLength + 1;
//               ri = StdRandom.uniform(1,n+1);//System.out.println("ri = "+ri);
//               rj = StdRandom.uniform(1,n+1);//System.out.println("rj = "+rj);
               percolation.open(ri,rj);
           }
           means[i] = (double)percolation.numberOfOpenSites() / (n*n);
           //System.out.println("mean "+i+" = "+means[i]);
       }

   }
   
   // sample mean of percolation threshold
   public double mean()   
   {
       return StdStats.mean(means);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev()   
   {
       return StdStats.stddev(means);
   }
   
   // low  endpoint of 95% confidence interval
   public double confidenceLo()  
   {
       return (StdStats.mean(means) - 1.96 * StdStats.stddev(means) / Math.sqrt(trialNum) ); 
   }
       
   // high endpoint of 95% confidence interval  
   public double confidenceHi()   
   {
       return (StdStats.mean(means) + 1.96 * StdStats.stddev(means) / Math.sqrt(trialNum) ); 
   }
   
   // test client (described below)
   public static void main(String[] args)
   {
       
       PercolationStats percolationstats = new PercolationStats(Integer.parseInt(args[0]),Integer.parseInt(args[1]));
       System.out.println("mean: "+percolationstats.mean());
       System.out.println("stddev: "+percolationstats.stddev());
       System.out.println("Lo: "+percolationstats.confidenceLo());
       System.out.println("Hi: "+percolationstats.confidenceHi());
   }
}
import java.util.*;

public class FastCollinearPoints 
{
   private int nSegments;
   private ArrayList<LineSegment> lineSegment = new ArrayList<LineSegment>(0);
   
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       //check null argument 
       if (points == null) throw new NullPointerException();
       int nPoint = points.length;
       for (int i = 0; i < nPoint; i++)
           if (points[i] == null) throw new NullPointerException();
       
       //check repeated points
       for (int i = 0; i < nPoint; i++)
           for (int j = i + 1; j < nPoint; j++)
           if (points[i].compareTo(points[j]) == 0)
           throw new IllegalArgumentException();
       
       //default order point array
       Point[] newPoints = new Point[nPoint];
       for (int i = 0; i < nPoint; i++)
           newPoints[i] =  points[i];
       Arrays.sort(newPoints);
       
       //new point array for reordering
       Point[] reorderNewPoints = new Point[nPoint];
       for (int i = 0; i < nPoint; i++)
           reorderNewPoints[i] =  points[i];
       
       //
       for (int i = 0; i < nPoint; i++)
       { 
           Arrays.sort(reorderNewPoints);
           Arrays.sort(reorderNewPoints, newPoints[i].slopeOrder());
           int j = 1;

           while (j < nPoint - 2)
           {
               int rindex = j + 1;
               Point rp = reorderNewPoints[rindex];
               boolean isRepeat = false;
               isRepeat = repeatChecker(newPoints,i,reorderNewPoints[j]);
               while ((newPoints[i].slopeTo(rp) == newPoints[i].slopeTo(reorderNewPoints[j])) )
               {
                   if (!isRepeat && repeatChecker(newPoints,i,rp)) 
                   {
                       isRepeat = true;
                       //break;
                   }
                   if ( rindex + 1 == nPoint) 
                   {
                       rindex++;
                       break;
                   }
                   rp = reorderNewPoints[++rindex];
                   
               }
               if (rindex - j > 2 && !isRepeat)
               {                
                   lineSegment.add(new LineSegment(newPoints[i], reorderNewPoints[rindex - 1]));
                   nSegments++;
               }
               j = rindex;
           }
       }   
   }
   public           int numberOfSegments() {return nSegments;}      
   public LineSegment[] segments() {return lineSegment.toArray(new LineSegment[lineSegment.size()]);}   
   
   private static boolean repeatChecker(Point[] parray, int k, Point p)
   {
       int lo = 0;
       int hi = k;
       
       while (lo <= hi)
       {
           int mid = lo + (hi - lo) / 2;
           int compareResult = p.compareTo(parray[mid]);
           if (compareResult > 0)
               lo = mid + 1;
           else if (compareResult < 0)
               hi = mid - 1;
           else return true;
       }
       return false;
   }
   
   
   public static void main(String[] args)
   {
       Point[] p = new Point[8];
       p[0] = new Point(10000,0);
       p[1] = new Point(0,10000);
       p[2] = new Point(3000,7000);
       p[3] = new Point(7000,3000);
       p[4] = new Point(20000,21000);
       p[5] = new Point(3000,4000);
       p[6] = new Point(14000,15000);
       p[7] = new Point(6000,7000);
 
       FastCollinearPoints fcp = new FastCollinearPoints(p);
       System.out.println(fcp.numberOfSegments());
       LineSegment[] l = fcp.segments();  
   }
   
}
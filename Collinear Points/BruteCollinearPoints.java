import java.util.*;
public class BruteCollinearPoints 
{
   private int nSegments;
   private LineSegment[] lineSegment = new LineSegment[0];
   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       if (points == null) throw new NullPointerException();
       int nPoint = points.length;
       for (int i = 0; i < nPoint; i++)
           if (points[i] == null) throw new NullPointerException();
       LineSegment[] oldLineSegment;
       double sj,sp,sq;

       for (int i = 0; i < nPoint; i++)
           for (int j = i + 1; j < nPoint; j++)
           if (points[i].compareTo(points[j]) == 0)
           throw new IllegalArgumentException();
       
       Point[] newPoints = new Point[nPoint];
       for (int i = 0; i < nPoint; i++)
           newPoints[i] = points[i];
       Arrays.sort(newPoints);
           
       for (int i = 0; i < nPoint; i++)
           for (int j = i + 1; j < nPoint; j++)
           {
               sj = newPoints[i].slopeTo(newPoints[j]);
               for (int p = j + 1; p < nPoint; p++)
               {
                   sp = newPoints[i].slopeTo(newPoints[p]);
                   for (int q = p + 1; q < nPoint; q++)
                   {
                       sq = newPoints[i].slopeTo(newPoints[q]);
                       if (sj == sp && sp == sq)
                       {
                           
                           oldLineSegment = lineSegment;
                           lineSegment = new LineSegment[nSegments + 1];
                           for (int k = 0; k < nSegments; k++)
                               lineSegment[k] = oldLineSegment[k];
                           oldLineSegment = null;
                           lineSegment[nSegments] = new LineSegment(newPoints[i],newPoints[q]);
                           
                           nSegments++;   
                       }
                   }
               }
           }
   }
   public           int numberOfSegments()        // the number of line segments
   {
       return nSegments;
   }
   public LineSegment[] segments()                // the line segments
   {
       return Arrays.copyOf(lineSegment,lineSegment.length);
   }
   
   public static void main(String[] args)
   {
       Point[] p = new Point[4];
       p[0] = new Point(2,2);
       p[1] = new Point(1,1);
       p[2] = new Point(0,0);
       p[3] = new Point(3,3);
       BruteCollinearPoints bcp = new BruteCollinearPoints(p);
       System.out.println(bcp.numberOfSegments());
       LineSegment[] l = bcp.segments();
       l[0] = new LineSegment(new Point(5,5),new Point(3,3));
       LineSegment[] l2 = bcp.segments();
   }
}
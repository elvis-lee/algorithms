import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;
import java.util.Iterator;

public class PointSET 
{
   private TreeSet<Point2D> rbt;//use red-black BST
   
   public         PointSET()                               // construct an empty set of points 
   {
       rbt = new TreeSet<Point2D>();
   }
   
   public           boolean isEmpty()                      // is the set empty? 
   {
       return (rbt.size() == 0);
   }
   
   public               int size()                         // number of points in the set 
   {
       return rbt.size();
   }
   
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       if (p == null) 
           throw new java.lang.IllegalArgumentException();
       if (!rbt.contains(p))
           rbt.add(p);
   }
   
   public           boolean contains(Point2D p)            // does the set contain point p? 
   {
       if (p == null) 
           throw new java.lang.IllegalArgumentException();
       return rbt.contains(p);
   }
   
   public              void draw()                         // draw all points to standard draw 
   {
       Iterator<Point2D> iterator = this.range(new RectHV(0,0,1,1)).iterator(); //all points have x- and y-coordinates between 0 and 1
       while (iterator.hasNext())
           (iterator.next()).draw();
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
       if (rect == null) 
           throw new java.lang.IllegalArgumentException();
       
       Stack<Point2D> ans = new Stack<Point2D>();
       if (rbt.size() == 0) 
           return ans;
       
       Point2D floorPoint;
       Point2D ceilingPoint;
       
       double ymin = rect.ymin();
       double ymax = rect.ymax();
       if (rbt.floor(new Point2D(rect.xmin(),rect.ymin())) != null)
           floorPoint = rbt.floor(new Point2D(rect.xmin(),rect.ymin()));
       else 
           floorPoint = rbt.first();
       if (rbt.ceiling(new Point2D(rect.xmax(),rect.ymax())) != null)
           ceilingPoint = rbt.ceiling(new Point2D(rect.xmax(),rect.ymax()));
       else 
           ceilingPoint = rbt.last();
       Iterable<Point2D> subSet = rbt.subSet(floorPoint,true,ceilingPoint,true);
       Iterator<Point2D> iterator = subSet.iterator();
       while(iterator.hasNext())
       {
           Point2D temp = iterator.next();
           if (rect.contains(temp))
               ans.push(temp);
       }
       return ans;
   }
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       if (p == null) 
           throw new java.lang.IllegalArgumentException();
       Point2D ansP;
       if (rbt.size() == 0) return null;
       Iterator<Point2D> iterator = rbt.iterator();
       ansP = iterator.next();
       while(iterator.hasNext())
       {
           Point2D temp = iterator.next();
           if (p.distanceSquaredTo(temp) < p.distanceSquaredTo(ansP))
               ansP = temp;
       }
       return ansP;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
       PointSET pset = new PointSET();
       pset.draw();
       pset.insert(new Point2D(0.1,0.1));
       pset.insert(new Point2D(0.2,0.2));
       pset.insert(new Point2D(0.3,0.3));
       pset.insert(new Point2D(0.4,0.4));
       pset.insert(new Point2D(0.5,0.5));
//       pset.insert(new Point2D(6,6));
//       pset.insert(new Point2D(7,7));
       pset.draw();
       Iterable<Point2D> s = pset.range(new RectHV(0.2,0.2,0.6,0.6));
       Iterator<Point2D> iterator = s.iterator();
       while(iterator.hasNext())
       {
           Point2D temp = iterator.next();
           System.out.println(temp.x() + " " + temp.y());
       }
       Point2D n = pset.nearest(new Point2D(0.3,0.35));
       System.out.println(n.x() + " " + n.y());
   }
}
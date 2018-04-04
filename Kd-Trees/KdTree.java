import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import java.util.TreeSet;
import java.util.Iterator;

public class KdTree 
{
   private static class Node 
   {
       private Point2D p;      // the point
       private RectHV rect;    // the axis-aligned rectangle corresponding to this node
       private Node lb;        // the left/bottom subtree
       private Node rt;        // the right/top subtree
       private boolean isX; 
       
       Node(Point2D p, RectHV rect, boolean isX)
       {
           this.p = p;
           this.isX = isX;
           this.rect = rect;
           this.lb = null;
           this.rt = null;
       }
   }
   
   private int size = 0;
   private Node root;
   
   public         KdTree()                               // construct an empty set of points 
   {
   }

   public           boolean isEmpty()                      // is the set empty? 
   {
       return (size == 0);
   }
   
   public               int size()                         // number of points in the set 
   {
       return size;
   }
   
   public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
   {
       if (p == null) throw new java.lang.IllegalArgumentException();
       if (!this.contains(p)) 
       {
           if (size == 0)
           {
               size++;
               root = new Node(p,new RectHV(0,0,1,1),true);
           }
           else 
           {
               size++;
               root = insert(root, null, p);
           }
       }
   }
   
   private Node insert(Node x, Node lastX, Point2D p)
   {
       if (x == null) 
       {
           boolean isX = lastX.isX;
           RectHV newRect;
           boolean cmp = ((isX ? p.x() : p.y()) >= (isX ? lastX.p.x() : lastX.p.y()));
           if (isX)
           {
               if (cmp) 
                   newRect = new RectHV(lastX.p.x(),lastX.rect.ymin(),lastX.rect.xmax(),lastX.rect.ymax());
               else 
                   newRect = new RectHV(lastX.rect.xmin(),lastX.rect.ymin(),lastX.p.x(),lastX.rect.ymax());
           }
           else 
           {
               if (cmp) 
                   newRect = new RectHV(lastX.rect.xmin(),lastX.p.y(),lastX.rect.xmax(),lastX.rect.ymax());
               else 
                   newRect = new RectHV(lastX.rect.xmin(),lastX.rect.ymin(),lastX.rect.xmax(),lastX.p.y());
           }
                   
           return new Node(p, newRect, !isX);
       }
       boolean cmp = ((x.isX ? p.x() : p.y()) >= (x.isX ? x.p.x() : x.p.y()));
       if (cmp)  x.rt = insert(x.rt, x, p);
       else x.lb = insert(x.lb, x, p);
       return x;
   }
   
   public           boolean contains(Point2D p)            // does the set contain point p? 
   {
       if (p == null) throw new java.lang.IllegalArgumentException();
       return contains(root, p);
   }
   
   private boolean contains(Node x, Point2D p)
   {
       if (x == null) return false;
       boolean isX = x.isX;
       double cmp = ((isX ? p.x() : p.y()) - (isX ? x.p.x() : x.p.y()));
       if (cmp > 0) return contains(x.rt, p);
       else if (cmp < 0) return contains(x.lb, p);
       else 
       {
           if ((isX ? p.y() : p.x()) - (isX ? x.p.y() : x.p.x()) == 0)
               return true;
           else return contains(x.rt, p);
       }
   }
   
   public              void draw()                         // draw all points to standard draw 
   {
       
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
   {
       if (rect == null) 
           throw new java.lang.IllegalArgumentException();
       
       Stack<Point2D> ans = new Stack<Point2D>();
       if (size == 0) 
           return ans;
       
       range(root, rect, ans);
       return ans;
   }
   
   private void range(Node x, RectHV rect, Stack<Point2D> ans)    
   {
       if (x == null) return;
       if (rect.contains(x.p)) ans.push(x.p);
       double lineValue = x.isX ? x.p.x() : x.p.y();
       double rectMin = x.isX ? rect.xmin() : rect.ymin();
       double rectMax = x.isX ? rect.xmax() : rect.ymax();
       if (lineValue > rectMin && lineValue <= rectMax)
       {
           range(x.lb,rect,ans);
           range(x.rt,rect,ans);
       }
       else 
       {
           if (lineValue <= rectMin)
               range(x.rt,rect,ans);
           if (lineValue > rectMax)
               range(x.lb,rect,ans);
       }
   }
   public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
   {
       if (p == null) throw new java.lang.IllegalArgumentException();
       if (size == 0) return null;
       Point2D ans = root.p;
       Stack<Point2D> ansStack = new Stack<Point2D>();
       ansStack.push(root.p);
       nearest(root, p, ansStack);
       ans = ansStack.pop();
       return ans;
   }
   
   private void nearest(Node x, Point2D p, Stack<Point2D> ansStack)
   {
       Node firstX;
       Node secondX;
       if (x == null) return;
       if (p.distanceSquaredTo(x.p) < p.distanceSquaredTo(ansStack.peek())) 
       {
           ansStack.pop();
           ansStack.push(x.p);
       }
       
       if (x.lb == null && x.rt == null) return;
       if (x.lb == null)
       {
           if (x.rt.rect.distanceSquaredTo(p) < ansStack.peek().distanceSquaredTo(p))
           nearest(x.rt, p, ansStack);
           return;
       }
       if (x.rt == null)
       {
           if (x.lb.rect.distanceSquaredTo(p) < ansStack.peek().distanceSquaredTo(p))
           nearest(x.lb, p, ansStack);
           return;
       }
       
       if (x.lb.rect.contains(p)) 
       {
           firstX = x.lb;
           secondX = x.rt;
       }
       else 
       {
           firstX = x.rt;
           secondX = x.lb;
       }
       
       if (firstX.rect.distanceSquaredTo(p) < ansStack.peek().distanceSquaredTo(p))
           nearest(firstX, p, ansStack);
       if (secondX.rect.distanceSquaredTo(p) < ansStack.peek().distanceSquaredTo(p))
           nearest(secondX, p, ansStack);
   }
       
   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
       KdTree tree = new KdTree();
       tree.insert(new Point2D(0.5, 0.5));
       tree.insert(new Point2D(0.5, 0.6));
       System.out.println(tree.size());
       System.out.println(tree.contains(new Point2D(0.5,0.6)));
       tree.insert(new Point2D(0.7, 0.5));
       tree.insert(new Point2D(0.2, 0.8));
       tree.insert(new Point2D(0.3, 0.8));
       tree.insert(new Point2D(0.6, 0.4));
       System.out.println(tree.size());
       Stack<Point2D> ans = (Stack<Point2D>)tree.range(new RectHV(0,0,0.5,1));
       for (Point2D p: ans)
           System.out.println(p.x() + " " + p.y());
       Point2D pans = tree.nearest(new Point2D(0.61, 0.3));
       System.out.println(pans.x() + " " + pans.y());
       
   }
}
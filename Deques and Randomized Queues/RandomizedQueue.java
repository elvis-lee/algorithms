import java.util.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> 
{
   private int size = 0;
   private Item[] rqueue;
   
   public RandomizedQueue()                 // construct an empty randomized queue
   {
       rqueue = (Item[]) new Object[1];
   }
   
   public boolean isEmpty()                 // is the queue empty?
   {
       return (size == 0);
   }
   
   public int size()                        // return the number of items on the queue
   {
       return size;
   }
   
   public void enqueue(Item item)           // add the item
   {
       if (item == null)
           throw new NullPointerException();
//       if (size == 0)
//       {
//           size = 1;
//           rqueue = (Item[]) new Object[1];
//           rqueue[0] = item;
//       }
       else
       {
           if (size == rqueue.length) resize(2*size);
           rqueue[size++] = item;
       }  
   } 
   
   private void resize(int capacity)
   {
       Item[] copy = (Item[]) new Object[capacity];
       for (int i = 0; i < size; i++)
           copy[i] = rqueue[i];
       rqueue = copy;
       //System.out.println("After resizing: "+capacity);
   }
   
   public Item dequeue()                    // remove and return a random item
   {
       if (size == 0) throw new NoSuchElementException();
       int r = StdRandom.uniform(0,size);
       Item item = rqueue[r];
       rqueue[r] = rqueue[size-1];
       rqueue[--size] = null;
       /*
        * Notes: 
        * "size > 0" is necessary.
        * Otherwise, "empty, enqueue, dequeue, enqueue" will fail.
        * after dequeue, resize(1/2), make rqueue.length become zero
        */
       if (size > 0 && size <= rqueue.length / 4 ) resize(rqueue.length / 2);
       return item;
   }
   
   public Item sample()                     // return (but do not remove) a random item
   {
       if (size == 0) throw new NoSuchElementException();
       else   
       {
           Item item = rqueue[StdRandom.uniform(0,size)];
           return item;
       }
   }  
   
   public Iterator<Item> iterator()         // return an independent iterator over items in random order
   {
       return new RandomizedQueueIterator();
   }
   
   private class RandomizedQueueIterator implements Iterator<Item>
   {
       int copySize;
       Item[] copyRQueue;
       /*
        * Important: Iterator for array implementation
        * Make a copy of the whole array first!
        */
       public RandomizedQueueIterator() 
       {
           copySize = size;
           copyRQueue = (Item[]) new Object[size];
           for (int i = 0; i < size; i++)
               copyRQueue[i] = rqueue[i];
       }
       public boolean hasNext()
       {
           return (copySize > 0);
       }
       public Item next()
       {
           if (copySize == 0) throw new NoSuchElementException();
           int r = StdRandom.uniform(0,copySize);
           Item item = copyRQueue[r];
           copyRQueue[r] = copyRQueue[copySize-1];
           copyRQueue[--copySize] = null;
           return item;
       }
       public void remove()
       {
           throw new UnsupportedOperationException();
       }
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
       RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
       rq.enqueue(11);
       System.out.println("size: "+rq.size());
       rq.enqueue(22);
       System.out.println("size: "+rq.size());
       rq.enqueue(33);
       System.out.println("size: "+rq.size());
       rq.enqueue(44);
       System.out.println("size: "+rq.size());
       System.out.println(rq.dequeue());
       System.out.println(rq.dequeue());
       System.out.println(rq.dequeue());
       System.out.println(rq.dequeue());
       rq.enqueue(11);
       System.out.println("size: "+rq.size());
       System.out.println(rq.dequeue());
       rq.enqueue(11);
       System.out.println("size: "+rq.size());
        
   }
}
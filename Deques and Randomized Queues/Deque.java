import java.util.*;

public class Deque<Item> implements Iterable<Item> 
{
   private class Node
   {
       Item item;
       Node next;
       Node previous;
       
       public Node(Item item)
       {
           this.item = item;
           next = null;
           previous = null;
       }
   }
   private Node first = null;
   private Node last = null;
   private int size = 0;
    
    
   //public Deque()                           // construct an empty deque
   public boolean isEmpty()                 // is the deque empty?
   {
       return (size == 0);
   }
   
   public int size()                        // return the number of items on the deque
   {
       return size;
   }
   
   public void addFirst(Item item)          // add the item to the front
   {
       if (item == null)
           throw new NullPointerException();
       if (size == 0)
       {
           first = last = new Node(item);
           size = 1;
       }
       else if (size > 0)
       {
           Node oldfirst = first;
           first = new Node(item);
           first.next = oldfirst;
           oldfirst.previous = first;
           size++;
       }
   }
   
   public void addLast(Item item)           // add the item to the end
   {
       if (item == null)
           throw new NullPointerException();
       if (size == 0)
       {
           first = last = new Node(item);
           size = 1;
       }
       else if (size > 0)
       {
           Node oldlast = last;
           last = new Node(item);
           last.previous = oldlast;
           oldlast.next = last;
           size++;
       }
   }
   
   public Item removeFirst()                // remove and return the item from the front
   {
    if (size == 0)
        throw new NoSuchElementException();
    if (size == 1)
    {
        size = 0;
        Item item = first.item;
        first = last = null;
        return item;
    }
    else
    {
        size--;
        Item item = first.item;
        first = first.next;
        first.previous = null;
        return item;
    }
   }
   
   public Item removeLast()                 // remove and return the item from the end
   {
      if (size == 0)
          throw new NoSuchElementException();
      if (size == 1)
      {
          size = 0;
          Item item = last.item;
          first = last = null;
          return item;
      }
      else
      {
          size--;
          Item item = last.item;
          last = last.previous;
          last.next = null;
          return item;
      }
   }
   
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
   
   private class DequeIterator implements Iterator<Item>
   {
       private Node current = first;
       public boolean hasNext()
       {
           return (current != null);
       }
       public Item next()
       {
           if (current == null)
           {
               throw new NoSuchElementException();
           }
           else
           {
               Item item = current.item;
               current = current.next;
               return item;
           }
       }
       public void remove()
       {
           throw new UnsupportedOperationException();
       }
   }
   
   public static void main(String[] args)   // unit testing (optional)
   {
       Deque<String> deque = new Deque<String>();
       deque.addFirst("11");
       System.out.println(deque.size);
       System.out.println(deque.removeFirst());
       System.out.println(deque.size);
       deque.addFirst("44");
       deque.addLast("55");
       for (String s:deque)
           System.out.println(s);
       System.out.println(deque.size);
   }
}
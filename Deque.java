/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int numberOfElement;
    // Pointer to first element of deque
    private Node first;
    // Pointer to last element of deque
    private Node last;

    private class Node {
        Item item;
        // Pointer to next element
        Node next;
        // Pointer to previous element
        Node prev;
    }

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        numberOfElement = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return numberOfElement == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numberOfElement;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        // Create a pointer to first node
        Node currentFirst = first;

        // Create new node and populate data
        first = new Node();
        first.item = item;
        first.prev = null;

        // Set new Node next pointer to the old first Node
        first.next = currentFirst;

        // Set the old first Node previous pointer to new Node
        // When we insert first ever node in to deque, the currentFirst which we set point to first is null
        // For deque which have only 1 node both first and last pointer point to same node
        if (isEmpty()) {
            last = first;
        }
        else {
            currentFirst.prev = first;
        }

        numberOfElement++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node currentLast = last;
        last = new Node();
        last.item = item;
        last.next = null;

        // Set new Node previous pointer to old last Node
        last.prev = currentLast;

        // Set old last Node next pointer to new Node
        if (isEmpty()) {
            first = last;
        }
        else {
            currentLast.next = last;
        }

        numberOfElement++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        // Store data from first element in item
        Item item = first.item;
        // Move pointer from first element to next element
        first = first.next;
        // Set the previous pointer of (now the second element) to null
        first.prev = null;

        numberOfElement--;

        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }

        Item item = last.item;
        // Move pointer from last element to previous element (Second to last)
        last = last.prev;
        // Set pointer of (now second to last element) to null
        last.next = null;

        numberOfElement--;

        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // Implement hasNext() and next() method to iterate over the doubly linked list
    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.item;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        deque.addFirst("First 1");
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        deque.addFirst("First 2");
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        deque.addLast("last 1");
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        deque.addLast("last 2");
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        String removeFirst1 = deque.removeFirst();
        System.out.println(removeFirst1);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        String removeFirst2 = deque.removeFirst();
        System.out.println(removeFirst2);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());

        String removeLast1 = deque.removeLast();
        System.out.println(removeLast1);
        System.out.println(deque.isEmpty());
        System.out.println(deque.size());


        for (String str : deque) {
            System.out.print(str + " ");
        }
    }
}

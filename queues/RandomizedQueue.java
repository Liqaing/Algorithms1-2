/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] randomizedQueue;
    private int numberOfElement;

    // construct an empty randomized queue
    public RandomizedQueue() {
        randomizedQueue = (Item[]) new Object[1];
        numberOfElement = 0;
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return numberOfElement == 0;
    }

    // return the number of items on the deque
    public int size() {
        return numberOfElement;
    }

    // add the item
    // Add item to tail of the queue, if the queue is full then resize it
    public void enqueue(Item item) {
        if (numberOfElement == randomizedQueue.length) {
            resizeArray(randomizedQueue.length * 2);
        }
        // Assign item to index of numberOfElement, then add one to it
        randomizedQueue[numberOfElement++] = item;
    }

    // remove and return a random item
    // Get random array index, then swap the element at that index to last element
    public Item dequeue() {
        int randomIndex = getRandomIndex(numberOfElement - 1);
        Item item = randomizedQueue[randomIndex];
        // Minus one from numberOfElement then access the element in queue
        randomizedQueue[randomIndex] = randomizedQueue[--numberOfElement];
        randomizedQueue[numberOfElement] = null;
        if (numberOfElement > 0 && numberOfElement == randomizedQueue.length / 4) {
            resizeArray(randomizedQueue.length / 2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int randomIndex = getRandomIndex(numberOfElement - 1);
        Item item = randomizedQueue[randomIndex];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        // Use to keep track of current index in queue
        private int currentIndex = 0;

        // Shuffle the queue with Fisher-Yates algorithms
        // Starting from the last element in array
        // Pick a random index between 0 and current last element (that are not yet shuffle)
        // Swap the random index value to the position of current last element, then decrease the range of random index by 1
        public RandomizedQueueIterator() {
            for (int shuffleRange = numberOfElement - 1; shuffleRange > 0; shuffleRange--) {
                int randomIndex = getRandomIndex(shuffleRange);
                Item item = randomizedQueue[shuffleRange];
                randomizedQueue[shuffleRange] = randomizedQueue[randomIndex];
                randomizedQueue[randomIndex] = item;
            }

        }

        public boolean hasNext() {
            return currentIndex != numberOfElement;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = randomizedQueue[currentIndex];
            currentIndex++;
            return item;
        }

    }

    // Create new array with double the size, and copy element to the new array
    private void resizeArray(int newSize) {
        Item[] newArray = (Item[]) new Object[newSize];
        for (int i = 0; i < numberOfElement; i++) {
            newArray[i] = randomizedQueue[i];
        }
        randomizedQueue = newArray;
    }

    // Return random index of array
    private int getRandomIndex(int range) {
        return StdRandom.uniformInt(0, range);
    }

    public static void main(String[] args) {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        rq.enqueue("Item 1");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        rq.enqueue("Item 2");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        rq.enqueue("Item 3");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        rq.enqueue("Item 4");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        System.out.println(rq.dequeue());
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        rq.enqueue("Item 5");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        rq.enqueue("Item 6");
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        System.out.println(rq.dequeue());
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        System.out.println(rq.sample());
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());

        for (String item : rq) {
            System.out.print(item + " ");
        }
        
        System.out.println(" ");

        for (String item : rq) {
            System.out.print(item + " ");
        }

    }
}

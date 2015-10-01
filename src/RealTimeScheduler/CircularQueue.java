///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  RealTimeScheduler
// File:             CircularQueue
// Semester:         CS367 Spring 2014
//
// Author:           Nicholas Sieb nasieb@wisc.edu
// CS Login:         sieb
// Lecturer's Name:  Skrentny
// Lab Section:      Lec 002
///////////////////////////////////////////////////////////////////////////////
package RealTimeScheduler;

/**
 *
 * @author Nicholas
 */
import java.util.List;
import java.util.ArrayList;

public class CircularQueue<E> implements QueueADT<E> {

    private int frontPos;
    private int backPos;
    private int numItems;
    private int maxCap;
    private List<E> queue;

    /**
     * Constructor for CircularQueue with specified cap
     *
     * @param capacity size of queue
     * @throws IllegalArgumentException if not positive integer
     */
    public CircularQueue(int cap) {


        numItems = 0;
        maxCap = cap;
        queue = new ArrayList<E>(cap);
        frontPos = 0;
        backPos = 0;
        if (maxCap <= 0) {
            throw new IllegalArgumentException("Invalid parameters must have positive capacity");
        }
    }

    /**
     * toString method for CircQueue useful for debugging
     *
     * @return returns a string containing data in Circular Queue
     */
    public String toString() {
        String data = "(";

        for (E element : queue) {
            data += element + ", ";
        }
        data += ")";
        return data;

    }

    /**
     * Checks if queue is empty
     *
     * @return if numItems is 0 return true else false
     */
    public boolean isEmpty() {
        return numItems == 0;
    }

    /**
     * Checks if queue is full
     *
     * @return true if numItems = maxCap otherwise false
     */
    public boolean isFull() {
        return numItems == maxCap;
    }

    /**
     * Looks at value next in stack
     *
     * @return data contained in peeked element
     * @throws EmptyQueueException if no elements in queue
     */
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        return queue.get(frontPos);
    }

    /**
     * Takes an item out of the queue
     *
     * @return returns data contained within element
     * @throws EmptyQueueException if queue is empty
     */
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        E info = queue.get(frontPos);
        queue.set(frontPos, null);
        numItems--;

        if (isEmpty()) {
            frontPos = 0;
            backPos = 0;
        } else {
            frontPos++;
        }
        return info;
    }

    /**
     * adds an item to the queue
     *
     * @param item item to be added to queue
     * @throws FullQueueException if queue is already full
     */
    public void enqueue(E item) throws FullQueueException {
        if (isFull()) {
            throw new FullQueueException();
        }
        if (item == null) {
            throw new IllegalArgumentException();
        }

        //circular so requires 2 cases
        //needs wrap around? or not
        if (backPos == maxCap) { //needs wrap around
            backPos = 0;
            queue.set(backPos, item);
        } else if (backPos < queue.size()) {
            queue.set(backPos, item);
            backPos++;
        } else {
            queue.add(item);
            backPos++;
        }
        numItems++; //added item adjust item count
    }

    /**
     * Capacity for the CircularQueue
     *
     * @return returns maxCap
     */
    public int capacity() {
        return maxCap;
    }

    /**
     * Size of current queue
     *
     * @return current size
     */
    public int size() {
        return numItems;
    }
}
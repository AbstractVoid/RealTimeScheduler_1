///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  RealTimeScheduler
// File:             PriorityQueue
// Semester:         CS367 Spring 2014
//
// Author:           Nicholas Sieb nasieb@wisc.edu
// CS Login:         sieb
// Lecturer's Name:  Skrentny
// Lab Section:      Lec 002
///////////////////////////////////////////////////////////////////////////////
package RealTimeScheduler;

import java.util.Comparator;

/**
 *
 * @author Nicholas
 */
public class PriorityQueue<E> implements QueueADT<E> {

    private Comparator<E> compare;
    private E[] queue;
    private int numItems;

    /**
     * Constructor for a PriorityQueue
     *
     * @param comparator Comparator used in heapifying tasks
     * @param maxCapacity maximum size of queue
     */
    public PriorityQueue(Comparator<E> comparator, int maxCapacity) {
        if (comparator == null) {
            throw new IllegalArgumentException();
        }

        if (maxCapacity <= 0) {
            throw new IllegalArgumentException("invalid capacity");
        }

        queue = (E[]) new Object[maxCapacity + 1];
        this.compare = comparator;
        numItems = 0;
    }

    /**
     * Checks if queue is empty
     *
     * @return true if empty false if not
     */
    public boolean isEmpty() {
        return numItems == 0;
    }

    /**
     * Checks if queue is full
     *
     * @return true if full false if not
     */
    public boolean isFull() {
        //0 indexing means we need adjustment length doesn't account
        if (numItems == (queue.length - 1)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Peeks at value in queue
     *
     * @return data in location peeked
     * @throws EmptyQueueException if queue is empty (no values to find)
     */
    public E peek() throws EmptyQueueException {

        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return queue[1];
    }

    /**
     * Dequeues(removes) the next item from queue then reheapifies
     *
     * @return data contained in removed element
     * @throws EmptyQueueException if queue is empty (nothing to remove)
     */
    public E dequeue() throws EmptyQueueException {

        if (isEmpty()) {
            throw new EmptyQueueException();
        }

        E data = queue[1]; //We will look here 
        queue[1] = queue[numItems];

        queue[numItems] = null;
        numItems--;
        downHeap(1);
        return data;
    }

    /**
     * Method for downheaping from a specified index
     *
     * @param pos index position to begin downheaping from
     */
    private void downHeap(int pos) {
        if (2 * pos > numItems) {
            return;
        }

        int left = 2 * pos;
        int right = left + 1;
        int smaller;

        if (right > numItems) {
            smaller = left;
        } else {
            smaller = compare.compare(queue[left], queue[right]) < 0 ? left : right;
        }

        if (compare.compare(queue[smaller], queue[pos]) < 0) {
            swapPos(pos, smaller);
            downHeap(smaller);
        }

    }

    /**
     * This method is used to swap 2 indexes in a heap (useful in downHeap,
     * upHeap)
     *
     * @param pos1 first index
     * @param pos2 second index
     */
    private void swapPos(int pos1, int pos2) {
        if (pos1 < 0 || pos1 > numItems || pos2 < 0 || pos2 > numItems) {
            throw new IndexOutOfBoundsException();
        }

        E temp = queue[pos1];
        queue[pos1] = queue[pos2];
        queue[pos2] = temp;
    }

    /**
     * upHeaps from specified index
     *
     * @param pos position to begin upHeap
     */
    private void upHeap(int pos) {
        if (pos <= 1) { //we are at top
            return;
        }

        int parent = pos / 2;

        if (compare.compare(queue[pos], queue[parent]) < 0) {
            swapPos(pos, parent);

            upHeap(parent);

            downHeap(pos);
        }
    }

    /**
     * adds item to queue
     *
     * @param item item to be added to queue
     * @throws FullQueueException if queue is already at capacity
     */
    public void enqueue(E item) throws FullQueueException {

        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (size() >= capacity()) {
            throw new FullQueueException();
        }
        queue[numItems + 1] = item;
        numItems++;
        upHeap(numItems);

    }

    /**
     * stores capacity of array accounts for 0 indexing
     *
     * @return capacity of array
     */
    public int capacity() {
        return queue.length - 1;
    }

    /**
     * Gives size of array
     *
     * @return returns numItems
     */
    public int size() {
        return numItems;
    }

    /**
     * Debug method to return priorityqueue as a string
     *
     * @return String containing data in queue
     */
    public String toString() {
        String data = "(";
        for (int i = 1; i < numItems + 1; i++) {
            data += queue[i] + ", ";
        }
        data += ")";

        return data;

    }
}

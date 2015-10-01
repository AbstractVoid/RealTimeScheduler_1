///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  RealTimeScheduler
// File:             TaskComparator
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
import java.util.Comparator;

public class TaskComparator implements Comparator<Task> {

    /**
     * Compares two tasks and computes an integer based on comparison
     *
     * @param task1 first task to compare
     * @param task2 second task to compare
     * @return positive, negative or 0 depending on which is larger
     */
    public int compare(Task task1, Task task2) {
        if (task1 == null || task2 == null) {
            throw new IllegalArgumentException();
        }

        int deadline1 = task1.getDeadline();
        int deadline2 = task2.getDeadline();

        if (deadline1 < deadline2) {
            return -1;
        } else if (deadline1 > deadline2) {
            return 1;
        }

        return 0;
    }

    /**
     * Necessary method for Comparator but unused in this implementation
     */
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }
}

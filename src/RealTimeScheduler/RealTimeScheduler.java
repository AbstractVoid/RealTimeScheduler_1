///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            RealTimeScheduler
// Files:            CircularQueue, ComputeResource, ComputeResourceGenerator,
// Files:            EmptyQueueException, PriorityQueue, Process
// Files:            ProcessGenerator, QueueADT, RealTimeScheduler
// Files:            Task, TaskComparator
// Semester:         CS367 Spring 2014
//
// Author:           Nicholas Sieb
// Email:            nasieb@wisc.edu
// CS Login:         sieb
// Lecturer's Name:  Skrentny
// Lab Section:      Lecture 2
///////////////////////////////////////////////////////////////////////////////
package RealTimeScheduler;
//imports to read file, scan file, and lists for data
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Nicholas
 */
public class RealTimeScheduler {

    static private List<Process> processes = new ArrayList<Process>();
    static private int maxComputeTime;
    static private int capacityCircular; //max cap for circ queue
    static private int capacityPriority; //max cap for priority queue
    static private int period; //period and compTime used to construct taks
    static private int compTime;
    static private int deadLine = 1; //deadline to be met
    //List to hold tasks that need to be readded to "todo"
    static private List<Task> incompleteTasks = new ArrayList<Task>();
    static File toRead;
    Scanner in;

    /**
     * Computes greatest common denominator from two numbers
     *
     * @param numOne first number
     * @param numTwo second number
     * @return the greatest common denominator
     */
    private static int gcd(int numOne, int numTwo) {
        if (numOne < 0 || numTwo < 0) {
            throw new IllegalArgumentException();
        }

        if (numTwo == 0) {
            return numOne;
        }
        return gcd(numTwo, numOne % numTwo);
    }

    /**
     * returns LCM of two numbers
     *
     * @param numOne first number
     * @param numTwo second number
     * @return returns LCM
     */
    private static int lcm(int numOne, int numTwo) {

        return (numOne * numTwo) / gcd(numOne, numTwo);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here



            if (args.length != 1) {
                System.out.println("Usage: java RealTimeScheduler <file>");
                return;
            }
            //uses args to create file path
            toRead = new File(args[0]);
            System.out.println(toRead.getName());
            Scanner in = new Scanner(toRead);
            List<String> fileInfo = new ArrayList<String>();
            //while lines exist add them into info list
            while (in.hasNextLine()) {
                fileInfo.add(in.nextLine());
            }
            in.close();

            //size needs to be more than 3 to have any usefulness
            if (fileInfo.size() < 3) {
                System.out.println("you probably have an invalid input file");
                System.out.println(fileInfo);
            }
            //uses info computed to generate information needed to create
            //data structures later follows format outlined on course page
            maxComputeTime = Integer.parseInt(fileInfo.get(0));
            capacityCircular = Integer.parseInt(fileInfo.get(1));
            capacityPriority = Integer.parseInt(fileInfo.get(2));
            //when we get to the processes
            //to get needed info, split into 2 substrings to get period and time
            for (int i = 3; i < fileInfo.size(); i++) {
                String[] processData = fileInfo.get(i).split(" ");

                //you need data in multiples of 2 to construct a task
                if (processData.length != 2) {
                }
                period = Integer.parseInt(processData[0]);

                compTime = Integer.parseInt(processData[1]);
                Process toAdd = new Process(period, compTime);
                processes.add(toAdd);
            }


            //use read info to create the necessary data structures!
            TaskComparator taskComp = new TaskComparator();
            ComputeResourceGenerator resourceGen = new ComputeResourceGenerator(maxComputeTime);
            CircularQueue<ComputeResource> circQueue = new CircularQueue<ComputeResource>(capacityCircular);
            PriorityQueue<Task> priorityQueue = new PriorityQueue<Task>(taskComp, capacityPriority);
            ProcessGenerator processGen = new ProcessGenerator();
            int deadline = 1;

            //add each process to generator and calculate the deadline
            for (Process p : processes) {
                processGen.addProcess(p.getPeriod(), p.getComputeTime());
                deadline = lcm(deadline, p.getPeriod());
            }
            //add all resources to circular queue
            for (int tS = 0; tS <= deadline; tS++) {
                for (ComputeResource resource : resourceGen.getResources()) {
                    try {
                        circQueue.enqueue(resource);
                        //if queue is full then don't add (THEY WASTE AWAY)
                    } catch (FullQueueException e) {
                        break;
                    }
                }
                //add all taks to a priority queue
                for (Task task : processGen.getTasks(tS)) {
                    try {
                        priorityQueue.enqueue(task);
                        //the priority Queue CANNOT become full or we missed something
                    } catch (FullQueueException e) {
                        System.out.println("Deadline missed at timestep " + (tS - 1));
                        return;
                    }
                }

                List<Task> incompleteTasks = new ArrayList<Task>();
                //while we still have resources in circ queue
                //and while we have tasks in priority queue
                while (!circQueue.isEmpty() && !priorityQueue.isEmpty()) {
                    try {
                        //try to apply resources to complete tasks
                        ComputeResource resource = circQueue.dequeue();
                        Task highest = priorityQueue.dequeue();

                        highest.updateProgress(resource.getValue());

                        if (!highest.isComplete()) {
                            incompleteTasks.add(highest);
                        }
                    } catch (EmptyQueueException e) {
                        System.out.println("queue shouldn't be empty at this point");
                        return;
                    }
                }

                //tasks that were incomplete must be readded into the task queue
                for (Task incomplete : incompleteTasks) {
                    try {
                        priorityQueue.enqueue(incomplete);
                    } catch (FullQueueException e) {
                        System.out.println("error can't end up with more tasks than start");
                        return;
                    }
                }

                //before we go ahead, have any deadlines been missed?
                try {
                    Task top = priorityQueue.peek();
                    if (top.missedDeadline(tS)) {
                        System.out.println("Missed a deadline at timestep " + tS);
                        return;
                    }
                    //are we done?
                } catch (EmptyQueueException e) {
                }

            }
            //we are done ;)
            System.out.println("Successful scheduling after " + deadline + " timesteps.");
        } catch (FileNotFoundException ex) {
            System.out.println("error finding input file");
        }
    }
}

///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Main Class File:  RealTimeScheduler
// File:             ProcessGenerator
// Semester:         CS367 Spring 2014
//
// Author:           Nicholas Sieb nasieb@wisc.edu
// CS Login:         sieb
// Lecturer's Name:  Skrentny
// Lab Section:      Lec 002
///////////////////////////////////////////////////////////////////////////////
package RealTimeScheduler;

import java.util.ArrayList;

/**
 *
 * @author Nicholas
 */
public class ProcessGenerator {
    
    private ArrayList<Process> processList;
    /**
     * Constructor for ProcessGenerator creates empty ArrayList to contain
     * Processes
     */
    public ProcessGenerator(){
        processList = new ArrayList<Process>();
    }
    /**
     * Adds a process to the generator constructs process
     * using inputted data
     * @param period period used to create process
     * @param computeTime  time used to create process
     */
    public void addProcess(int period, int computeTime){
        Process toadd = new Process(period,computeTime);
        processList.add(toadd);
    }
    /**
     * gets Tasks by searching for tasks at a multiple of time
     * @param time time of the tasks
     * @return 
     */
    public ArrayList<Task> getTasks(int time){
        ArrayList<Task> taskList = new ArrayList<Task>();
        
        for(Process process:processList){
            if(time % process.getPeriod()==0){ //it is a multiple of time
                Task toadd = new Task(process, time);
                taskList.add(toadd);
            }
        }
        return taskList;
    }
    
}

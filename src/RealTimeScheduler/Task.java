/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RealTimeScheduler;

/**
 *
 * @author Nicholas
 */
public class Task extends Process {

    private int deadline;
    private int progress;
    private int arrival;

    public Task(Process p, int time) {
        super(p.getPeriod(), p.getComputeTime());
        arrival = time;
        deadline = time + this.period;
        progress = 0;
    }

    public int getArrival() {
        return arrival;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getProgress() {
        return progress;
    }

    public void updateProgress(int amt) {
        progress += amt;
    }

    public boolean isComplete() {
        return progress >= compute_time;
    }

    public boolean missedDeadline(int time) {
        return deadline <= time;
    }
}
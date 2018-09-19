/* Implementation of an abstract class representing a criterion. */

import java.util.LinkedList;

public abstract class Criterion {
    protected Process[] processes; //Array of processes sorted by id.
    protected double endTime[]; //Array of end times of processes.

    public Criterion (LinkedList<Process> processList) {
        this.processes = new Process[processList.size()];
        processList.toArray(this.processes);
        endTime = new double[this.processes.length];
    }

    public void setEndTime (int n, double value) { this.endTime[n] = value; }

    // This method prints an outcome of this criterion.
    public abstract void outcome ();
}

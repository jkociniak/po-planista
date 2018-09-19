/* Implementation of class representing the criterion of the average waiting time of a process. */

import java.util.LinkedList;
import java.util.Locale;

public class AvgWaitTime extends Criterion {
    public AvgWaitTime (LinkedList<Process> processes) {
        super(processes);
    }

    @Override
    public void outcome() {
        System.out.print("Średni czas oczekiwania: ");

        double sumOfWaitTimes = 0;

        for (int i = 0; i < this.processes.length; i++)
            sumOfWaitTimes += endTime[i] - processes[i].getTimeOfStart() - processes[i].getDemand();

        sumOfWaitTimes /= this.processes.length;

        System.out.println(String.format(Locale.US, "%.2f", sumOfWaitTimes));
    }
}
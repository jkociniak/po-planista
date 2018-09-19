/* Implementation of class representing the criterion of the average runtime of a process. */

import java.util.LinkedList;
import java.util.Locale;

public class AvgRunTime extends Criterion {
    public AvgRunTime (LinkedList<Process> processes) {
        super(processes);
    }

    @Override
    public void outcome() {
        System.out.print("Średni czas obrotu: ");

        double sumOfRunTimes = 0;

        for (int i = 0; i < this.processes.length; i++)
            sumOfRunTimes += endTime[i] - processes[i].getTimeOfStart();

        sumOfRunTimes /= this.processes.length;

        System.out.println(String.format(Locale.US, "%.2f", sumOfRunTimes));
    }
}

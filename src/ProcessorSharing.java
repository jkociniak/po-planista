/* Implementation of a class representing the "Processor Sharing" strategy. */

import java.util.ArrayList;
import java.util.LinkedList;

public class ProcessorSharing extends Strategy {
    private ArrayList<Process> runningProcessesList;

    public ProcessorSharing () {
        this.name = "PS";
        this.runningProcessesList = null;
    }

    @Override
    public void experiment(LinkedList<Process> processList, Criterion... criteria) {
        double time = 0;
        double minRD; //Field which describes the minimal remaining demand of running processes.
        Process tempProcess;
        Triplet processInfo;

        this.runningProcessesList = new ArrayList<>();

        do {
            if (!runningProcessesList.isEmpty()) {
                int n = runningProcessesList.size();
                int i = 0;

                /* Update the remaining demands of running processes.
                   If a process has no remaining demand, remove it. */
                while (i < runningProcessesList.size()) {
                    tempProcess = runningProcessesList.get(i);
                    tempProcess.setRemainingDemand(time, n);

                    /* If there is any process which has no remaining demand, set
                       endtime in given strategies, print info about it and remove it. */
                    if (Math.abs(tempProcess.getRemainingDemand()) < 0.000001) {
                        for (Criterion c : criteria)
                            c.setEndTime(tempProcess.getId(), time);

                        processInfo = new Triplet(tempProcess.getId(), tempProcess.getTimeOfStart(), time);
                        System.out.print(processInfo.toString());

                        runningProcessesList.remove(i);
                    }

                    else {
                        tempProcess.setLastCheck(time);
                        i++;
                    }
                }
            }

            /* Put the processes which show up to the running processes list. */
            while (!processList.isEmpty() && processList.peekFirst().getTimeOfStart() == time) {
                tempProcess = processList.pollFirst();
                tempProcess.setLastCheck(time);
                runningProcessesList.add(tempProcess);
            }



            /* Set the new time (jump to the nearest time when something happens). */

            if (runningProcessesList.isEmpty()) {
                if (!processList.isEmpty())
                    time = processList.peekFirst().getTimeOfStart();
            }

            else {
                minRD = runningProcessesList.get(0).getRemainingDemand();
                for (int i = 1; i < runningProcessesList.size(); i++)
                    minRD = Math.min(minRD, runningProcessesList.get(i).getRemainingDemand());

                if (!processList.isEmpty())
                    time += Math.min(minRD*runningProcessesList.size(), processList.peekFirst().getTimeOfStart() - time);
                else
                    time += minRD*runningProcessesList.size();
            }

        } while (!processList.isEmpty() || !runningProcessesList.isEmpty());

        System.out.println();

        for (Criterion c : criteria) {
            if (!c.getClass().getName().equals("AvgWaitTime"))
                c.outcome();
            else
                System.out.println("Średni czas oczekiwania: 0.00");
        }

    }
}

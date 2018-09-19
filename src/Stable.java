/* Implementation of a class representing strategy which has
   the following properties:

   - If the process is running, there's no time limit for it.
     It can only be replaced by the process which
     has just shown up and has higher priority.

   - There is only one process running at the time.    */

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class Stable extends Strategy {
    /* Comparator for priority queue of waiting processes. */
    private Comparator<Process> cmp;

    private PriorityQueue<Process> waitingProcesses;

    /* This field describes if the process which has just
       shown up and has a higher priority can replace
       the running process. If false, the new process
       must stay in the waiting processes queue.        */
    private boolean changeOnTheFly;

    public Stable (String name, Comparator<Process> cmp, boolean changeOnTheFly) {
        this.name = name;
        this.cmp = cmp;
        this.changeOnTheFly = changeOnTheFly;
        this.waitingProcesses = null;
    }

    @Override
    public void experiment(LinkedList<Process> processList, Criterion... criteria) {
        double time = 0;
        int n = processList.size();

        this.waitingProcesses = new PriorityQueue<>(n, this.cmp);

        Process runningProcess = null;
        Triplet processInfo;

        do {
            /* First, we must put the processes which show up now to the waiting processes queue. */
            while (!processList.isEmpty() && processList.peekFirst().getTimeOfStart() == time)
                this.waitingProcesses.add(processList.pollFirst());


            if (runningProcess != null) {
                /* Update the remaining demand of a running process. */
                runningProcess.setRemainingDemand(time);
                runningProcess.setLastCheck(time);

                /* If the running process has no remaining demand,
                   set endtimes for criteria, print info and remove it.*/
                if (Math.abs(runningProcess.getRemainingDemand()) < 0.000001) {
                    for (Criterion c : criteria)
                        c.setEndTime(runningProcess.getId(), time);

                    processInfo = new Triplet(runningProcess.getId(), runningProcess.getTimeOfStart(), time);
                    System.out.print(processInfo.toString());

                    runningProcess = null;
                    continue;
                }


                /* If the process which shows up now has a higher priority
                   than the running process, it must be on the top of the
                   waiting processes queue. If change on the fly is on,
                   replace the running process. */
                if (changeOnTheFly && !waitingProcesses.isEmpty()) {
                    if (waitingProcesses.peek().getRemainingDemand() < runningProcess.getRemainingDemand()) {
                        Process tmp = runningProcess;
                        runningProcess = waitingProcesses.poll();
                        runningProcess.setLastCheck(time);
                        waitingProcesses.add(tmp);
                    }
                }

                /* Set the new time (jump to the nearest time when something happens). */
                if (!processList.isEmpty())
                    time += Math.min(runningProcess.getRemainingDemand(), processList.peekFirst().getTimeOfStart() - time);
                else
                    time += runningProcess.getRemainingDemand();
            }

            else {
                /* The slot for running process is empty, so if it's possible at this time,
                   get the next process from queue and set the new time.*/

                if (!waitingProcesses.isEmpty()) {
                    runningProcess = waitingProcesses.poll();
                    runningProcess.setLastCheck(time);

                    if (!processList.isEmpty())
                        time += Math.min(runningProcess.getRemainingDemand(), processList.peekFirst().getTimeOfStart() - time);
                    else
                        time += runningProcess.getRemainingDemand();
                }

                else if (!processList.isEmpty())
                    time += processList.peekFirst().getTimeOfStart();
            }

        } while (!this.waitingProcesses.isEmpty() || !processList.isEmpty() || runningProcess != null);

        System.out.println();

        for (Criterion c : criteria)
            c.outcome();
    }
}

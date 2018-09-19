/* Implementation of a class representing the "Round Robin" strategy. */

import java.util.LinkedList;

public class RoundRobin extends Strategy {
    private int q; //The length of a time quant.
    private LinkedList<Process> waitingProcesses;

    public RoundRobin (int q) {
        this.name = "RR-" + Integer.toString(q);
        this.q = q;
        this.waitingProcesses = null;
    }

    public void experiment (LinkedList<Process> processList, Criterion... criteria) {
        double time = 0;
        int n = processList.size();
        double qTemp = this.q;

        this.waitingProcesses = new LinkedList<>();

        Process runningProcess = null;
        Triplet processInfo;

        do {
            if (runningProcess != null) {
                /* Update the remaining part of time quant and
                   the remaining demand of a running process.*/
                qTemp -= time - runningProcess.getLastCheck();
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

                /* If the quant of time ended, put the running process back
                   to the waiting processes queue. */
                if (qTemp == 0) {
                    this.waitingProcesses.addLast(runningProcess);
                    runningProcess = null;
                }

                /* Put the processes which show up now to the waiting list. */
                while (!processList.isEmpty() && processList.peekFirst().getTimeOfStart() == time)
                    this.waitingProcesses.addLast(processList.pollFirst());

                /* We added all, but if the quant of time ended, reset it
                   and continue to the next iteration to put a new
                   process in the slot for running process. */
                if (qTemp == 0) {
                    qTemp = this.q;
                    continue;
                }


                /* Set the new time (jump to the nearest time when something happens). */
                if (!processList.isEmpty()) {
                    double jump = Math.min(runningProcess.getRemainingDemand(), processList.peekFirst().getTimeOfStart() - time);
                    jump = Math.min(jump, qTemp);
                    time += jump;
                }

                else
                    time += Math.min(runningProcess.getRemainingDemand(), qTemp);
            }

            else {
                /* Put the processes which show up now to the waiting processes queue. */
                while (!processList.isEmpty() && processList.peekFirst().getTimeOfStart() == time)
                    this.waitingProcesses.addLast(processList.pollFirst());

                /* If there is a process on the top of waiting processes queue, put it
                   in the slot for running process and set the new time.*/
                if (!waitingProcesses.isEmpty()) {
                    runningProcess = waitingProcesses.pollFirst();
                    runningProcess.setLastCheck(time);
                    qTemp = this.q;

                    if (!processList.isEmpty()) {
                        double jump = Math.min(runningProcess.getRemainingDemand(), processList.peekFirst().getTimeOfStart() - time);
                        jump = Math.min(jump, qTemp);
                        time += jump;
                    }
                    else
                        time += Math.min(runningProcess.getRemainingDemand(), qTemp);
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

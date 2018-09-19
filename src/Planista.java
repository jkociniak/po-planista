/* The main class of the program. */

import java.util.LinkedList;
import java.util.ListIterator;

public class Planista {
    public static void main (String... args) {
        Parser ps;

        if (args.length > 1) {
            System.out.println("Za dużo parametrów.");
            return;
        }

        else if (args.length == 1)
            ps = new Parser(args[0]);

        else
            ps = new Parser(null);

        ps.parse();

        if (ps.getProcessList() == null) {
            return;
        }


        LinkedList<Process> processList = ps.getProcessList();
        int[] q = ps.getQArr();
        LinkedList<Process> temp = new LinkedList<>();
        ListIterator<Process> iter;

        Strategy[] strategies = new Strategy[4 + q.length];

        strategies[0] = new Stable ("FCFS", new CompareTOS(), false);
        strategies[1] = new Stable ("SJF", new CompareRD(), false);
        strategies[2] = new Stable ("SRT", new CompareRD(), true);
        strategies[3] = new ProcessorSharing();

        for (int i = 0; i < q.length; i++)
            strategies[i+4] = new RoundRobin(q[i]);

        Criterion[] criteria = {
                new AvgRunTime(processList),
                new AvgWaitTime(processList)
        };

        for (Strategy s : strategies) {
            iter = processList.listIterator();

            while (iter.hasNext()) {
                temp.addLast(iter.next().copy());
            }

            System.out.println("Strategia: " + s.getName());
            s.experiment(temp, criteria);
            System.out.println();

            temp.clear();
        }
    }
}

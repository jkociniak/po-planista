/* Implementation of the comparator for processes
   which compares them using the remaining demand
   in the first place. If they are equal, it
   compares the processes using id. */

import java.util.Comparator;

public class CompareRD implements Comparator<Process> {
    public int compare (Process o1, Process o2) {
        if (o1.getRemainingDemand() > o2.getRemainingDemand())
            return 1;
        else if (o1.getRemainingDemand() < o2.getRemainingDemand())
            return -1;
        else return Integer.compare(o1.getId(), o2.getId());
    }
}

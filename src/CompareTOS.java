/* Implementation of the comparator for processes
   which compares them using the time of start.
   in the first place. If they are equal, it
   compares the processes using id. */

import java.util.Comparator;

public class CompareTOS implements Comparator<Process> {
    public int compare (Process o1, Process o2) {
        if (o1.getTimeOfStart() > o2.getTimeOfStart())
            return 1;
        else if (o1.getTimeOfStart() < o2.getTimeOfStart())
            return -1;
        else return Integer.compare(o1.getId(), o2.getId());
    }
}

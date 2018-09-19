/* Implementation of an abstract class representing a strategy. */

import java.util.LinkedList;

public abstract class Strategy {
    protected String name;

    public String getName() {
        return name;
    }

    /* Method which prints an outcome of experiment using this strategy,
       given list of processes and array of criteria. */
    public abstract void experiment (LinkedList<Process> processList, Criterion... criteria);
}

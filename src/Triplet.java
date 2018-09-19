/* Implementation of class representing triplets
   containing id, time of start and time of end
   of a given process. */

import java.util.Locale;

public class Triplet {
    private int id;
    private int timeOfStart;
    private double timeOfEnd;

    public Triplet(int id, int ts, double te) {
        this.id = id;
        this.timeOfStart = ts;
        this.timeOfEnd = te;
    }

    public String toString () {
        String res = "[" + Integer.toString(id+1) + " " + Integer.toString(timeOfStart) + " ";
        res += String.format(Locale.US, "%.2f", timeOfEnd) + "]";

        return res;
    }
}

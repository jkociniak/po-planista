/* Implementation of class representing a process. */

public class Process {
    private final int id;
    private final int timeOfStart;
    private final int demand;
    private double remainingDemand;
    private double lastCheck;

    public Process(int id, int t, int d) {
        this.id = id;
        this.timeOfStart = t;
        this.remainingDemand = d;
        this.demand = d;
        this.lastCheck = -1; //Initiating with negative value for safety.
    }

    public int getId () { return this.id; }

    public int getTimeOfStart () { return this.timeOfStart; }

    public int getDemand () { return this.demand; }

    public double getLastCheck () { return this.lastCheck; }

    public void setLastCheck (double newLastCheck) { this.lastCheck = newLastCheck; }

    public double getRemainingDemand() { return this.remainingDemand; }

    //Decreases the remaining demand by the difference between given time and last check.
    public void setRemainingDemand (double time) { this.remainingDemand -= (time - this.lastCheck); }

    //Same as the method above, but divides the difference by modifier n.
    public void setRemainingDemand (double time, int n) { this.remainingDemand -= ((time - this.lastCheck)/n); }

    public Process copy () { return new Process(this.id, this.timeOfStart, this.demand); }
}

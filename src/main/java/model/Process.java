package model;

public class Process {

    public String id;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int turnAroundTime = 0;
    public int waitingTime = 0;
    public int responseTime = 0;
    public int finish;
    public int firstStart;

    public Process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
    public int getWaitingTime() {
    return waitingTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public int getEffectiveRemainingTime(int agingInterval) {        
        int agingValue = waitingTime / agingInterval;       
        int effective = remainingTime - agingValue;       
        return Math.max(effective, 1);
       }
}

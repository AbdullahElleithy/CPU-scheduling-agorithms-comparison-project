package model;

public class Process {

    public String id;
    public int arrivalTime;
    public int burstTime;
    public int remainingTime;
    public int turnAroundTime = 0;
    public int waitingTime = 0;
    public int responseTime = 0;

    public Process(String id, int arrivalTime, int burstTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.remainingTime = burstTime;
    }
}
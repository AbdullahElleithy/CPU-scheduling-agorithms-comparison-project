package model;

import java.util.List;

public class Result {

    public String algorithmName;
    public List<Process> processes;
    public List<GanttBlock> ganttBlocks;

    public double avgWaitingTime;
    public double avgTurnaroundTime;
    public double avgResponseTime;

    public String conclusion;

    public Result(String algorithmName) {
        this.algorithmName = algorithmName;
    }
}

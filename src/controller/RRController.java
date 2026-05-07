package controller;

import model.Process;
import model.Result;
import model.GanttBlock;
import java.util.ArrayList;
import java.util.List;

public class RRController {
    private List<Process> RRProcesses;
    private int RRQuantum;
    private Result RRResult = new Result("Round Roubin algorith");

    public RRController(List<Process> processes, int quantum) {
        RRProcesses = processes;
        RRQuantum = quantum;
    }
    public void setResponseTime(Process p, int blockStart) {
        p.responseTime = blockStart - p.arrivalTime;
        return;
    }
    public void setTurnAroundTime(Process p, int completion) {
        p.turnAroundTime = completion - p.arrivalTime;
    }
    public void setWaitingTime(Process p) {
        p.waitingTime = p.turnAroundTime - p.burstTime;
    }
    public Process getFirstProcess() {
        Process min = null;

        for (Process p : RRProcesses) {

            if (min == null || p.arrivalTime < min.arrivalTime) {
                min = p;
            }
        }

        return min;
    }

    public Process getNextProcess(Process current) {
        Process best = null;
        Process bestButGreater = null;
        for (Process p : RRProcesses) {
            if (p == current || p.remainingTime == 0)
                continue;

            if (bestButGreater == null){
                if (p.arrivalTime > current.arrivalTime){
                    bestButGreater = p;
                }
            }

            if (best == null){
                if (p.arrivalTime < current.arrivalTime){
                    best = p;
                }
            }
        }
        if (bestButGreater != null){return bestButGreater;}
        if (best != null){return best;}
        if (current.remainingTime > 0){return current;}
        return null;
    }
    public void calculateAVGs() {

        double sumWT = 0;
        double sumTAT = 0;
        double sumRT = 0;

        for (Process p : RRProcesses) {
            sumWT += p.waitingTime;
            sumTAT += p.turnAroundTime;
            sumRT += p.responseTime;
        }

        RRResult.avgWaitingTime = sumWT / RRProcesses.size();
        RRResult.avgTurnaroundTime = sumTAT / RRProcesses.size();
        RRResult.avgResponseTime = sumRT / RRProcesses.size();
    }

    public Result buildResult() {



        RRResult.processes = RRProcesses;
        return RRResult;
        }
}

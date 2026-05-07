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



    public Result buildResult() {



        RRResult.processes = RRProcesses;
        return RRResult;
        }
}

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




    public Result buildResult() {



        RRResult.processes = RRProcesses;
        return RRResult;
        }
}

package controller;

import model.Process;
import model.Result;
import java.util.List;

public class SRTFController {
    private int currentTime = 0;
    private List<Process> SRTFProcesses;
    private Result SRTFResult = new Result("SRTF algorith");

    public SRTFController(List<Process> processes) {
        SRTFProcesses = processes;
    }

    public Result buildResult() {
        SRTFResult.processes = SRTFProcesses;
        return SRTFResult;
    }
}
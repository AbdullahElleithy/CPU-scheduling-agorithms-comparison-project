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


    public List<GanttBlock> buildGanttChart() {

        List<GanttBlock> chart = new ArrayList<>();


        GanttBlock previous = null;


        Process firstProcess = getFirstProcess();

        GanttBlock currentBlock = new GanttBlock(
                firstProcess,
                firstProcess.arrivalTime,
                0
        );


        while (true) {

            Process currentProcess = currentBlock.myProcess;

            int STMTRemaining = currentProcess.remainingTime;


            if (currentProcess.remainingTime >RRQuantum) {

                currentProcess.remainingTime =
                        currentProcess.remainingTime - RRQuantum;

                currentBlock.end = currentBlock.start + RRQuantum;

            } else {

                currentBlock.end =
                        currentBlock.start + currentProcess.remainingTime;

                currentProcess.remainingTime = 0;

                setTurnAroundTime(currentProcess, currentBlock.end);
                setWaitingTime(currentProcess);
            }

            chart.add(currentBlock);


            if (STMTRemaining == currentProcess.burstTime) {
                setResponseTime(currentProcess, currentBlock.start);
            }


            Process next = getNextProcess(currentProcess);

            if (next != null) {

                previous = currentBlock;

                currentBlock = new GanttBlock(
                        next,
                        previous.end,
                        0
                );

            } else {
                break;
            }
        }

        return chart;
    }

    public Result buildResult() {

        RRResult.ganttBlocks = buildGanttChart();
        calculateAVGs();



        RRResult.processes = RRProcesses;
        return RRResult;
        }
}

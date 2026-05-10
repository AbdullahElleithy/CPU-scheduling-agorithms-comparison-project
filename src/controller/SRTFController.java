package controller;

import model.Process;
import model.Result;
import model.GanttBlock;

import java.util.ArrayList;
import java.util.List;

public class SRTFController {

    public String algorithmName;
    private Result SRTFResult = new Result("SRTF algorithm");
    private static final int AGING_INTERVAL = 5;
    public List<GanttBlock> ganttChartList;
    private List<Process> SRTFProcesses;

    public SRTFController(List<Process> processes) {
        this.SRTFProcesses = processes;
    }


    public Result buildResult() {

        int n = SRTFProcesses.size();
        int currentTime = 0;
        int completed = 0;

        for (Process p : SRTFProcesses) {
            p.remainingTime = p.burstTime;
            p.waitingTime = 0;
        }

        SRTFResult.ganttBlocks = new ArrayList<>();

        boolean[] isStarted = new boolean[n];

        Process lastProcess = null;
        int startTime = 0;

        Result result = new Result("SRTF Algorithm");

        while (completed < n) {

            int shortestIdx = -1;
            int minEffectiveRemainingTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                Process p = SRTFProcesses.get(i);

                if (p.arrivalTime <= currentTime
                        && p.remainingTime > 0) {

                    int effectiveTime = p.getEffectiveRemainingTime(AGING_INTERVAL);


                    if (p.remainingTime < minEffectiveRemainingTime) {

                        minEffectiveRemainingTime = effectiveTime;
                        shortestIdx = i;

                    } else if (effectiveTime == minEffectiveRemainingTime) {

                        if (p.arrivalTime
                                < SRTFProcesses.get(shortestIdx).arrivalTime) {

                            shortestIdx = i;
                        }
                    }
                }
            }

            if (shortestIdx != -1) {

                Process p = SRTFProcesses.get(shortestIdx);

                for (Process q : SRTFProcesses) {
                    if (q.arrivalTime <= currentTime && q.remainingTime > 0) {
                        if (q != p) {
                            q.setWaitingTime(q.getWaitingTime() + 1);
                        }
                    }
                }

                if (lastProcess == null
                        || !p.id.equals(lastProcess.id)) {

                    if (lastProcess != null) {

                        SRTFResult.ganttBlocks.add(
                                new GanttBlock(
                                        lastProcess,
                                        startTime,
                                        currentTime
                                )
                        );
                    }

                    lastProcess = p;
                    startTime = currentTime;
                }

                if (!isStarted[shortestIdx]) {

                    p.firstStart = currentTime;

                    isStarted[shortestIdx] = true;
                }

                p.remainingTime--;

                currentTime++;

                if (p.remainingTime == 0) {

                    completed++;

                    p.finish = currentTime;
                }

            } else {

                if (lastProcess != null) {

                    SRTFResult.ganttBlocks.add(
                            new GanttBlock(
                                    lastProcess,
                                    startTime,
                                    currentTime
                            )
                    );

                    lastProcess = null;
                }

                currentTime++;
            }
        }

        if (lastProcess != null) {

            SRTFResult.ganttBlocks.add(
                    new GanttBlock(
                            lastProcess,
                            startTime,
                            currentTime
                    )
            );
        }

        calculateMetrics();

        SRTFResult.processes = SRTFProcesses;

        return SRTFResult;
    }

    public void calculateMetrics() {
        if (SRTFProcesses == null || SRTFProcesses.isEmpty()) return;
        int n = SRTFProcesses.size();
        double totalWT = 0;
        double totalTAT = 0;
        double totalRT = 0;
        for (Process p : SRTFProcesses) {
            p.turnAroundTime = p.finish - p.arrivalTime;
            p.waitingTime = p.turnAroundTime - p.burstTime;
            p.responseTime = p.firstStart - p.arrivalTime;
            totalWT += p.waitingTime;
            totalTAT += p.turnAroundTime;
            totalRT += p.responseTime;
        }
        SRTFResult.avgWaitingTime = totalWT / n;
        SRTFResult.avgTurnaroundTime = totalTAT / n;
        SRTFResult.avgResponseTime = totalRT / n;
    }
}

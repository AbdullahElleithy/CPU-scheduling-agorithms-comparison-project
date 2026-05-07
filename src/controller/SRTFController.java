package controller;

import model.Process;
import model.Result;
import model.GanttBlock;

import java.util.ArrayList;
import java.util.List;

public class SRTFController {

    public String algorithmName;
    private Result SRTFResult= new Result("SRTF algorith");
    public List<GanttBlock> ganttChartList;
    private List<Process> SRTFProcesses;

    public SRTFController(List<Process> processes) {
        this.SRTFProcesses = processes;
    }

    public void calculateMetrics() {

    }
    public Result buildResult() {

        int n = SRTFProcesses.size();
        int currentTime = 0;
        int completed = 0;

        for (Process p : SRTFProcesses) {
            p.remainingTime = p.burstTime;
        }

        SRTFResult.ganttBlocks = new ArrayList<>();

        boolean[] isStarted = new boolean[n];

        Process lastProcess = null;
        int startTime = 0;

        Result result = new Result("SRTF Algorithm");

        while (completed < n) {

            int shortestIdx = -1;
            int minRemainingTime = Integer.MAX_VALUE;

            for (int i = 0; i < n; i++) {

                Process p = SRTFProcesses.get(i);

                if (p.arrivalTime <= currentTime
                        && p.remainingTime > 0) {

                    if (p.remainingTime < minRemainingTime) {

                        minRemainingTime = p.remainingTime;
                        shortestIdx = i;

                    } else if (p.remainingTime == minRemainingTime) {

                        if (p.arrivalTime
                                < SRTFProcesses.get(shortestIdx).arrivalTime) {

                            shortestIdx = i;
                        }
                    }
                }
            }

            if (shortestIdx != -1) {

                Process p = SRTFProcesses.get(shortestIdx);

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
    }}

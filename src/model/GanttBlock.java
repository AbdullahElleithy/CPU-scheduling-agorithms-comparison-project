package model;
import model.Process;

public class GanttBlock {

    public Process process;
    public String processId;
    public int start;
    public int end;
    public Object myProcess;

    public GanttBlock(Process process, int start, int end) {
        this.myProcess = process;
        this.start = start;
        this.end = end;
    }
}

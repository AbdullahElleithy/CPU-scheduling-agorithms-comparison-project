package model;
import model.Process;

public class GanttBlock {

    public Process myProcess;
    public int start;
    public int end;

    public GanttBlock(Process process, int start, int end) {
        this.myProcess = process;
        this.start = start;
        this.end = end;
    }
}

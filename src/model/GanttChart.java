package model;

import java.util.ArrayList;
import java.util.List;

public class GanttChart {

    private List<GanttBlock> blocks = new ArrayList<>();

    public void addBlock(GanttBlock block) {
        blocks.add(block);
    }
    public List<GanttBlock> getBlocks() {
        return blocks;
    }
}

package PageTable;

import Config.Config;
import Config.OutputWriter;

import java.util.Arrays;

public class InvertedTable implements PagleTable{

    private int[] table;
    private int numFrames;

    public InvertedTable(Config config){
        this.numFrames = config.getPhysicalAddressSpace() / config.getPageSizeSpace();
        this.table = new int[numFrames];
        Arrays.fill(table, -1);
    }
    @Override
    public int[] getTable() {
        return new int[0];
    }

    @Override
    public int getFrameNumber(int virtualPageNumber) {
        return 0;
    }

    @Override
    public void setMapping(int virtualPageNumber, int frameNumber) {

    }

    @Override
    public void printState(OutputWriter writer) {

    }
}

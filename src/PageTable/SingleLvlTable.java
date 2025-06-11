package PageTable;

import Config.Config;
import Config.OutputWriter;

import java.util.Arrays;

public class SingleLvlTable implements PagleTable{

    private int[] table;

    public SingleLvlTable(Config config) {
        int numVirtualPages = config.getVirtualAddressSpace() / config.getPageSizeSpace();
        this.table = new int[numVirtualPages];
        Arrays.fill(table, -1);
    }

    public int[] getTable() {
        return table;
    }

    @Override
    public int getFrameNumber(int virtualPageNumber) {
        return table[virtualPageNumber];
    }

    @Override
    public void setMapping(int virtualPageNumber, int frameNumber) {
        table[virtualPageNumber] = frameNumber;
    }

    @Override
    public void printState(OutputWriter writer) {

    }
}

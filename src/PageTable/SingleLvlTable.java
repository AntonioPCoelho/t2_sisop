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

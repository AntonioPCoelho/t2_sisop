package PageTable;

import Config.Config;
import Config.OutputWriter;

public class TwoLvlTable implements PagleTable {

    private int[] firstLevelTable;
    private int[] secondLevelTables;
    private final int pt1Bits;
    private final int pt2Bits;

    public TwoLvlTable(Config config) {
        int virtualAdressBits = config.getVirtualAddressBits();

        this.pt1Bits = virtualAdressBits / 2;
        this.pt2Bits = virtualAdressBits - pt1Bits;

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

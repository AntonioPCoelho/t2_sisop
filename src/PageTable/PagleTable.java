package PageTable;

import Config.OutputWriter;

public interface PagleTable {

    int getFrameNumber(int virtualPageNumber);

    void setMapping(int virtualPageNumber, int frameNumber);

    void printState(OutputWriter writer);
}

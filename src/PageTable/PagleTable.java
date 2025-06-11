package PageTable;

import Config.Config;
import Config.OutputWriter;

public interface PagleTable {

    int[] getTable();

    int getFrameNumber(int virtualPageNumber);

    void setMapping(int virtualPageNumber, int frameNumber);

    void printState(OutputWriter writer);

    static PagleTable create(Config config) {
        switch (config.getPageType()) {
            case SINGLE_LVL:
                return new SingleLvlTable(config);
            case TWO_LVL:
                return new TwoLvlTable(config);
            case INVERTED:
                return new InvertedTable(config);
            default:
                throw new IllegalArgumentException("Tipo de tabela de página desconhecido: " + config.getPageType());
        }
    }
}

package PageTable;

import Config.Config;
import Config.OutputWriter;
import Memory.PhysicalMem; // Importação necessária

public interface PageTable {

    int[] getTable();

    int getFrameNumber(int virtualPageNumber);

    // Novo método na interface para lidar com mapeamento e alocação, se necessário
    int mapPage(int virtualPageNumber, PhysicalMem physicalMem);

    void setMapping(int virtualPageNumber, int frameNumber);

    void printState(OutputWriter writer);

    static PageTable create(Config config) {
        return switch (config.getPageType()) {
            case SINGLE_LVL -> new SingleLvlTable(config);
            case TWO_LVL -> new TwoLvlTable(config);
            case INVERTED -> new InvertedTable(config);
            default -> throw new IllegalArgumentException("Tipo de tabela de página desconhecido: " + config.getPageType());
        };
    }
}
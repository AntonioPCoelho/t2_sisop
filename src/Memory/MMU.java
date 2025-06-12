package Memory;

import Config.Config;
import PageTable.PageTable;


public class MMU {

    private final PhysicalMem physicalMem;
    private final PageTable pageTable;
    private final Config config;

    public MMU(PhysicalMem physicalMem, PageTable pageTable, Config config) {
        this.physicalMem = physicalMem;
        this.pageTable = pageTable;
        this.config = config;
    }

    public int translateAddress(int virtualAddress) {
        int pageSize = config.getPageSizeSpace();
        int virtualPageNumber = virtualAddress / pageSize;
        int offset = virtualAddress % pageSize;
        int frame = pageTable.getFrameNumber(virtualPageNumber);
        return switch (config.getPageType()) {
            case SINGLE_LVL -> {
                if (frame == -1) {
                    frame = physicalMem.allocateFrame(virtualPageNumber);
                    if (frame == -1) {
                        yield -1;
                    }
                    pageTable.setMapping(virtualPageNumber, frame);
                }
                yield frame * pageSize + offset;
            }
            case INVERTED -> {
                // Se a página não está na tabela, aloca uma nova moldura.
                if (frame == -1) {
                    frame = physicalMem.allocateFrame(virtualPageNumber);
                    // Se a memória física estiver cheia, retorna erro.
                    if (frame == -1) {
                        yield -1;
                    }
                    // Atualiza a tabela invertida com o novo mapeamento.
                    pageTable.setMapping(virtualPageNumber, frame);
                }
                // Calcula o endereço físico final.
                yield frame * pageSize + offset;
            }
            case TWO_LVL -> -1;
            default -> -1;
        };
    }
}

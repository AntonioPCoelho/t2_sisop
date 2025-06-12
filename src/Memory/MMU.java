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

        // A lógica de alocação e mapeamento é agora responsabilidade da PageTable.
        // O método mapPage retornará o frame number, alocando-o se necessário.
        int frame = pageTable.mapPage(virtualPageNumber, physicalMem);

        if (frame == -1) {
            // Se mapPage retornou -1, significa que a memória física está cheia
            // ou houve algum problema no mapeamento/alocação.
            return -1;
        }

        // Calcula o endereço físico final
        return frame * pageSize + offset;
    }
}
package PageTable;

import Config.Config;
import Config.OutputWriter;
import Memory.PhysicalMem; // Importação necessária
import java.util.Arrays;

public class SingleLvlTable implements PageTable{

    private final int[] table;

    public SingleLvlTable(Config config) {
        int numVirtualPages = config.getVirtualAddressSpace() / config.getPageSizeSpace();
        this.table = new int[numVirtualPages];
        Arrays.fill(table, -1);
    }

    @Override
    public int[] getTable() {
        return table;
    }

    @Override
    public int getFrameNumber(int virtualPageNumber) {
        // Garante que o virtualPageNumber é um índice válido
        if (virtualPageNumber < 0 || virtualPageNumber >= table.length) {
            return -1; // Página virtual fora dos limites da tabela
        }
        return table[virtualPageNumber];
    }

    @Override
    public void setMapping(int virtualPageNumber, int frameNumber) {
        // Garante que o virtualPageNumber é um índice válido antes de mapear
        if (virtualPageNumber >= 0 && virtualPageNumber < table.length) {
            table[virtualPageNumber] = frameNumber;
        } else {
            System.err.println("Erro: Tentativa de mapear página virtual fora dos limites: " + virtualPageNumber);
        }
    }

    @Override
    public int mapPage(int virtualPageNumber, PhysicalMem physicalMem) {
        int frame = getFrameNumber(virtualPageNumber);
        if (frame == -1) { // Se a página não está mapeada
            frame = physicalMem.allocateFrame(virtualPageNumber); // Tenta alocar um frame
            if (frame != -1) { // Se a alocação foi bem-sucedida
                setMapping(virtualPageNumber, frame); // Mapeia a página virtual para o frame
            }
        }
        return frame; // Retorna o frame (pode ser -1 se a memória estiver cheia)
    }

    @Override
    public void printState(OutputWriter writer) {
        writer.writeLine("\n--- Tabela de Páginas de Nível Único ---");
        String state = Arrays.toString(this.table);
        writer.writeLine(state);
    }
}
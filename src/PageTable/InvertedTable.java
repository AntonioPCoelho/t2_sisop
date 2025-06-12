package PageTable;

import Config.Config;
import Config.OutputWriter;
import Memory.PhysicalMem; // Importação necessária
import java.util.Arrays;

public class InvertedTable implements PageTable{

    private final int[] table;
    private final int numFrames;

    public InvertedTable(Config config){
        this.numFrames = config.getPhysicalAddressSpace() / config.getPageSizeSpace();
        this.table = new int[numFrames];
        Arrays.fill(table, -1);
    }
    @Override
    public int[] getTable() {
        return this.table;
    }

   /**
    * @param virtualPageNumber O número da página virtual para a qual se deseja obter o número do quadro.
    * @return O número da moldura se a página estiver mapeada, ou -1 se não estiver mapeada.
    */
    @Override
    public int getFrameNumber(int virtualPageNumber) {
        for (int frameNumber = 0; frameNumber < numFrames; frameNumber++) {
            if (table[frameNumber] == virtualPageNumber) {
                return frameNumber;
            }
        }
        return -1; // Página não mapeada
    }

    /**
     * Mapeia uma página virtual para uma moldura física.
     * @param virtualPageNumber O número da página virtual a ser mapeada.
     * @param frameNumber O número da moldura física onde a página será armazenada.
     */
    @Override
    public void setMapping(int virtualPageNumber, int frameNumber) {
        // Na posição da moldura (frameNumber), armazena qual página virtual (virtualPageNumber) ela contém.
        if (frameNumber >= 0 && frameNumber < numFrames) {
            table[frameNumber] = virtualPageNumber;
        } else {
            System.err.println("Erro: Tentativa de mapear página virtual para um frame inválido: " + frameNumber);
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
        // Escreve o estado da tabela de forma formatada.
        writer.writeLine("\n--- Tabela de Páginas Invertida (Frame -> Página Virtual) ---");
        String state = Arrays.toString(this.table);
        writer.writeLine(state);
    }
}
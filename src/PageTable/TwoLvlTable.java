package PageTable;

import Config.Config;
import Config.OutputWriter;
import Memory.PhysicalMem; // Importação necessária
import java.util.Arrays;

public class TwoLvlTable implements PageTable {

    private final int[] firstLevelTable;
    private final int[][] secondLevelTables;

    private final int pt1Bits;
    private final int pt2Bits;
    private final int pageSizeBits;

    public TwoLvlTable(Config config) {
        this.pageSizeBits = config.pageSize;
        int virtualPageNumberBits = config.getVirtualAddressBits() - pageSizeBits;

        this.pt1Bits = virtualPageNumberBits / 2;
        this.pt2Bits = virtualPageNumberBits - this.pt1Bits;

        this.firstLevelTable = new int[1 << pt1Bits];
        Arrays.fill(this.firstLevelTable, -1);

        this.secondLevelTables = new int[1 << pt1Bits][];
    }

    @Override
    public int[] getTable() {
        return null;
    }

    @Override
    public int getFrameNumber(int virtualPageNumber) {
        int pt1Index = virtualPageNumber >> pt2Bits;
        int pt2Index = virtualPageNumber & ((1 << pt2Bits) - 1);

        // Adicionar validação de índice
        if (pt1Index < 0 || pt1Index >= firstLevelTable.length) {
            return -1;
        }

        if (firstLevelTable[pt1Index] == -1 || secondLevelTables[pt1Index] == null) {
            return -1;
        }

        int[] currentSecondLevelTable = secondLevelTables[pt1Index];

        // Adicionar validação de índice
        if (pt2Index < 0 || pt2Index >= currentSecondLevelTable.length) {
            return -1;
        }

        return currentSecondLevelTable[pt2Index];
    }

    @Override
    public void setMapping(int virtualPageNumber, int frameNumber) {
        int pt1Index = virtualPageNumber >> pt2Bits;
        int pt2Index = virtualPageNumber & ((1 << pt2Bits) - 1);

        // Adicionar validação de índice
        if (pt1Index < 0 || pt1Index >= firstLevelTable.length) {
            System.err.println("Erro: Tentativa de mapear página virtual com pt1Index fora dos limites: " + pt1Index);
            return;
        }

        if (secondLevelTables[pt1Index] == null) {
            secondLevelTables[pt1Index] = new int[1 << pt2Bits];
            Arrays.fill(secondLevelTables[pt1Index], -1);
        }

        firstLevelTable[pt1Index] = pt1Index;

        // Adicionar validação de índice
        if (pt2Index < 0 || pt2Index >= secondLevelTables[pt1Index].length) {
            System.err.println("Erro: Tentativa de mapear página virtual com pt2Index fora dos limites: " + pt2Index);
            return;
        }

        secondLevelTables[pt1Index][pt2Index] = frameNumber;
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
        writer.writeLine("\n--- Tabela de Páginas de Dois Níveis ---");
        writer.writeLine("Diretório de Páginas (Primeiro Nível):");
        writer.writeLine(Arrays.toString(firstLevelTable));

        writer.writeLine("\nTabelas de Segundo Nível:");
        for (int i = 0; i < secondLevelTables.length; i++) {
            if (secondLevelTables[i] != null) {
                writer.writeLine(String.format("  Tabela Secundária para Entrada %d (Índice PT1): %s", i, Arrays.toString(secondLevelTables[i])));
            } else {
                writer.writeLine(String.format("  Tabela Secundária para Entrada %d (Índice PT1): Não alocada", i));
            }
        }
    }
}
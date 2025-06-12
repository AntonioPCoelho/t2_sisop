package Config;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class OutputWriter implements AutoCloseable {
    private final BufferedWriter writer;
    private final String filePath = "src/Config/Files/output.txt";

    public OutputWriter() throws IOException {
        this.writer = new BufferedWriter(new FileWriter(filePath));
    }

    public void writeLine(String line) {
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
        }
    }

    public void writeBinary(int virtualAddress, int physicalAddress, int frameNumber, Config config) {
        int vTotalBits = config.virtualAdressBits;
        int offsetBits = config.pageSize;
        int vPageBits = vTotalBits - offsetBits;

        int virtualPageNumber = virtualAddress >> offsetBits;
        int offset = virtualAddress & ((1 << offsetBits) - 1);

        String virtualBinary = String.format("%" + vTotalBits + "s", Integer.toBinaryString(virtualAddress)).replace(' ', '0');
        String vPageBinary = String.format("%" + vPageBits + "s", Integer.toBinaryString(virtualPageNumber)).replace(' ', '0');
        String offsetBinary = String.format("%" + offsetBits + "s", Integer.toBinaryString(offset)).replace(' ', '0');

        int pTotalBits = config.physicalAdressBits;
        int pFrameBits = pTotalBits - offsetBits;

        String physicalBinary = String.format("%" + pTotalBits + "s", Integer.toBinaryString(physicalAddress)).replace(' ', '0');
        String pFrameBinary = String.format("%" + pFrameBits + "s", Integer.toBinaryString(frameNumber)).replace(' ', '0');

        String line = String.format("Binário | VA: %s (pag: %s, ooffset: %s) -> PA: %s (frame: %s, offset: %s)",
                virtualBinary, vPageBinary, offsetBinary,
                physicalBinary, pFrameBinary, offsetBinary
        );
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
        }

    }

    @Override
    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}

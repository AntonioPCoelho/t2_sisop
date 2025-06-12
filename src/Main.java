import Config.Config;
import Config.OutputWriter;
import Config.ReaderConfig;
import Memory.MMU;
import Memory.PhysicalMem;
import Memory.VirtualMem;
import PageTable.PageTable;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ReaderConfig readerConfig = new ReaderConfig();
        Config config = readerConfig.readConfigFile();
        PhysicalMem physicalMem = new PhysicalMem(config);
        VirtualMem virtualMem = new VirtualMem(config);
        List<Integer> virtualAddresses = readerConfig.virtualAdressFile();
        PageTable pageTable = PageTable.create(config);
        MMU mmu = new MMU(physicalMem, pageTable, config);

        try(OutputWriter outputWriter = new OutputWriter()) {
            for (int virtualAddress : virtualAddresses) {
                String segment = virtualMem.determineSegment(virtualAddress);
                int virtualPageNumber = virtualMem.getVirtualPageNumber(virtualAddress);
                int physicalAddress = mmu.translateAddress(virtualAddress);
                if (physicalAddress == -1) {
                    System.out.println("memoria cheia!");
                    break;
                }
                int frame = pageTable.getFrameNumber(virtualPageNumber);
                String output = String.format("(Virtual Address: %d (Page: %d) -> Physical Address: %d (Frame: %d) | Segment: %s, ",
                        virtualAddress,virtualPageNumber, physicalAddress, frame, segment);
                //System.out.println(output);
                outputWriter.writeLine(output);
                outputWriter.writeBinary(virtualAddress, physicalAddress, frame, config);
            }
            outputWriter.writeLine("\n--- Vetor Final da Memória Física ---");
            String physicalMemoryState = Arrays.toString(physicalMem.getFrames());
            outputWriter.writeLine(physicalMemoryState);
            outputWriter.writeLine("\n--- Vetor Final da Tabela de Páginas ---");
            pageTable.printState(outputWriter);
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("Finalizado. Verifique o arquivo  em src/Config/Files.");
    }
}
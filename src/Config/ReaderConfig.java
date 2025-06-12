package Config;

import PageTable.PageType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ReaderConfig {

    private String filePath;
    private String filePathVA;
    public ReaderConfig() {
        Path basePath = Paths.get("src", "Config", "Files").toAbsolutePath();
        this.filePath = basePath.resolve("config.txt").toString();
        this.filePathVA = basePath.resolve("virtualAddress.txt").toString();
    }

    public Config readConfigFile(){
        int virtualAddressBits = 0;
        int physicalAddressBits = 0;
        int pageSizeBits = 0;
        int textSize = 0;
        int dataSize = 0;
        int stackSize = 0;
        PageType pageType = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                String key = parts[0].trim();
                String value = parts[1].trim();
                switch (key) {
                    case "virtualAddressBits" -> virtualAddressBits = Integer.parseInt(value);
                    case "physicalAddressBits" -> physicalAddressBits = Integer.parseInt(value);
                    case "pageSizeBits" -> pageSizeBits = Integer.parseInt(value);
                    case "textSize" -> textSize = Integer.parseInt(value);
                    case "dataSize" -> dataSize = Integer.parseInt(value);
                    case "stackSize" -> stackSize = Integer.parseInt(value);
                    case "pageTableType" -> pageType = PageType.valueOf(value.toUpperCase());
                    default -> throw new IllegalArgumentException("Chave desconhecida: " + key);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        if(physicalAddressBits > virtualAddressBits) {
            throw new IllegalArgumentException("Não é possivel ter endereço fisico maior que endereço virtual.");
        }
        return Config.getInstance(virtualAddressBits, physicalAddressBits, pageSizeBits, textSize, dataSize, stackSize, pageType);
    }

    public List<Integer> virtualAdressFile(){
        List<Integer> virtualAddresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePathVA))) {
            String line;
            while ((line = reader.readLine()) != null) {
                    int address = Integer.parseInt(line.trim());
                    virtualAddresses.add(address);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return virtualAddresses;
    }
}

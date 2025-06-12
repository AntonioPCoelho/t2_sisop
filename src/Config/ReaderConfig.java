package Config;

import PageTable.PageType;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ReaderConfig {

    private final String filePath = "src/Config/Files/config.txt";
    private final String filePathVA = "src/Config/Files/virtualAddress.txt";
    private Config loadedConfig; // Campo para armazenar a configuração lida

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
            System.err.println("Erro ao ler o arquivo de configuração: " + e.getMessage()); // Saída de erro aprimorada
            // e.printStackTrace();
            System.exit(1); // Encerrar o programa em caso de erro crítico de configuração
        }
        if(physicalAddressBits > virtualAddressBits) {
            throw new IllegalArgumentException("Não é possivel ter endereço fisico maior que endereço virtual.");
        }
        loadedConfig = Config.getInstance(virtualAddressBits, physicalAddressBits, pageSizeBits, textSize, dataSize, stackSize, pageType);
        return loadedConfig;
    }

    public List<Integer> virtualAdressFile(){
        List<Integer> virtualAddresses = new ArrayList<>();
        // Garantir que a configuração já foi lida
        if (loadedConfig == null) {
            readConfigFile(); // Se não foi lida, tenta ler (idealmente, readConfigFile é chamado antes)
        }
        long maxVirtualAddress = (long) Math.pow(2, loadedConfig.getVirtualAddressBits()) - 1; // 2^n - 1

        try (BufferedReader reader = new BufferedReader(new FileReader(filePathVA))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int address = Integer.parseInt(line.trim());
                    // Validação do endereço virtual
                    if (address < 0 || address > maxVirtualAddress) {
                        System.err.println("Aviso: Endereço virtual " + address + " fora do limite (0 a " + maxVirtualAddress + "). Ignorando.");
                    } else {
                        virtualAddresses.add(address);
                    }
                } catch (NumberFormatException e) {
                    System.err.println("Aviso: Linha inválida no arquivo de endereços virtuais (não é um número): '" + line + "'. Ignorando.");
                }
            }
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo de endereços virtuais: " + e.getMessage());
            // e.printStackTrace();
        }
        return virtualAddresses;
    }
}
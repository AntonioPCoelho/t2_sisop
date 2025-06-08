package Memory;

import Config.Config;

public class VirtualMem {

    private Config config;

    private final int endOfText;
    private final int endOfData;
    private final int endOfBss;
    private final int startOfStack;

    public VirtualMem(Config config) {
        this.config = config;
        this.endOfText = (int) Math.pow(2,config.textSize);
        this.endOfData = this.endOfText + (int)Math.pow(2,config.dataSize);
        this.startOfStack = config.getVirtualAddressSpace() - (int) Math.pow(2, config.stackSize);

        int bssSize;
        if (this.startOfStack > this.endOfData) {
            bssSize = this.startOfStack - this.endOfData;
        } else {
            bssSize = 0;
        }

        this.endOfBss = this.endOfData + bssSize;
    }

    public String determineSegment(int virtualAddress) {
        if (virtualAddress < this.endOfText) {
            return ".text";
        }
        if (virtualAddress < this.endOfData) {
            return ".data";
        }
        if (virtualAddress < this.endOfBss) {
            return ".bss";
        }
        if (virtualAddress >= this.startOfStack) {
            return ".stack";
        }

        return ".heap"; //foi adicionado heap aqui caso não encontre, professor.
    }

    public int getVirtualPageNumber(int virtualAddress) {
        return (int) (virtualAddress / config.getPageSizeSpace());
    }

    //nã sei se ta certo
    public int getOffset(int virtualAddress) {
        return (int) (virtualAddress % config.getPageSizeSpace());
    }
}




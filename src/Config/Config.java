package Config;

import PageTable.PageType;

public class Config {

    private static Config instance;

    public final int virtualAdressBits;
    public final int physicalAdressBits;
    public final int pageSize;
    public final int textSize;
    public final int dataSize;
    public final int stackSize;
    private final PageType pageType;

    private Config(int virtualAdressBits, int physicalAdressBits, int pageSize, int textSize, int dataSize, int stackSize, PageType pageType) {
        this.virtualAdressBits = virtualAdressBits;
        this.physicalAdressBits = physicalAdressBits;
        this.pageSize = pageSize;
        this.textSize = textSize;
        this.dataSize = dataSize;
        this.stackSize = stackSize;
        this.pageType = pageType;
    }
    public static Config getInstance(int virtualAdressBits, int physicalAdressBits, int pageSize, int textSize, int dataSize, int stackSize, PageType pageType) {
        if (instance == null) {
            instance = new Config(virtualAdressBits, physicalAdressBits, pageSize, textSize, dataSize, stackSize, pageType);
        }
        return instance;
    }
    public int getVirtualAddressSpace() {
        return (int) Math.pow(2, virtualAdressBits);
    }
    public int getPhysicalAddressSpace() {
        return (int) Math.pow(2, physicalAdressBits);
    }

    public int getPageSizeSpace(){
        return (int) Math.pow(2, pageSize);
    }

    public int getVirtualAddressBits() {
        return virtualAdressBits;
    }
    public int getPhysicalAddressBits() {
        return physicalAdressBits;
    }
    public int getPageCount() {
        return getVirtualAddressSpace() / pageSize;
    }
    public int getPageCountPhysical() {
        return getPhysicalAddressSpace() / pageSize;
    }
    public int getTextPages() {
        return (int) Math.ceil((double) textSize / pageSize);
    }
    public int getDataPages() {
        return (int) Math.ceil((double) dataSize / pageSize);
    }

    public PageType getPageType() {
        return pageType;
    }

    @Override
    public String toString() {
        return "Config{" +
                "virtualAdressBits=" + virtualAdressBits +
                ", physicalAdressBits=" + physicalAdressBits +
                ", pageSize=" + pageSize +
                ", textSize=" + textSize +
                ", dataSize=" + dataSize +
                ", stackSize=" + stackSize +
                ", pageType=" + pageType +
                '}';
    }
}

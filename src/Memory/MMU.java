package Memory;

import Config.Config;
import PageTable.PagleTable;


public class MMU {

    private PhysicalMem physicalMem;
    private PagleTable pageTable;
    private Config config;

    public MMU(PhysicalMem physicalMem, PagleTable pageTable, Config config) {
        this.physicalMem = physicalMem;
        this.pageTable = pageTable;
        this.config = config;
    }

    public int translateAddress(int virtualAddress) {

        switch (config.getPageType()){
            case SINGLE_LVL:{
                int pageSize = config.getPageSizeSpace();
                int virtualPageNumber = virtualAddress / pageSize;
                int offset = virtualAddress % pageSize;
                int frame = pageTable.getFrameNumber(virtualPageNumber);
                if(frame == -1){
                    frame = physicalMem.allocateFrame(virtualPageNumber);
                    if(frame == -1){
                        return -1;
                    }
                    pageTable.setMapping(virtualPageNumber, frame);
                }
                return frame * pageSize + offset;
            }
            case TWO_LVL:
                return -1;

            default:
                return -1;
        }


    }
}

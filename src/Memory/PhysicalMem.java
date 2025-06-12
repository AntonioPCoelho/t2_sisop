package Memory;
import Config.Config;
import java.util.Arrays;

public class PhysicalMem {

    private final int[] frames;
    private int frameCount;
    private final int numFrames;
    private final int pageSize;

    public PhysicalMem(Config config) {
        this.numFrames = config.getPhysicalAddressSpace()/ config.getPageSizeSpace();
        this.pageSize = config.getPageSizeSpace();
        this.frames = new int[numFrames];
        this.frameCount = 0;
        Arrays.fill(frames, -1);
    }


    public int allocateFrame(int virtualPageNumber) {
        for (int i = 0; i < numFrames; i++) {
            if (frames[i] == -1) {
                frames[i] = virtualPageNumber;
                frameCount++;
                return i;
            }
        }
        return -1; //botei -1 pq pode não ter mais frames disponiveis, mas como a alocação inicial começa com -1 não sei se é o melhor
    }

    public int[] getFrames() {
        return frames;
    }


    public int getFrameCount() {
        return frameCount;
    }


    public int getNumFrames() {
        return numFrames;
    }


    public int getPageSize() {
        return pageSize;
    }

}

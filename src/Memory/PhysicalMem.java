package Memory;
import Config.Config;

import java.util.Arrays;

public class PhysicalMem {

    private int[] frames;
    private int frameCount;
    private int numFrames;
    private int pageSize;

    public PhysicalMem(Config config) {
        this.numFrames = config.getPhysicalAddressSpace()/ config.getPageSizeSpace();
        this.pageSize = config.getPageSizeSpace();
        this.frames = new int[numFrames];
        this.frameCount = 0;
        Arrays.fill(frames, -1);
    }

    public int[] getFrames() {
        return frames;
    }

    public void setFrames(int[] frames) {
        this.frames = frames;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public void setFrameCount(int frameCount) {
        this.frameCount = frameCount;
    }

    public int getNumFrames() {
        return numFrames;
    }

    public void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}

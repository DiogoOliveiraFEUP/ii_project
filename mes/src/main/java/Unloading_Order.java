public class Unloading_Order extends Order{

    private int blockType;

    public Unloading_Order(int blockType,int mainID, int inputTime, int maxDelay, int penalty) {
        super(mainID, inputTime, maxDelay, penalty);
        this.blockType = blockType;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }
}

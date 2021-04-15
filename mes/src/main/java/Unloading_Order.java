public class Unloading_Order extends Order{

    private String blockType;

    public Unloading_Order(String blockType,int mainID, int inputTime, int maxDelay, int penalty) {
        super(mainID, inputTime, maxDelay, penalty);
        this.blockType = blockType;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }
}

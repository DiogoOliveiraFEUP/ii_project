public class Unloading_Order extends Order{

    private String blockType;
    private String destination;

    public Unloading_Order(String blockType, String destination, int mainID, int inputTime, int maxDelay, int penalty) {
        super(mainID, inputTime, maxDelay, penalty);
        this.blockType = blockType;
        this.destination = destination;
    }

    public String getBlockType() {
        return blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }
}

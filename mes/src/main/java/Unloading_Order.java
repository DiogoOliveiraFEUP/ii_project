public class Unloading_Order extends Order{

    private final String blockType;
    private final String destination;

    public Unloading_Order(String blockType, String destination, int mainID) {
        super(mainID);

        this.blockType = blockType;
        this.destination = destination;
    }

    public String getBlockType() {
        return blockType;
    }

    public String getDestination() {
        return destination;
    }
}

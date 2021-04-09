public class Transformation_Order extends Order{

    private int initBlockType;
    private int finalBlockType;

    public Transformation_Order(int initBlockType, int finalBlockType, int mainID, int inputTime, int maxDelay, int penalty) {
        super(mainID, inputTime, maxDelay, penalty);
        this.finalBlockType = finalBlockType;
        this.initBlockType = initBlockType;
    }

    public int getInitBlockType() {
        return initBlockType;
    }

    public void setInitBlockType(int initBlockType) {
        this.initBlockType = initBlockType;
    }

    public int getFinalBlockType() {
        return finalBlockType;
    }

    public void setFinalBlockType(int finalBlockType) {
        this.finalBlockType = finalBlockType;
    }
}

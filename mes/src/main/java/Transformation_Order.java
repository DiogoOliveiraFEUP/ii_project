public class Transformation_Order extends Order{

    private String initBlockType;
    private String finalBlockType;

    public Transformation_Order(String initBlockType, String finalBlockType, int mainID, int inputTime, int maxDelay, int penalty) {
        super(mainID, inputTime, maxDelay, penalty);
        this.finalBlockType = finalBlockType;
        this.initBlockType = initBlockType;
    }

    public String getInitBlockType() {
        return initBlockType;
    }

    public void setInitBlockType(String initBlockType) {
        this.initBlockType = initBlockType;
    }

    public String getFinalBlockType() {
        return finalBlockType;
    }

    public void setFinalBlockType(String finalBlockType) {
        this.finalBlockType = finalBlockType;
    }
}

public class Transformation_Order extends Order{

    private final String initBlockType;
    private final String finalBlockType;
    private final int inputTime;
    private final int maxDelay;
    private final int penalty;
    private final int realInputTime;
    private int startTime;
    private int endTime;
    private int realPenalty;

    public Transformation_Order(String initBlockType, String finalBlockType, int mainID, int inputTime, int maxDelay, int penalty) {
        super(mainID);

        this.finalBlockType = finalBlockType;
        this.initBlockType = initBlockType;
        this.inputTime = inputTime;
        this.maxDelay = maxDelay;
        this.penalty = penalty;

        /* ---- REAL INPUT TIME --- */
        /* ### Change this ### */
        this.realInputTime = inputTime;

    }

    public String getInitBlockType() {
        return initBlockType;
    }


    public String getFinalBlockType() {
        return finalBlockType;
    }

    public int getInputTime() {
        return inputTime;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public int getPenalty() {
        return penalty;
    }

    public int getRealInputTime() {
        return realInputTime;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public int getRealPenalty() {
        return realPenalty;
    }

    public void setRealPenalty(int realPenalty) {
        this.realPenalty = realPenalty;
    }
}

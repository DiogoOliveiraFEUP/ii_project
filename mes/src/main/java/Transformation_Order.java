import java.time.Instant;

public class Transformation_Order extends Order{

    private final String initBlockType;
    private final String finalBlockType;
    private final long inputTime;
    private final int maxDelay;
    private final int penalty;
    private final long realInputTime;
    private int startTime;
    private int endTime;
    private int realPenalty;

    public Transformation_Order(String initBlockType, String finalBlockType, int mainID, long inputTime, int maxDelay, int penalty) {
        super(mainID);

        this.finalBlockType = finalBlockType;
        this.initBlockType = initBlockType;
        this.inputTime = inputTime;
        this.maxDelay = maxDelay;
        this.penalty = penalty;

        this.realInputTime = Instant.now().getEpochSecond();
    }

    public String getInitBlockType() {
        return initBlockType;
    }


    public String getFinalBlockType() {
        return finalBlockType;
    }

    public long getInputTime() {
        return inputTime;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public int getPenalty() {
        return penalty;
    }

    public long getRealInputTime() {
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

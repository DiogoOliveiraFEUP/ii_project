import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

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

    public Transformation_Order(int mainID, int subID, String initBlockType, String finalBlockType, long inputTime, int maxDelay, int penalty) {

        super(mainID,subID);

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

    public static List<Integer> getMainIDs(List<Transformation_Order> orders){
        List<Integer> ids = new ArrayList<>();
        for(Transformation_Order order : orders){
            if(ids.contains(order.getMainID())){}
            else {ids.add(order.getMainID());}
        }
        return ids;
    }

    public static List<Transformation_Order> getOrdersByMainID(List<Transformation_Order> orders, int id){
        List<Transformation_Order> result = new ArrayList<>();
        for(Transformation_Order order : orders){
            if(order.getMainID() == id) result.add(order);
        }
        return result;
    }
}

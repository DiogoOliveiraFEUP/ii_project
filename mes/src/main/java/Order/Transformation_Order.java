package Order;

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
    private long startTime;
    private long endTime;
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

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
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

    public static List<Integer> getIDs(List<Transformation_Order> orders){
        List<Integer> ids = new ArrayList<>();
        for(Transformation_Order order : orders){
            if(ids.contains(order.getID())){}
            else {ids.add(order.getID());}
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

    public static List<Transformation_Order> getOrdersByID(List<Transformation_Order> orders, int id){
        List<Transformation_Order> result = new ArrayList<>();
        for(Transformation_Order order : orders){
            if(order.getID() == id) result.add(order);
        }
        return result;
    }

    public static List<Transformation_Order> getOrdersByMainID_ID(List<Transformation_Order> orders, int mainid, int id){
        List<Transformation_Order> result = new ArrayList<>();
        for(Transformation_Order order : orders){
            if(order.getID() == id && order.getMainID() == mainid) result.add(order);
        }
        return result;
    }

    public static Transformation_Order getOrderByMainID_ID_SubID(List<Transformation_Order> orders, int mainid, int id, int subid){
        for(Transformation_Order order : orders){
            if(order.getSubID() == subid && order.getID() == id && order.getMainID() == mainid)
                return order;
        }
        return null;
    }

    public static List<Transformation_Order> getOrdersByMainID_ID_Status(List<Transformation_Order> orders, int mainid, int id, Status status){
        List<Transformation_Order> result = new ArrayList<>();
        for(Transformation_Order order : orders){
            if(order.getID() == id && order.getMainID() == mainid && order.getStatus() == status) result.add(order);
        }
        return result;
    }
}

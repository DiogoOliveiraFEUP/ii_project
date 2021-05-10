package Order;

import java.util.ArrayList;
import java.util.List;

public class Unloading_Order extends Order{

    private final String blockType;
    private final String destination;

    public Unloading_Order(String blockType, String destination, int mainID) {

        /* CHANGE ID !!!!!!!!!!! */
        super(1,mainID);

        this.blockType = blockType;
        this.destination = destination;
    }

    public String getBlockType() {
        return blockType;
    }

    public String getDestination() {
        return destination;
    }

    public static List<Integer> getMainIDs(List<Unloading_Order> orders){
        List<Integer> ids = new ArrayList<>();
        for(Unloading_Order order : orders){
            if(ids.contains(order.getMainID())){}
            else {ids.add(order.getMainID());}
        }
        return ids;
    }

    public static List<Unloading_Order> getOrdersByMainID(List<Unloading_Order> orders, int id){
        List<Unloading_Order> result = new ArrayList<>();
        for(Unloading_Order order : orders){
            if(order.getMainID() == id) result.add(order);
        }
        return result;
    }
}

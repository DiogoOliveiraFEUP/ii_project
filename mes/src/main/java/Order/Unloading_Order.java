package Order;

import Factory.Entities.Entity;
import Factory.Factory;
import Transform.PathEdge;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
import java.util.List;

public class Unloading_Order extends Order{

    private final String blockType;
    private final String destination;
    private final boolean priority;

    public Unloading_Order(String blockType, String destination, int mainID, boolean priority) {

        /* CHANGE ID !!!!!!!!!!! */
        super(mainID,mainID,0);

        this.blockType = blockType;
        this.destination = destination;
        this.priority = priority;
        super.setStatus(Status.READY);
        System.out.println();
        GraphPath<Entity, PathEdge> path = (new Factory()).getPath("Wo1","O"+destination.substring(2));

        String pathString ="";
        for(int i = 0; i<=path.getLength();i++){
            pathString +=String.format("%s:",path.getVertexList().get(i));
        }

        super.setPath(pathString);
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

    public boolean isPriority() {
        return priority;
    }
}

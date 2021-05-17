import java.util.List;

public class Scheduler {

    public void schedule(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders){
        // some scheduling algorithm
        for(Transformation_Order order : transfOrders){
            if(order.getStatus() == Order.Status.NEW){
                order.setPath("Wo1:L7:?P=6");
                order.setStatus(Order.Status.READY);
            }
        }
        for(Unloading_Order order : unldOrders){
            if(order.getStatus() == Order.Status.NEW){
                order.setPath("Wo1:L7:?P=6");
                order.setStatus(Order.Status.READY);
            }
        }
    }

}


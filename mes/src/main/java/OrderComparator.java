import Order.Transformation_Order;

import java.util.Comparator;

public class OrderComparator implements Comparator<Transformation_Order> {

    PathPlanner planner = new PathPlanner();
    public OrderComparator() {

    }

    @Override
    public int compare(Transformation_Order o1, Transformation_Order o2) {
        int machiningTime1=planner.getMachiningTime(o1.getInitBlockType(), o1.getFinalBlockType());
        int machiningTime2=planner.getMachiningTime(o2.getInitBlockType(), o2.getFinalBlockType());

        if(machiningTime1>machiningTime2){
            return 1;
        }else{
            if(machiningTime1==machiningTime2){
                if(o1.getMaxDelay()<o2.getMaxDelay()){
                    return 1;
                }
            }
        }
        return -1;
    }
}

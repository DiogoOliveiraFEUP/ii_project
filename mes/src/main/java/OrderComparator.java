import Order.Transformation_Order;

import java.time.Instant;
import java.util.Comparator;

public class OrderComparator implements Comparator<Transformation_Order> {

    PathPlanner planner = new PathPlanner();
    public OrderComparator() {

    }

    @Override
    public int compare(Transformation_Order o1, Transformation_Order o2) {
        int machiningTime1=planner.getMachiningTime(o1.getInitBlockType(), o1.getFinalBlockType());
        int machiningTime2=planner.getMachiningTime(o2.getInitBlockType(), o2.getFinalBlockType());


        long missing1 = o1.getMaxDelay()- (Instant.now().getEpochSecond()-o1.getRealInputTime());
        long missing2 = o2.getMaxDelay()-(Instant.now().getEpochSecond()-o1.getRealInputTime());
        if(missing1 < missing2) {
            return -1;
        }else if(missing1 == missing2) {
            if(o1.getPenalty()<o2.getPenalty()){
                return 1;
            }
        }

        return -1;
    }
}

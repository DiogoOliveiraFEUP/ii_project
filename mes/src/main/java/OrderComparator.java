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
        return Long.compare(missing1,missing2);
    }
}

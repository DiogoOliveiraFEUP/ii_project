import Factory.Factory;
import Order.Transformation_Order;
import Order.Unloading_Order;
import Planning.Timetable;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.GraphPath;

import java.util.List;


public class Scheduler {


    Factory factory;
    PathPlanner pathPlanner;
    Timetable timetable;
    public Scheduler() {
        factory= new Factory();
        timetable = new Timetable();
        pathPlanner = new PathPlanner();
    }


    public void schedule(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders){
        transfOrders.sort(new OrderComparator());
        for(Transformation_Order transformation_order:transfOrders){
            GraphPath<Part, PathEdge> partPath = pathPlanner.getPath(transformation_order.getInitBlockType(),transformation_order.getFinalBlockType());
            timetable.addToTimetable(transformation_order,partPath);

        }

    }

}


import Factory.Entities.Entity;
import Factory.Entities.Rotative;
import Factory.Factory;
import Planning.Timetable;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;


public class Scheduler {


    Factory factory;
    PathPlanner pathPlanner;
    Timetable timetable;
    public Scheduler() {
        factory= new Factory();
    }


    public void schedule(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders){
        transfOrders.sort(new OrderComparator());
        for(Transformation_Order transformation_order:transfOrders){
            GraphPath<Part, PathEdge> partPath = pathPlanner.getPath(transformation_order.getInitBlockType(),transformation_order.getFinalBlockType());

        }
    }

}


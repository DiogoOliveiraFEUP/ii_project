import Entities.Entity;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.List;


public class Scheduler {



    public Scheduler() {
    }

    Graph<Entity, DefaultEdge> g = new DefaultDirectedGraph<>(DefaultEdge.class);

    public void schedule(List<Transformation_Order> transfOrders, List<Unloading_Order> unldOrders){
        // some scheduling algorithm
    }

}


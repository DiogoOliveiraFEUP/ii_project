import Factory.Entities.Entity;
import Factory.Factory;
import Order.Transformation_Order;
import Order.Unloading_Order;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.GraphPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PathPlannerTest {
    PathPlanner pathPlanner = null;

    @BeforeEach
    public void createPathPlanner(){
         pathPlanner = new PathPlanner();

    }

    @Test
    public void PathTest(){
        GraphPath<Part, PathEdge> path =         pathPlanner.getPath("P1","P9");

        for(int i = 0; i<path.getLength();i++){
            System.out.println(path.getVertexList().get(i));
            System.out.println(path.getEdgeList().get(i).getTool());
        }
        System.out.println(path.getVertexList().get(path.getLength()));

        pathPlanner.getPath("P1","P9");
        Factory factory = new Factory();
        List<String> machineList = new ArrayList<>();
        machineList.add("M1");
        machineList.add("M2");
        GraphPath<Entity, PathEdge> fpath =   factory.getPath("Wo1","Wi1",machineList);

        for(int i = 0; i<fpath.getLength();i++){
            System.out.format("%s:",fpath.getVertexList().get(i));
        }
        System.out.format("%s:\n",fpath.getVertexList().get(fpath.getLength()));


        return;
    }


    @Test
    public void schedulerTest(){

        Transformation_Order transformation_order = new Transformation_Order(1,1,"P5","P9",0,0,0);
        Transformation_Order transformation_order1 = new Transformation_Order(1,1,"P5","P9",0,0,0);
        Transformation_Order transformation_order2 = new Transformation_Order(1,1,"P5","P9",0,0,0);
        Transformation_Order transformation_order3 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order4 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order5 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order6 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order7 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order8 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order9 = new Transformation_Order(1,1,"P1","P5",0,0,0);
        Transformation_Order transformation_order10 = new Transformation_Order(1,1,"P1","P5",0,0,0);


        Scheduler scheduler = new Scheduler();
        List<Transformation_Order> transformation_order_list = new ArrayList<>();
        transformation_order_list.add(transformation_order);
        transformation_order_list.add(transformation_order1);
        transformation_order_list.add(transformation_order2);
        transformation_order_list.add(transformation_order3);
        transformation_order_list.add(transformation_order4);
       transformation_order_list.add(transformation_order5);
        transformation_order_list.add(transformation_order6);
        transformation_order_list.add(transformation_order7);
        transformation_order_list.add(transformation_order8);
        transformation_order_list.add(transformation_order9);
        transformation_order_list.add(transformation_order10);



        scheduler.schedule(transformation_order_list, new ArrayList<Unloading_Order>());
        for(Transformation_Order transformation_order_ : transformation_order_list){
            System.out.println(transformation_order_.getPath());
        }
        System.out.println(scheduler.timetable);
    }

}

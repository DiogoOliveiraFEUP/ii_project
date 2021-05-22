import Factory.Entities.Entity;
import Factory.Entities.Machine;
import Factory.Factory;
import Order.Order;
import Order.Transformation_Order;
import Order.Unloading_Order;
import Planning.MachineTimeSlot;
import Planning.Timetable;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.GraphPath;

import java.util.ArrayList;
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


    public void schedule(List<Transformation_Order> transfOrders){
        System.out.println("Scheduling");

        transfOrders.sort(new OrderComparator());
        List<String > machineList;
        for(Transformation_Order transformation_order:transfOrders){
            if(transformation_order.getStatus() == Order.Status.NEW && transformation_order.getStatus()== Order.Status.READY){
            GraphPath<Entity, PathEdge> path = null;
            GraphPath<Part, PathEdge> partPath = pathPlanner.getPath(transformation_order.getInitBlockType(),transformation_order.getFinalBlockType());
            timetable.addToTimetable(transformation_order,partPath);
            machineList = timetable.getSideMachines();
                System.out.println(machineList);
            char side_indicator = machineList.get(0).charAt(1);
            if(side_indicator=='1'||side_indicator=='2'||side_indicator=='3'||side_indicator=='4'){
                path = factory.getPath("Wo1","Wi1",machineList);
            }
            else
                path = factory.getPath("Wo2","Wi2",machineList);


            String pathString ="";
            for(int i = 0; i<path.getLength();i++){
                pathString +=String.format("%s:",path.getVertexList().get(i));
            }
            pathString +=String.format("%s:",path.getVertexList().get(path.getLength()));
                //System.out.println(pathString);
            for (String s:machineList){

                pathString = pathString.replace(s.substring(0,2),s);
            }
            pathString+="Wh:";
            transformation_order.setStatus(Order.Order.Status.READY);
            transformation_order.setPath(pathString);
            }
        }
        System.out.println("Scheduled");
    }


}


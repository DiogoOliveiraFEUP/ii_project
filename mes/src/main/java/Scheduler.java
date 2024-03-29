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
//        System.out.println("Scheduling");
        //System.out.println(transfOrders);
        for(Transformation_Order transformation_order:transfOrders) {
            if(transformation_order.getStatus() == Order.Status.RUNNING||transformation_order.getStatus()==Order.Status.COMPLETED){
                if(transformation_order.getTimetable()!= null) timetable = transformation_order.getTimetable();
            }
        }
        transfOrders.sort((o1, o2) ->
                Integer.compare(o1.getSubID(), o2.getSubID()));
        transfOrders.sort((o1, o2) ->
                Integer.compare(o1.getPenalty(), o2.getPenalty()));
        transfOrders.sort(new OrderComparator());


        for(Transformation_Order transformation_order:transfOrders){
            System.out.println(transformation_order);
        }
        List<String > machineList;
        for(Transformation_Order transformation_order:transfOrders){
            if(transformation_order.getStatus() == Order.Status.NEW || transformation_order.getStatus()== Order.Status.READY){
            GraphPath<Entity, PathEdge> path = null;
            GraphPath<Part, PathEdge> partPath = pathPlanner.getPath(transformation_order.getInitBlockType(),transformation_order.getFinalBlockType());
            timetable.addToTimetable(transformation_order,partPath);
            machineList = timetable.getSideMachines();
  //              System.out.println(machineList);
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
                for(int i = machineList.size()-1;i>=0;i--){
                    pathString = pathString.replace(machineList.get(i).substring(0,2),machineList.get(i));

                }
            pathString+="Wh:";
            transformation_order.setStartTime(timetable.getOrderStartingTime());
            transformation_order.setEndTime(timetable.getOrderEndingTime());
            transformation_order.setStatus(Order.Status.READY);
            transformation_order.setPath(pathString);
            transformation_order.setTimetable(timetable);
                //System.out.println(timetable.getOrderEndingTime());
            }
        }
        //System.out.println("Scheduled");
    }


}


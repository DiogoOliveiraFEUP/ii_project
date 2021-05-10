package Planning;

import Factory.Entities.Entity;
import Factory.Entities.Machine;
import Order.Transformation_Order;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.GraphPath;

import javax.tools.Tool;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Timetable {
    //Map<Entity, Map> timetable = new HashMap<>();
    HashMap<Entity,LinkedList<MachineTimeSlot>> timetable = new HashMap<>();

    public Timetable() {
        addEntity(new Machine("M1"));
        addEntity(new Machine("M2"));
        addEntity(new Machine("M3"));
        addEntity(new Machine("M4"));
        addEntity(new Machine("M5"));
        addEntity(new Machine("M6"));
        addEntity(new Machine("M7"));
        addEntity(new Machine("M8"));
        for(Entity entity:timetable.keySet()){
            timetable.put(entity,new LinkedList<MachineTimeSlot>());
        }

    }

    public void addEntity(Entity entity){
        timetable.put(entity,new LinkedList<MachineTimeSlot>());
    }

    public void setSlotOccupied(Entity entity, MachineTimeSlot machineTimeSlot){
        timetable.get(entity).add(machineTimeSlot);
    }

    public boolean isSlotOccupied(Entity entity ){
        return false;
    }

    public Machine getMachineWithTool(String tool){
        for (int i = 1; i < 9; i++) {
            Entity entity = new Machine("M"+i);
            if(timetable.get(entity).isEmpty()||timetable.get(entity).getLast().tool.equals(tool) ){
                return (Machine) entity;
            }
        }
        return new Machine("M9");
    }

    public int getLastEndingTime(Machine machine){
        if(timetable.get(machine).isEmpty())
            return 0;
        else
            return timetable.get(machine).getLast().getEnding_Time();
    }

    public boolean addToTimetable(Transformation_Order transformation_order,GraphPath<Part, PathEdge> orderPath){
        boolean isFirst = true;
        int lastEndingTime=0;
        int newStartTime = 0;
        for (PathEdge pathEdge:orderPath.getEdgeList()) {
            Machine machine = getMachineWithTool(pathEdge.getTool());
            MachineTimeSlot machineTimeSlot = null;
            if(isFirst){
                 machineTimeSlot = new MachineTimeSlot(transformation_order,pathEdge.getTool(),getLastEndingTime(machine),getLastEndingTime(machine)+ pathEdge.getTime());
                 isFirst=false;
                 lastEndingTime = getLastEndingTime(machine)+ pathEdge.getTime();
            }else{
                newStartTime=getLastEndingTime(machine);
                newStartTime = Math.max(newStartTime, lastEndingTime);
                lastEndingTime = newStartTime+ pathEdge.getTime();
                machineTimeSlot = new MachineTimeSlot(transformation_order,pathEdge.getTool(),newStartTime,newStartTime+ pathEdge.getTime());
            }
            timetable.get(machine).addLast(machineTimeSlot);
        }
        return true;
    }

    @Override
    public String toString() {
        String returnString = "";
        for(Entity entity:timetable.keySet()){
            returnString+= "Machine " + entity + "";
            for(MachineTimeSlot machineTimeSlot:timetable.get(entity)){
                returnString+= " S:" + machineTimeSlot.starting_Time +" T:"+ machineTimeSlot.tool+ " E:" + machineTimeSlot.ending_Time;
            }
            returnString +="\n";
        }
        return returnString;
    }
}

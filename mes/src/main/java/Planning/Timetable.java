package Planning;

import Factory.Entities.Entity;
import Factory.Entities.Machine;
import Order.Transformation_Order;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.GraphPath;

import javax.tools.Tool;
import java.sql.Time;
import java.util.*;

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

    public Timetable(HashMap<Entity,LinkedList<MachineTimeSlot>> timetable){
        this.timetable = new HashMap<Entity, LinkedList<MachineTimeSlot>>();
        for(Entity entity: timetable.keySet()){
            this.timetable.put(entity,new LinkedList<MachineTimeSlot>());
            for(MachineTimeSlot machineTimeSlot:timetable.get(entity)){
                this.timetable.get(entity).add(machineTimeSlot);
            }
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

    public int getEndingTime(){
        int maxEndingTime = 0;
        for(Entity entity:timetable.keySet()){
            if(!timetable.get(entity).isEmpty())
                maxEndingTime = Math.max(timetable.get(entity).getLast().getEnding_Time(),maxEndingTime);
        }
        return maxEndingTime;
    }

    public Machine getMachineWithTool(String tool, int side){
        for (int i = 4*side + 1; i < 4*side +5; i++) {
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

    public List<String> addToTimetable(Transformation_Order transformation_order,GraphPath<Part, PathEdge> orderPath){
        boolean isFirst = true;
        int lastEndingTime=0;
        int newStartTime = 0;
        List<String> sideAMachines = new ArrayList<>();
        List<String> sideBMachines = new ArrayList<>();

        Timetable sideA = new Timetable(timetable);
        for (PathEdge pathEdge:orderPath.getEdgeList()) {

            Machine machine = sideA.getMachineWithTool(pathEdge.getTool(),0);
            sideAMachines.add(machine.toString()+"#"+pathEdge.getTool().substring(1,2)+'|'+pathEdge.getTime());
            MachineTimeSlot machineTimeSlot = null;
            if(isFirst){
                 machineTimeSlot = new MachineTimeSlot(transformation_order,pathEdge.getTool(),sideA.getLastEndingTime(machine),sideA.getLastEndingTime(machine)+ pathEdge.getTime());
                 isFirst=false;
                 lastEndingTime = sideA.getLastEndingTime(machine)+ pathEdge.getTime();
            }else{
                newStartTime=sideA.getLastEndingTime(machine);
                newStartTime = Math.max(newStartTime, lastEndingTime);
                lastEndingTime = newStartTime+ pathEdge.getTime();
                machineTimeSlot = new MachineTimeSlot(transformation_order,pathEdge.getTool(),newStartTime,newStartTime+ pathEdge.getTime());
            }
            sideA.timetable.get(machine).addLast(machineTimeSlot);
        }
        Timetable sideB = new Timetable(timetable);
         isFirst = true;
         lastEndingTime=0;
         newStartTime = 0;
        for (PathEdge pathEdge:orderPath.getEdgeList()) {

            Machine machine = sideB.getMachineWithTool(pathEdge.getTool(),1);
            sideBMachines.add(machine.toString()+"#"+pathEdge.getTool().substring(1,2)+'|'+pathEdge.getTime());

            MachineTimeSlot machineTimeSlot = null;
            if(isFirst){
                machineTimeSlot = new MachineTimeSlot(transformation_order,pathEdge.getTool(),sideB.getLastEndingTime(machine),sideB.getLastEndingTime(machine)+ pathEdge.getTime());
                isFirst=false;
                lastEndingTime = sideB.getLastEndingTime(machine)+ pathEdge.getTime();
            }else{
                newStartTime=sideB.getLastEndingTime(machine);
                newStartTime = Math.max(newStartTime, lastEndingTime);
                lastEndingTime = newStartTime+ pathEdge.getTime();
                machineTimeSlot = new MachineTimeSlot(transformation_order,pathEdge.getTool(),newStartTime,newStartTime+ pathEdge.getTime());
            }
            sideB.timetable.get(machine).addLast(machineTimeSlot);
        }
        if(sideA.getEndingTime()>sideB.getEndingTime()){
            timetable=sideB.timetable;
            return sideBMachines;
        }
        else{
            timetable= sideA.timetable;
            return sideAMachines;
        }
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

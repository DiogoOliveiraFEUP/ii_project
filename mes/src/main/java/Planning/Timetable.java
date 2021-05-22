package Planning;

import Factory.Entities.Entity;
import Factory.Entities.Machine;
import Order.Transformation_Order;
import Transform.Part;
import Transform.PathEdge;
import org.bouncycastle.tsp.TSPUtil;
import org.jgrapht.GraphPath;

import javax.tools.Tool;
import java.sql.SQLOutput;
import java.sql.Time;
import java.util.*;

public class Timetable {
    //Map<Entity, Map> timetable = new HashMap<>();
    HashMap<Entity,LinkedList<MachineTimeSlot>> timetable = new HashMap<>();
    List<String> sideMachines=new ArrayList<>();

    public HashMap<Entity, LinkedList<MachineTimeSlot>> getTimetable() {
        return timetable;
    }

    public List<String> getSideMachines() {
        return sideMachines;
    }

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

    public int getEndingTime(int side){
        int maxEndingTime = 0;
        for(int i=1;i<=4*side+4;i++){
            if(!timetable.get(new Machine("M"+i)).isEmpty())
                maxEndingTime += timetable.get(new Machine("M"+i)).getLast().getEnding_Time()-timetable.get(new Machine("M"+i)).getFirst().starting_Time;
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

    public int getMachiningEndTime(Machine machine, String tool, int tool_time){
        if(timetable.get(machine).isEmpty()){
            return 30 + tool_time;
        }else if(timetable.get(machine).getLast().getTool().equals(tool)){
            return tool_time;
        }else return 30+tool_time;

    }

    public int getLastEndingTime(Machine machine){
        if(timetable.get(machine).isEmpty())
            return 0;
        else
            return timetable.get(machine).getLast().getEnding_Time();
    }

    public HashMap<Entity,LinkedList<MachineTimeSlot>> iterate(List<String>sideMachines,int level,int side, int highest_machine, int LastEndingTime,Transformation_Order transformation_order,GraphPath<Part, PathEdge> orderPath){

        PathEdge pathEdge = orderPath.getEdgeList().get(level);
        System.out.println(level);
        //System.out.println("Highest machine:"+highest_machine +" -> Tool" + pathEdge.getTool());

        List<Timetable> timetables = new ArrayList<>();
        if(level == orderPath.getEdgeList().size()-1) {
            for (int i = highest_machine; i < 4; i++) {
                //System.out.println(i);
                Machine machine = new Machine("M" + (4 * side + i+1));

                int LatestStartingTime = timetable.get(machine).isEmpty()?0:Math.max(timetable.get(machine).getLast().getEnding_Time(),LastEndingTime);
                int LatestEndingTime=0;
                LastEndingTime=timetable.get(machine).isEmpty()?LastEndingTime:Math.max(timetable.get(machine).getLast().getEnding_Time(),LastEndingTime);

                if(getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime())>30){

                    LatestStartingTime = timetable.get(machine).isEmpty()?0:timetable.get(machine).getLast().getEnding_Time();
                    if(LastEndingTime<=LatestStartingTime+30){
                        LatestEndingTime = LastEndingTime + getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime());
                    }else{
                        LatestEndingTime = LastEndingTime+getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime())-30;
                    }
                    System.out.println(getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime())-30);
                    System.out.println(i);
                    System.out.println(LastEndingTime);
                    System.out.println(LatestStartingTime);
                }
                else{
                    LatestEndingTime=LatestStartingTime+getMachiningEndTime(machine, pathEdge.getTool(), pathEdge.getTime());
                }
                Timetable buffer = new Timetable(timetable);
                buffer.sideMachines.addAll(sideMachines);
                buffer.sideMachines.add(machine+":M#"+pathEdge.getTool()+"|"+pathEdge.getTime());
                MachineTimeSlot machineTimeSlot = new MachineTimeSlot(transformation_order, pathEdge.getTool(),LatestStartingTime,LatestEndingTime);
                buffer.timetable.get(machine).addLast(machineTimeSlot);
                timetables.add(buffer);
                //System.out.println(buffer.toString());


            }
            Timetable best = timetables.get(0);
            for (Timetable buffer : timetables) {
                if (buffer.getEndingTime() < best.getEndingTime()) {
                    best = buffer;
                }
            }
            //System.out.println("Winner\n"+ best);
            sideMachines.clear();
            sideMachines.addAll( best.sideMachines);
            //System.out.println(best);
            //System.out.println(sideMachines);
            this.timetable = best.timetable;
            return timetable;
        }else{
            for (int i = highest_machine; i < 4; i++) {
                Machine machine = new Machine("M" + (4 * side + i + 1));
                Timetable buffer = new Timetable(timetable);
                int LatestStartingTime = timetable.get(machine).isEmpty()?0:Math.max(timetable.get(machine).getLast().getEnding_Time(),LastEndingTime);
                int LatestEndingTime=0;
                LastEndingTime=timetable.get(machine).isEmpty()?LastEndingTime:Math.max(timetable.get(machine).getLast().getEnding_Time(),LastEndingTime);
                if(getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime())>30){

                    LatestStartingTime = timetable.get(machine).isEmpty()?0:timetable.get(machine).getLast().getEnding_Time();
                    if(LastEndingTime<=LatestStartingTime+30){
                        LatestEndingTime = LastEndingTime + getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime());
                    }else{
                        LatestEndingTime = LastEndingTime+getMachiningEndTime(machine,pathEdge.getTool(), pathEdge.getTime())-30;
                    }
                }
                else{
                    LatestEndingTime=LatestStartingTime+getMachiningEndTime(machine, pathEdge.getTool(), pathEdge.getTime());
                }
                buffer.sideMachines.addAll(sideMachines);
                buffer.sideMachines.add(machine+":M#"+pathEdge.getTool()+"|"+pathEdge.getTime());

                MachineTimeSlot machineTimeSlot = new MachineTimeSlot(transformation_order, pathEdge.getTool(),LatestStartingTime,LatestEndingTime);
                buffer.timetable.get(machine).addLast(machineTimeSlot);
                buffer.timetable = buffer.iterate(buffer.sideMachines,level + 1, side, i+1==4?i:i+1,LatestEndingTime, transformation_order, orderPath);
                timetables.add(buffer);
                //System.out.println("Level" + level + "\n" + buffer);
            }
                Timetable best = timetables.get(0);
                for (Timetable buffer : timetables) {
                    if (buffer.getEndingTime() < best.getEndingTime()) {
                        best = buffer;
                    }
                }
                //System.out.println("Winner\n" + best);
                sideMachines.clear();
                sideMachines.addAll( best.sideMachines);
            //System.out.println(sideMachines);

            this.timetable = best.timetable;
                return timetable;


        }
    }






    public int getBestEndingTime( List<String> sideMachines,int side, Transformation_Order transformation_order,GraphPath<Part, PathEdge> orderPath){
        int lastEndingTime=0;
        int newStartTime = 0;
        boolean isFirst = true;
        timetable = iterate(sideMachines,0,side,0,0,transformation_order,orderPath);
        //System.out.println(this);


        return getEndingTime();
    }

    public void addToTimetable(Transformation_Order transformation_order,GraphPath<Part, PathEdge> orderPath){
        List<String> sideAMachines = new ArrayList<>();
        List<String> sideBMachines = new ArrayList<>();

        Timetable sideA = new Timetable(timetable);
        Timetable sideB = new Timetable(timetable);
        if(sideA.getBestEndingTime(sideAMachines,0,transformation_order,orderPath)>sideB.getBestEndingTime(sideBMachines,1,transformation_order,orderPath)){
            timetable=sideB.timetable;
            //System.out.println(this);

            sideMachines.clear();
            sideMachines.addAll(sideBMachines);

        }
        else{
            timetable= sideA.timetable;
            //System.out.println(this);

            sideMachines.clear();
            sideMachines.addAll(sideAMachines);

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

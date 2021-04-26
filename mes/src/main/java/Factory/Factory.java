package Factory;

import Factory.Entities.*;
import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Factory{
    DefaultDirectedGraph<Entity, PathEdge> factoryModel = new DefaultDirectedGraph<Entity, PathEdge>(PathEdge.class);
    HashMap<String,Machine> machineList = new HashMap<String,Machine>();
    HashMap<String,Roller> rollerList = new HashMap<String,Roller>();
    HashMap<String,Rotative> rotativeList = new HashMap<String,Rotative>();
    HashMap<String,Linear> linearList = new HashMap<String,Linear>();


    //Machine Instantiation
    Machine M1 = new Machine("M1");
    Machine M2 = new Machine("M2");
    Machine M3 = new Machine("M3");
    Machine M4 = new Machine("M4");
    Machine M5 = new Machine("M5");
    Machine M6 = new Machine("M6");
    Machine M7 = new Machine("M7");
    Machine M8 = new Machine("M8");

    public Factory() {

        //Machine Instantiation
        Machine M1 = new Machine("M1");
        Machine M2 = new Machine("M2");
        Machine M3 = new Machine("M3");
        Machine M4 = new Machine("M4");
        Machine M5 = new Machine("M5");
        Machine M6 = new Machine("M6");
        Machine M7 = new Machine("M7");
        Machine M8 = new Machine("M8");

        machineList.put("M1",M1);
        machineList.put("M2",M2);
        machineList.put("M3",M3);
        machineList.put("M4",M4);
        machineList.put("M5",M5);
        machineList.put("M6",M6);
        machineList.put("M7",M7);
        machineList.put("M8",M8);

        for(Machine machine: machineList.values()){
            factoryModel.addVertex(machine);
        }


    }
}
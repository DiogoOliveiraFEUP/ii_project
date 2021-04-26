package Factory;

import Factory.Entities.*;
import Transform.PathEdge;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.HashMap;

public class Factory{
    DefaultDirectedGraph<Entity, PathEdge> factoryModel = new DefaultDirectedGraph<Entity, PathEdge>(PathEdge.class);
    HashMap<String,Machine> machineList = new HashMap<String,Machine>();
    HashMap<String,Roller> rollerList = new HashMap<String,Roller>();
    HashMap<String,Rotative> rotativeList = new HashMap<String,Rotative>();
    HashMap<String,Linear> linearList = new HashMap<String,Linear>();
    HashMap<String,WarehouseIn> warehouseInList = new HashMap<String,WarehouseIn>();
    HashMap<String,WarehouseOut> warehouseOutList = new HashMap<String,WarehouseOut>();
    HashMap<String, Entity> factoryList = new HashMap<String, Entity>();

    public Factory() {

        //Machine Instantiation
        for(int i=0; i < 8;i++){
            machineList.put("M%d".formatted(i+1),new Machine("M%d".formatted(i+ 1)));
        }

        for(Machine machine: machineList.values()){
            factoryModel.addVertex(machine);
        }


        //Rotative instantiation
        for(int i=0; i < 16;i++){
            rotativeList.put("R%d".formatted(i+1),new Rotative("R%d".formatted(i+ 1)));
        }

        for(Rotative rotative: rotativeList.values()){
            factoryModel.addVertex(rotative);
        }

        //Linear Mat instantiation
        for(int i=0; i < 10;i++){
            linearList.put("L%d".formatted(i+1),new Linear("L%d".formatted(i+ 1)));
        }

        for(Linear linear: linearList.values()){
            factoryModel.addVertex(linear);
        }

        //WarehouseIn instantiation
        warehouseInList.put("Wi1", new WarehouseIn("Wi1"));
        warehouseInList.put("Wi2", new WarehouseIn("Wi2"));

        for(WarehouseIn warehouseIn: warehouseInList.values()){
            factoryModel.addVertex(warehouseIn);
        }

        //WarehouseOut instantiation
        warehouseInList.put("Wo1", new WarehouseIn("Wo1"));
        warehouseInList.put("Wo2", new WarehouseIn("Wo2"));

        for(WarehouseOut warehouseOut: warehouseOutList.values()){
            factoryModel.addVertex(warehouseOut);
        }

        //Roller Mat instantiation
        for(int i=0; i < 3;i++){
            rollerList.put("O%d".formatted(i+1),new Roller("O%d".formatted(i+ 1)));
        }

        for(Roller roller: rollerList.values()){
            factoryModel.addVertex(roller);
        }

        factoryList.putAll(linearList);
        factoryList.putAll(rollerList);
        factoryList.putAll(rotativeList);
        factoryList.putAll(warehouseInList);
        factoryList.putAll(warehouseOutList);
        factoryList.putAll(machineList);



        //Graph Edges
        factoryModel.addEdge(factoryList.get("Wo2"),factoryList.get("L8"));
        factoryModel.addEdge(factoryList.get("L8"),factoryList.get("R10"));
        factoryModel.addEdge(factoryList.get("R10"),factoryList.get("R11"));
        factoryModel.addEdge(factoryList.get("R11"),factoryList.get("L9"));
        factoryModel.addEdge(factoryList.get("R11"),factoryList.get("R12"));





    }
}
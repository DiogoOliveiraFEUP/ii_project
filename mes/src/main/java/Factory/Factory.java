package Factory;

import Factory.Entities.*;
import Transform.Part;
import Transform.PathEdge;
import kotlin.Pair;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
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
    HashMap<String,Pusher> pusherList = new HashMap<String,Pusher>();
    HashMap<Pair<Entity,Entity>,GraphPath<Entity, PathEdge>> factoryIndexedPaths = new HashMap<>();


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
        warehouseOutList.put("Wo1", new WarehouseOut("Wo1"));
        warehouseOutList.put("Wo2", new WarehouseOut("Wo2"));

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

        //PusherMat instantiation
        for(int i=0; i < 3;i++){
            pusherList.put("P%d".formatted(i+1),new Pusher("P%d".formatted(i+ 1)));
        }

        for(Pusher pusher: pusherList.values()){
            factoryModel.addVertex(pusher);
        }


        factoryList.putAll(linearList);
        factoryList.putAll(rollerList);
        factoryList.putAll(rotativeList);
        factoryList.putAll(warehouseInList);
        factoryList.putAll(warehouseOutList);
        factoryList.putAll(machineList);
        factoryList.putAll(pusherList);



        //Graph Edges - RightSide
        factoryModel.addEdge(factoryList.get("Wo2"),factoryList.get("L8"));
        factoryModel.addEdge(factoryList.get("L8"),factoryList.get("R10"));
        factoryModel.addEdge(factoryList.get("R10"),factoryList.get("R11"));
        factoryModel.addEdge(factoryList.get("R11"),factoryList.get("L9"));
        factoryModel.addEdge(factoryList.get("R11"),factoryList.get("R12"));
        factoryModel.addEdge(factoryList.get("R12"),factoryList.get("M5"));
        factoryModel.addEdge(factoryList.get("R12"),factoryList.get("R13"));
        factoryModel.addEdge(factoryList.get("M5"),factoryList.get("R12"));
        factoryModel.addEdge(factoryList.get("R13"),factoryList.get("M6"));
        factoryModel.addEdge(factoryList.get("R13"),factoryList.get("R14"));
        factoryModel.addEdge(factoryList.get("M6"),factoryList.get("R13"));
        factoryModel.addEdge(factoryList.get("R14"),factoryList.get("M7"));
        factoryModel.addEdge(factoryList.get("R14"),factoryList.get("R15"));
        factoryModel.addEdge(factoryList.get("M7"),factoryList.get("R14"));
        factoryModel.addEdge(factoryList.get("R15"),factoryList.get("M8"));
        factoryModel.addEdge(factoryList.get("R15"),factoryList.get("R16"));
        factoryModel.addEdge(factoryList.get("M8"),factoryList.get("R15"));
        factoryModel.addEdge(factoryList.get("R16"),factoryList.get("L10"));
        factoryModel.addEdge(factoryList.get("L10"),factoryList.get("R16"));
        factoryModel.addEdge(factoryList.get("R16"),factoryList.get("Wi2"));

        factoryModel.addEdge(factoryList.get("Wo1"),factoryList.get("L7"));
        factoryModel.addEdge(factoryList.get("L7"),factoryList.get("R3"));
        factoryModel.addEdge(factoryList.get("R3"),factoryList.get("R2"));
        factoryModel.addEdge(factoryList.get("R2"),factoryList.get("L2"));
        factoryModel.addEdge(factoryList.get("R2"),factoryList.get("R6"));
        factoryModel.addEdge(factoryList.get("R6"),factoryList.get("M1"));
        factoryModel.addEdge(factoryList.get("R6"),factoryList.get("R7"));
        factoryModel.addEdge(factoryList.get("M1"),factoryList.get("R6"));
        factoryModel.addEdge(factoryList.get("R7"),factoryList.get("M2"));
        factoryModel.addEdge(factoryList.get("R7"),factoryList.get("R8"));
        factoryModel.addEdge(factoryList.get("M2"),factoryList.get("R7"));
        factoryModel.addEdge(factoryList.get("R8"),factoryList.get("M3"));
        factoryModel.addEdge(factoryList.get("R8"),factoryList.get("R9"));
        factoryModel.addEdge(factoryList.get("M3"),factoryList.get("R8"));
        factoryModel.addEdge(factoryList.get("R9"),factoryList.get("M4"));
        factoryModel.addEdge(factoryList.get("R9"),factoryList.get("R5"));
        factoryModel.addEdge(factoryList.get("M4"),factoryList.get("R9"));
        factoryModel.addEdge(factoryList.get("R5"),factoryList.get("L4"));
        factoryModel.addEdge(factoryList.get("L4"),factoryList.get("R5"));
        factoryModel.addEdge(factoryList.get("R5"),factoryList.get("Wi1"));

        factoryModel.addEdge(factoryList.get("L2"),factoryList.get("R1"));
        factoryModel.addEdge(factoryList.get("R1"),factoryList.get("L6"));
        factoryModel.addEdge(factoryList.get("L6"),factoryList.get("P1"));
        factoryModel.addEdge(factoryList.get("P1"),factoryList.get("O1"));
        factoryModel.addEdge(factoryList.get("P1"),factoryList.get("P2"));
        factoryModel.addEdge(factoryList.get("P2"),factoryList.get("O2"));
        factoryModel.addEdge(factoryList.get("P2"),factoryList.get("P3"));
        factoryModel.addEdge(factoryList.get("P3"),factoryList.get("L5"));
        factoryModel.addEdge(factoryList.get("P3"),factoryList.get("O3"));
        factoryModel.addEdge(factoryList.get("L5"),factoryList.get("R4"));

        AllDirectedPaths<Entity,PathEdge> factoryPaths = new AllDirectedPaths<>(factoryModel);
        for(Entity eFrom:factoryModel.vertexSet()){
            for(Entity eTo:factoryModel.vertexSet()){
                try{
                    GraphPath<Entity,PathEdge> path = factoryPaths.getAllPaths(eFrom,eTo,true,null).get(0);
                    factoryIndexedPaths.put(new Pair<>(eFrom,eTo), path);

                } catch (Exception e) {

                }
            }
        }

    }

    public GraphPath<Entity, PathEdge> getPath(String entity1, String entity2){
        Entity p1 = factoryList.get(entity1);
        Entity p2 = factoryList.get(entity2);
        return factoryIndexedPaths.get(new Pair<>(p1,p2));
    }



}
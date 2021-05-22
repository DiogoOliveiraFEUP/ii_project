import Transform.Part;
import Transform.PathEdge;
import kotlin.Pair;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.GraphWalk;

import java.nio.file.Path;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class PathPlanner {
    DefaultDirectedGraph<Part, PathEdge> p = new DefaultDirectedGraph<Part, PathEdge>(PathEdge.class);
    HashMap<Pair<Part,Part>,GraphPath<Part, PathEdge>> indexedPaths = new HashMap<>();
    HashMap<String,Part> partList = new HashMap<String,Part>();
    public PathPlanner() {

        //Parts instantiation
        Part P1 = new Part("P1");
        Part P2 = new Part("P2");
        Part P3 = new Part("P3");
        Part P4 = new Part("P4");
        Part P5 = new Part("P5");
        Part P6 = new Part("P6");
        Part P7 = new Part("P7");
        Part P8 = new Part("P8");
        Part P9 = new Part("P9");
        partList.put("P1",P1);
        partList.put("P2",P2);
        partList.put("P3",P3);
        partList.put("P4",P4);
        partList.put("P5",P5);
        partList.put("P6",P6);
        partList.put("P7",P7);
        partList.put("P8",P8);
        partList.put("P9",P9);


        //Vertexes
        for(Part part:partList.values()){
            p.addVertex(part);
        }

        //Edges
        p.addEdge(P1,P2,new PathEdge(15, "T1"));
        p.addEdge(P4,P5,new PathEdge(15, "T1"));
        p.addEdge(P6,P8,new PathEdge(15, "T1"));
        p.addEdge(P2,P3,new PathEdge(15, "T2"));
        p.addEdge(P5,P6,new PathEdge(30, "T2"));
        p.addEdge(P3,P4,new PathEdge(15, "T3"));
        p.addEdge(P5,P9,new PathEdge(30, "T3"));
        p.addEdge(P6,P7,new PathEdge(30, "T3"));

        AllDirectedPaths<Part,PathEdge> paths = new AllDirectedPaths<>(p);
        for(Part pFrom:p.vertexSet()){
            for(Part pTo:p.vertexSet()){
                try{
                    GraphPath<Part,PathEdge> path = paths.getAllPaths(pFrom,pTo,true,null).get(0);
                    indexedPaths.put(new Pair<>(pFrom,pTo), path);

                } catch (Exception e) {

                }
            }
        }
    }

    public GraphPath<Part, PathEdge> getPath(String part1, String part2){
        Part p1 = partList.get(part1);
        Part p2 = partList.get(part2);
        return indexedPaths.get(new Pair<>(p1,p2));
    }

    public int getMachiningTime(String part1, String part2){
        Part p1 = partList.get(part1);
        Part p2 = partList.get(part2);
        int time_sum = 0;
        for(PathEdge pathEdge:indexedPaths.get(new Pair<>(p1,p2)).getEdgeList()){
            time_sum+=pathEdge.getTime();
        };
        return time_sum;
    }


}

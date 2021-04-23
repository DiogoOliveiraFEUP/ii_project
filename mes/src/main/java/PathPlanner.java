import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.GraphWalk;

import java.nio.file.Path;
import java.util.List;


public class PathPlanner {
    DefaultDirectedGraph<Part, PathEdge> p = new DefaultDirectedGraph<Part, PathEdge>(PathEdge.class);
    List<GraphPath<Part, PathEdge>> indexedPaths;
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

        //Vertexes
        p.addVertex(P1);
        p.addVertex(P2);
        p.addVertex(P3);
        p.addVertex(P4);
        p.addVertex(P5);
        p.addVertex(P6);
        p.addVertex(P7);
        p.addVertex(P8);
        p.addVertex(P9);

        //Edges
        p.addEdge(P1,P2,new PathEdge(15, "T1"));
        p.addEdge(P4,P5,new PathEdge(15, "T1"));
        p.addEdge(P6,P8,new PathEdge(15, "T1"));
        p.addEdge(P2,P3,new PathEdge(15, "T2"));
        p.addEdge(P5,P6,new PathEdge(30, "T2"));
        p.addEdge(P3,P4,new PathEdge(15, "T3"));
        p.addEdge(P5,P9,new PathEdge(30, "T3"));
        p.addEdge(P6,P9,new PathEdge(30, "T3"));

        AllDirectedPaths<Part,PathEdge> paths = new AllDirectedPaths<>(p);
        indexedPaths = paths.getAllPaths(p.vertexSet(),p.vertexSet(),true,null);
    }

}

import Transform.Part;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;


public class PathPlanner {
    DefaultDirectedGraph<Part,DefaultEdge> p = new DefaultDirectedGraph<Part,DefaultEdge>(DefaultEdge.class);

    public PathPlanner() {
        Part P1 = new Part("P1");
        Part P2 = new Part("P2");
        Part P3 = new Part("P3");
        Part P4 = new Part("P4");
        Part P5 = new Part("P5");
        Part P6 = new Part("P6");
        Part P7 = new Part("P7");
        Part P8 = new Part("P8");
        Part P9 = new Part("P9");
        p.addVertex(P1);
        p.addVertex(P2);
        p.addVertex(P3);
        p.addVertex(P4);
        p.addVertex(P5);
        p.addVertex(P6);
        p.addVertex(P7);
        p.addVertex(P8);
        p.addVertex(P9);

        p.addEdge(P1,P2);
        p.addEdge(P4,P5);
        p.addEdge(P6,P8);
        p.addEdge(P2,P3);
        p.addEdge(P5,P6);
        p.addEdge(P3,P4);
        p.addEdge(P5,P9);
        p.addEdge(P6,P9);

        AllDirectedPaths<Part,DefaultEdge> paths = new AllDirectedPaths<>(p);
        System.out.println(paths.getAllPaths(P1,P9,false,10));




    }
}

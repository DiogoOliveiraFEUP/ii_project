import Transform.Part;
import Transform.PathEdge;
import org.jgrapht.GraphPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PathPlannerTest {
    PathPlanner pathPlanner = null;

    @BeforeEach
    public void createPathPlanner(){
         pathPlanner = new PathPlanner();

    }

    @Test
    public void PathTest(){
        GraphPath<Part, PathEdge> path =         pathPlanner.getPath("P1","P9");

        for(int i = 0; i<path.getLength();i++){
            System.out.println(path.getVertexList().get(i));
            System.out.println(path.getEdgeList().get(i).getTool());
        }
        System.out.println(path.getVertexList().get(path.getLength()));

        pathPlanner.getPath("P1","P9");

        return;
    }

}

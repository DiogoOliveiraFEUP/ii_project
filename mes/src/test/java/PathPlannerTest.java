import Factory.Entities.Entity;
import Factory.Factory;
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
        Factory factory = new Factory();
        GraphPath<Entity, PathEdge> fpath =   factory.getPath("Wo1","Wi1");

        for(int i = 0; i<fpath.getLength();i++){
            System.out.format("%s:",fpath.getVertexList().get(i));
        }
        System.out.format("%s:\n",fpath.getVertexList().get(fpath.getLength()));


        return;
    }

}

package Transform;

import org.jgrapht.graph.DefaultEdge;

public class PathEdge extends DefaultEdge {
    int time;
    String Tool;

    public PathEdge(int time, String tool) {

        this.time = time;
        Tool = tool;
    }
}

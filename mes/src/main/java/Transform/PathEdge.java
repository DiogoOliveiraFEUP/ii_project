package Transform;

import org.jgrapht.graph.DefaultEdge;

public class PathEdge extends DefaultEdge {
    int time;
    String Tool;


    public PathEdge(int time, String tool) {
        this.time = time;
        Tool = tool;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getTool() {
        return Tool;
    }

    public void setTool(String tool) {
        Tool = tool;
    }
}

package Factory.Entities;

public class Machine extends Entity{
    int Tool;

    public Machine(String name, int tool) {
        super(name);
        Tool = tool;
    }

    public Machine(String name) {
        super(name);
    }
}

package Planning;

import Order.Transformation_Order;

public class MachineTimeSlot {
    Transformation_Order order;
    String tool;
    long starting_Time;
    long ending_Time;

    public MachineTimeSlot(Transformation_Order order, String tool, long starting_Time, long ending_Time) {
        this.order = order;
        this.tool = tool;
        this.starting_Time = starting_Time;
        this.ending_Time = ending_Time;
    }

    public Transformation_Order getOrder() {
        return order;
    }

    public void setOrder(Transformation_Order order) {
        this.order = order;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public long getStarting_Time() {
        return starting_Time;
    }

    public void setStarting_Time(long starting_Time) {
        this.starting_Time = starting_Time;
    }

    public long getEnding_Time() {
        return ending_Time;
    }

    public void setEnding_Time(long ending_Time) {
        this.ending_Time = ending_Time;
    }
}

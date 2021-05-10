package Planning;

import Order.Transformation_Order;

public class MachineTimeSlot {
    Transformation_Order order;
    String tool;
    int starting_Time;
    int ending_Time;

    public MachineTimeSlot(Transformation_Order order, String tool, int starting_Time, int ending_Time) {
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

    public int getStarting_Time() {
        return starting_Time;
    }

    public void setStarting_Time(int starting_Time) {
        this.starting_Time = starting_Time;
    }

    public int getEnding_Time() {
        return ending_Time;
    }

    public void setEnding_Time(int ending_Time) {
        this.ending_Time = ending_Time;
    }
}

package Order;

public class Order {

    private final int mainID;
    private final int ID;
    private int subID;
    private String path;
    //Path, e.g., "Wi1:L1:R1:M1#3:L2:Wo1"

    public enum Status {NEW,READY,RUNNING,COMPLETED};
    private Status status;

    public Order(int mainID, int ID){
        this.mainID = mainID;
        this.ID = ID;
        this.subID = 0;
        this.status = Status.NEW;
    }


    public void setPath(String path) {
        this.path = path;
    public int getMainID() {
        return mainID;
    }

    public int getID() {
        return ID;
    }

    public int getSubID() {
        return subID;
    }

    public void setSubID(int subID) {
        this.subID = subID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}

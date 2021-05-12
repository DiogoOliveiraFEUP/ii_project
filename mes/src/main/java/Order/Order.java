package Order;

public class Order {

    private int mainID;
    private int subID;
    private String path="";

    public enum Status {READY,RUNNING,COMPLETED};
    private Status status;

    public int getSubID() {
        return subID;
    }

    public void setSubID(int subID) {
        this.subID = subID;
    }

    public Order(int mainID, int subID){
        this.mainID = mainID;
        this.subID = subID;
    }


    public void setPath(String path) {
        this.path = path;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getMainID() {
        return mainID;
    }

    public void setMainID(int mainID) {
        this.mainID = mainID;
    }

    public String getPath() {
        return path;
    }

    public Status getStatus() {
        return status;
    }
}

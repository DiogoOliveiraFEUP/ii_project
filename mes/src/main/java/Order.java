public class Order {

    private int ID;
    private int mainID;
    private int path;

    public enum Status {READY,RUNNING,COMPLETED}
    private Status status;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Order(int ID, int mainID){
        this.ID = ID;
        this.mainID = mainID;
    }

    public void setPath(int path) {
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

    public int getPath() {
        return path;
    }

    public Status getStatus() {
        return status;
    }
}

public class Order {

    private int mainID;
    private int path;
    private int status;

    public Order(int mainID){
        this.mainID = mainID;
    }

    public void setPath(int path) {
        this.path = path;
    }

    public void setStatus(int status) {
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

    public int getStatus() {
        return status;
    }
}

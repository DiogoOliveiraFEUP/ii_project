public class Order {

    private int mainID;
    private int path;
    private int status;
    private int inputTime;
    private int maxDelay;
    private int penalty;

    public Order(int mainID, int inputTime, int maxDelay, int penalty){
        this.mainID = mainID;
        this.inputTime = inputTime;
        this.maxDelay = maxDelay;
        this.penalty = penalty;
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

    public int getInputTime() {
        return inputTime;
    }

    public void setInputTime(int inputTime) {
        this.inputTime = inputTime;
    }

    public int getMaxDelay() {
        return maxDelay;
    }

    public void setMaxDelay(int maxDelay) {
        this.maxDelay = maxDelay;
    }

    public int getPenalty() {
        return penalty;
    }

    public void setPenalty(int penalty) {
        this.penalty = penalty;
    }
}

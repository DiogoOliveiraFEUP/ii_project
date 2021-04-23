package Entities;

public abstract class Entity {
    String name;
    double timeToProcess;

    public Entity(String name, double timeToProcess) {
        this.name = name;
        this.timeToProcess = timeToProcess;
    }
}

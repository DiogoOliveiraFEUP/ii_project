package Factory.Entities;

public abstract class Entity {
    String name;
    boolean isFree = true;

    public Entity(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

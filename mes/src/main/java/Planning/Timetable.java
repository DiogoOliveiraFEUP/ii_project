package Planning;

import Factory.Entities.Entity;
import Factory.Entities.Machine;

import java.util.HashMap;
import java.util.Map;

public class Timetable {
    Map<Entity, Map> timetable = new HashMap<>();

    public Timetable() {
        addEntity(new Machine("M1"));
        addEntity(new Machine("M2"));
        addEntity(new Machine("M3"));
        addEntity(new Machine("M4"));
        addEntity(new Machine("M5"));
        addEntity(new Machine("M6"));
        addEntity(new Machine("M7"));
        addEntity(new Machine("M8"));

    }

    public void addEntity(Entity entity){
        timetable.put(entity,new HashMap<Integer,Boolean>());
    }

    public void setSlotOccupied(Entity entity, int slot){
        timetable.get(entity).put(slot,true);
    }

    public boolean isSlotOccupied(Entity entity, int slot){
        return (boolean) timetable.get(entity).get(slot);
    }
}

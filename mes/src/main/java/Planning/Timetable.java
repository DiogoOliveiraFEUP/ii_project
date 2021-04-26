package Planning;

import Factory.Entities.Entity;

import java.util.HashMap;
import java.util.Map;

public class Timetable {
    Map<Entity, Map> timetable = new HashMap<>();

    public Timetable() {
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

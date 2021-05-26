package GUI;

import javax.swing.*;
import java.util.ArrayList;

public class UnloadData {

    ArrayList<Integer> unloadParts = new ArrayList<>();
    String outputName="";

    public UnloadData(String outputName){
        this.outputName=outputName;
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
        unloadParts.add(0);
    }

    public int getTotalUnloaded(){
        Integer sum = 0;
        for(Integer integer:unloadParts){
            sum+=integer;
        }
        return sum;
    }

    public ArrayList<Integer> getUnloadParts() {
        return unloadParts;
    }

    public void setUnloadPartsNumber(int i,int number) {
        unloadParts.set(i,number);

    }

}

package GUI;

import javax.swing.*;
import java.util.ArrayList;

public class MachinedData {

   ArrayList<Integer> machinedParts = new ArrayList<>();
   Integer machinedSeconds=0;
   String machineName="";

   public MachinedData(String machineName){
       this.machineName=machineName;
       machinedParts.add(0);
       machinedParts.add(0);
       machinedParts.add(0);
       machinedParts.add(0);
       machinedParts.add(0);
       machinedParts.add(0);
   }

   public int getTotalMachined(){
       Integer sum = 0;
       for(Integer integer:machinedParts){
           sum+=integer;
       }
       return sum;
   }

    public String getMachinedTime() {
       return
                ((Integer)(machinedSeconds/60)).toString()+"m"+((Integer)(machinedSeconds%60)).toString()+"s";
    }

    public ArrayList<Integer> getMachinedParts() {
        return machinedParts;
    }

    public void setMachinedParts(ArrayList<Integer> machinedParts) {
        this.machinedParts = machinedParts;
    }

    public Integer getMachinedSeconds() {
        return machinedSeconds;
    }

    public void setMachinedSeconds(Integer machinedSeconds) {
        this.machinedSeconds = machinedSeconds;
    }

    public void setMachinedPartsNumber(int i,int number) {
       machinedParts.set(i,number);

    }

}

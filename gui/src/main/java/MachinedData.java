import jdk.swing.interop.SwingInterOpUtils;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class MachinedData {
    JTable jtable= null;
   ArrayList<Integer> machinedParts = new ArrayList<>();
   Integer machinedSeconds=0;
   String machineName="";
   String[] machinedDataString;
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

   public void setJtable(JTable jtable){
       this.jtable = jtable;
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

package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class MachinedTableData {
    ArrayList<MachinedData> machinedPartsList = new ArrayList<>();
    JTable jTable = null;

    public MachinedTableData(ArrayList<MachinedData> machinedPartsList) {
        this.machinedPartsList = machinedPartsList;
    }

    public MachinedTableData(){
        machinedPartsList.add(new MachinedData("M1"));
        machinedPartsList.add(new MachinedData("M2"));
        machinedPartsList.add(new MachinedData("M3"));
        machinedPartsList.add(new MachinedData("M4"));
        machinedPartsList.add(new MachinedData("M5"));
        machinedPartsList.add(new MachinedData("M6"));
        machinedPartsList.add(new MachinedData("M7"));
        machinedPartsList.add(new MachinedData("M8"));
    }

    public void setMachinedTime(int machine,int time){
        machinedPartsList.get(machine).machinedSeconds = time;
        if(jTable!=null)jTable.setModel(this.getTableModel());

    }

    public void setMachinedParts(int machine,int part,int number){
        machinedPartsList.get(machine).getMachinedParts().set(part,number);
        if(jTable!=null)jTable.setModel(this.getTableModel());
    }

    public int getMachinedParts(int machine,int part) {
        return  machinedPartsList.get(machine).getMachinedParts().get(part);
    }

    public int getMachinedTime(int machine,int part) {
        return  machinedPartsList.get(machine).getMachinedSeconds();
    }

        public void setTable(JTable jTable){
        this.jTable = jTable;
    }

    public TableModel getTableModel( ){
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] column ={"Machine","Time","Total","P1","P2","P3","P4","P5","P6"};
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(column);

        Object[] data = new Object[9];
        for (MachinedData machinedData : machinedPartsList) {
            data[0] = machinedData.machineName;
            data[1] = machinedData.getMachinedTime();
            data[2] = machinedData.getTotalMachined();
            data[3] = machinedData.getMachinedParts().get(0);
            data[4] = machinedData.getMachinedParts().get(1);
            data[5] = machinedData.getMachinedParts().get(2);
            data[6] = machinedData.getMachinedParts().get(3);
            data[7] = machinedData.getMachinedParts().get(4);
            data[8] = machinedData.getMachinedParts().get(5);
            tableModel.addRow(data);
        }
        return tableModel;
    }

}

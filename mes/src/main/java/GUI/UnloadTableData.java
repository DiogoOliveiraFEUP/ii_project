package GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.ArrayList;

public class UnloadTableData {
    ArrayList<UnloadData> unloadPartsList = new ArrayList<>();
    JTable jTable = null;

    public UnloadTableData(ArrayList<UnloadData> unloadPartsList) {
        this.unloadPartsList = unloadPartsList;
    }

    public UnloadTableData(){
        unloadPartsList.add(new UnloadData("O1"));
        unloadPartsList.add(new UnloadData("O2"));
        unloadPartsList.add(new UnloadData("O3"));

    }



    public void setUnloadPart(int exit,int part,int number){
        unloadPartsList.get(exit).getUnloadParts().set(part,number);
        if(jTable!=null)jTable.setModel(this.getTableModel());
    }

    public int getUnloadPart(int exit,int part) {
        return  unloadPartsList.get(exit).getUnloadParts().get(part);
    }


    public void setTable(JTable jTable){
        this.jTable = jTable;
    }

    public TableModel getTableModel( ){
        DefaultTableModel tableModel = new DefaultTableModel();
        String[] column ={"Output","P1","P2","P3","P4","P5","P6","P7","P8","P9","Total"};
        tableModel.setRowCount(0);
        tableModel.setColumnIdentifiers(column);

        Object[] data = new Object[11];
        for (UnloadData unloadData : unloadPartsList) {
            data[0] = unloadData.outputName;
            data[1] = unloadData.getUnloadParts().get(0);
            data[2] = unloadData.getUnloadParts().get(1);
            data[3] = unloadData.getUnloadParts().get(2);
            data[4] = unloadData.getUnloadParts().get(3);
            data[5] = unloadData.getUnloadParts().get(4);
            data[6] = unloadData.getUnloadParts().get(5);
            data[7] = unloadData.getUnloadParts().get(6);
            data[8] = unloadData.getUnloadParts().get(7);
            data[9] = unloadData.getUnloadParts().get(8);
            data[10] = unloadData.getTotalUnloaded();
            tableModel.addRow(data);
        }
        return tableModel;
    }

}

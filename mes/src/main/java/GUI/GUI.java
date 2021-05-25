package GUI;

import java.util.concurrent.TimeUnit;

public class GUI {

    public MachinedTableData machinedTableData;

    public UnloadTableData unloadTableData;

    public GUI(){
        machinedTableData = new  MachinedTableData();
        unloadTableData = new UnloadTableData();

        new GUI_Main(machinedTableData,unloadTableData);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machinedTableData.setMachinedParts(0,0,5);
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        machinedTableData.setMachinedTime(2,300);
        machinedTableData.setMachinedParts(3,0,10);
        unloadTableData.setUnloadPart(2,0,10);
    }
}

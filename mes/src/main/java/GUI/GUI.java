package GUI;

public class GUI {

    public MachinedTableData machinedTableData;

    public UnloadTableData unloadTableData;

    public GUI(){
        machinedTableData = new  MachinedTableData();
        unloadTableData = new UnloadTableData();

        new GUI_Main(machinedTableData,unloadTableData);
    }
}

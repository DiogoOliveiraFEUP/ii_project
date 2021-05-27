import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Application {
    int unldP1_PM1, unldP1_PM2, unldP1_PM3;
    int unldP2_PM1, unldP2_PM2, unldP2_PM3;
    int unldP3_PM1, unldP3_PM2, unldP3_PM3;
    int unldP4_PM1, unldP4_PM2, unldP4_PM3;
    int unldP5_PM1, unldP5_PM2, unldP5_PM3;
    int unldP6_PM1, unldP6_PM2, unldP6_PM3;
    int unldP7_PM1, unldP7_PM2, unldP7_PM3;
    int unldP8_PM1, unldP8_PM2, unldP8_PM3;
    int unldP9_PM1, unldP9_PM2, unldP9_PM3;

    int operP1_M1, operP1_M2, operP1_M3, operP1_M4, operP1_M5, operP1_M6, operP1_M7, operP1_M8;
    int operP2_M1, operP2_M2, operP2_M3, operP2_M4, operP2_M5, operP2_M6, operP2_M7, operP2_M8;
    int operP3_M1, operP3_M2, operP3_M3, operP3_M4, operP3_M5, operP3_M6, operP3_M7, operP3_M8;
    int operP4_M1, operP4_M2, operP4_M3, operP4_M4, operP4_M5, operP4_M6, operP4_M7, operP4_M8;
    int operP5_M1, operP5_M2, operP5_M3, operP5_M4, operP5_M5, operP5_M6, operP5_M7, operP5_M8;
    int operP6_M1, operP6_M2, operP6_M3, operP6_M4, operP6_M5, operP6_M6, operP6_M7, operP6_M8;

    int timeM1, timeM2, timeM3, timeM4, timeM5, timeM6, timeM7, timeM8;

    public static void main(String[] args) {

        MachinedTableData machinedTableData = new  MachinedTableData();
        UnloadTableData unloadTableData = new UnloadTableData();
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
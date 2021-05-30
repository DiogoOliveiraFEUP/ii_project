package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI_Main {

    JFrame f;

    public GUI_Main(MachinedTableData machinedData, UnloadTableData unloadTableData){

        f = new JFrame("MES Graphical User Interface");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---- MAIN PANEL ---- //
        JTabbedPane tabbedPane = new JTabbedPane();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        //constraints.insets = new Insets(10, 10, 10, 10);

        GUI_MachinedParts gui_machinedParts = new GUI_MachinedParts( machinedData);
        JPanel machinedPartsPanel = gui_machinedParts.getPanel();
        machinedPartsPanel.setName("Machined");
        tabbedPane.add(machinedPartsPanel);

        GUI_UnloadedParts gui_unloadedParts = new GUI_UnloadedParts(unloadTableData);
        JPanel unloadedPartsPanel = gui_unloadedParts.getPanel();
        unloadedPartsPanel.setName("Unloaded");
        tabbedPane.add(unloadedPartsPanel);

        // -------------------  //

        f.add(tabbedPane);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

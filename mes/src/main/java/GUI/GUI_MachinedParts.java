package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI_MachinedParts {

    private JPanel machinedPartsPanel;

    public GUI_MachinedParts(MachinedTableData machinedData) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        machinedPartsPanel = new JPanel();
        machinedPartsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Machined Parts"));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;

        machinedData.jTable.setBounds(30,40,600,400);

        JScrollPane sp=new JScrollPane(machinedData.jTable);

        machinedPartsPanel.add(sp,constraints);
        machinedPartsPanel.setSize(300,400);
        machinedPartsPanel.setVisible(true);
    }

    public JPanel getPanel() {
        return this.machinedPartsPanel;
    }

}

package GUI;

import javax.swing.*;
import java.awt.*;

public class GUI_UnloadedParts {

    private JPanel unloadedPartsPanel;

    public GUI_UnloadedParts(UnloadTableData unloadTableData) {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        unloadedPartsPanel = new JPanel();
        unloadedPartsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Machined Parts"));

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;

        unloadTableData.jTable.setBounds(30,40,600,400);

        JScrollPane sp = new JScrollPane(unloadTableData.jTable);

        unloadedPartsPanel.add(sp,constraints);
        unloadedPartsPanel.setSize(300,400);
        unloadedPartsPanel.setVisible(true);
    }

    public JPanel getPanel() {
        return this.unloadedPartsPanel;
    }

}
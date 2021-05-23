import javax.swing.*;
import java.awt.*;

public class GUI_UnloadedParts {
    private JPanel unloadedPartsPanel;
    private GUI_Main gui_main;

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

        JTable jt=new JTable();
        jt.setBounds(30,40,600,400);

        unloadTableData.setTable(jt);
        jt.setModel(unloadTableData.getTableModel());



        JScrollPane sp=new JScrollPane(jt);
        unloadedPartsPanel.add(sp,constraints);
        unloadedPartsPanel.setSize(300,400);
        unloadedPartsPanel.setVisible(true);

        //machinedPartsPanel.add(sendReqOrders,constraints);
    }

    public JPanel getPanel() {
        return this.unloadedPartsPanel;
    }

}
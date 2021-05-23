import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ContainerListener;

public class GUI_MachinedParts {
    private JPanel machinedPartsPanel;
    private GUI_Main gui_main;


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
        JTable jt=new JTable();
        jt.setBounds(30,40,600,400);

        machinedData.setTable(jt);
        jt.setModel(machinedData.getTableModel());



        JScrollPane sp=new JScrollPane(jt);

        machinedPartsPanel.add(sp,constraints);
        machinedPartsPanel.setSize(300,400);
        machinedPartsPanel.setVisible(true);



        //machinedPartsPanel.add(sendReqOrders,constraints);
    }



    public JPanel getPanel() {
        return this.machinedPartsPanel;
    }

}

import javax.swing.*;
import java.awt.*;

public class GUI_Main {


    public GUI_Main(){
        JFrame f = new JFrame("Enterprise Resource Planning");

        // ---- MAIN PANEL ---- //

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        JPanel transfPanel = (new GUI_TransfPanel()).getJPanel();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        mainPanel.add(transfPanel,constraints);

        JPanel unldPanel = (new GUI_UnldPanel()).getUnldPanel();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        mainPanel.add(unldPanel,constraints);

        JPanel storesPanel = (new GUI_StoresPanel()).getStoresPanel();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(storesPanel,constraints);

        JPanel ordersPanel = (new GUI_OrdersPanel()).getOrdersPanel();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(ordersPanel,constraints);

        JPanel footer = (new GUI_Footer()).getFooter();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        mainPanel.add(footer,constraints);

        // -------------------  //

        f.add(mainPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}

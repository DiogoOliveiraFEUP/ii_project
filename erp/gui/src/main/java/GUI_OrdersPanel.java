import javax.swing.*;
import java.awt.*;

public class GUI_OrdersPanel {

    private JPanel ordersPanel;

    public GUI_OrdersPanel(){

        // ---- REQUEST ORDERS ---- //

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        ordersPanel = new JPanel(new GridBagLayout());
        ordersPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Request Orders"));

        JButton sendReqOrders = new JButton("Send Order");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        ordersPanel.add(sendReqOrders,constraints);

        // ----------------------- //

    }

    public JPanel getOrdersPanel() {
        return this.ordersPanel;
    }

}


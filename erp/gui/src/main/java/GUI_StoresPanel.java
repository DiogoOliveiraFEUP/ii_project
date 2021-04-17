import javax.swing.*;
import java.awt.*;

public class GUI_StoresPanel {

    private JPanel storesPanel;

    public GUI_StoresPanel(){

        // ---- REQUEST STORES ---- //

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        storesPanel = new JPanel(new GridBagLayout());
        storesPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Request Stores"));

        JButton sendReqStores = new JButton("Send Order");
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        storesPanel.add(sendReqStores,constraints);

        // ------------------------ //
    }

    public JPanel getStoresPanel() {
        return storesPanel;
    }
}

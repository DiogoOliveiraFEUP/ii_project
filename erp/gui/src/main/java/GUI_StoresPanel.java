import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_StoresPanel {

    private final GUI_Main gui_main;
    private JPanel storesPanel;

    public GUI_StoresPanel(GUI_Main gui_main){
        this.gui_main = gui_main;

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

        sendReqStores.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                gui_main.getUdpClient().send(getMessage(),gui_main.getIPmes(),gui_main.getPortMes());
                new GUI_UDP_Listener(gui_main);
            }
        });

        // ------------------------ //
    }

    private String getMessage() {
        return "<?xml version=\"1.0\"?>\n" +
                "<!DOCTYPE PRODUCTION_ORDERS [\n" +
                "<!ELEMENT ORDERS (Request_Stores | Order*)>\n" +
                "<!ELEMENT Request_Stores EMPTY>\n" +
                "<!ELEMENT Order (Transform | Unload)>\n" +
                "<!ATTLIST Order\n" +
                "          Number   (CDATA) #REQUIRED\n" +
                ">\n" +
                "<!ELEMENT Transform EMPTY>\n" +
                "<!ATTLIST Transform\n" +
                "          From     (CDATA) #REQUIRED\n" +
                "          To       (CDATA) #REQUIRED\n" +
                "          Quantity (CDATA) #REQUIRED\n" +
                "          Time     (CDATA) #REQUIRED\n" +
                "          MaxDelay (CDATA) #REQUIRED\n" +
                "          Penalty  (CDATA) #REQUIRED\n" +
                ">\n" +
                "<!ELEMENT Unload EMPTY>\n" +
                "<!ATTLIST Unload\n" +
                "          Type        (CDATA) #REQUIRED\n" +
                "          Destination (CDATA) #REQUIRED\n" +
                "          Quantity    (CDATA) #REQUIRED\n" +
                ">\n" +
                "]>\n" +
                "<ORDERS>\n" +
                "<Request_Stores/>\n" +
                "</ORDERS>";
    }

    public JPanel getStoresPanel() {
        return storesPanel;
    }
}

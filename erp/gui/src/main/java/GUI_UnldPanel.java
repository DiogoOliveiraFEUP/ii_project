import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_UnldPanel {

    private final GUI_Main gui_main;
    String[] pieces = {"P1","P2","P3","P4","P5","P6","P7","P8","P9"};
    String[] dests = {"PM1","PM2","PM3"};

    private JPanel unldPanel;

    public GUI_UnldPanel(GUI_Main gui_main){
        this.gui_main = gui_main;

        // ---- UNLOAD ORDER ---- //

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        unldPanel = new JPanel(new GridBagLayout());
        unldPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Unload Order"));

        JLabel type = new JLabel("Type:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        unldPanel.add(type,constraints);

        JComboBox<String> unldType = new JComboBox<>(pieces);
        constraints.gridx = 0;
        constraints.gridy = 1;
        unldPanel.add(unldType,constraints);

        JLabel dest = new JLabel("Dest:");
        constraints.gridx = 1;
        constraints.gridy = 0;
        unldPanel.add(dest,constraints);

        JComboBox<String> unldDest = new JComboBox<>(dests);
        constraints.gridx = 1;
        constraints.gridy = 1;
        unldPanel.add(unldDest,constraints);

        JLabel quant = new JLabel("Quantity:");
        constraints.gridx = 2;
        constraints.gridy = 0;
        unldPanel.add(quant,constraints);

        JSpinner unldQuant = new JSpinner(
                new SpinnerNumberModel(1,1,50,1));
        constraints.gridx = 2;
        constraints.gridy = 1;
        unldPanel.add(unldQuant,constraints);

        JButton sendUnld = new JButton("Send Order");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        unldPanel.add(sendUnld,constraints);

        sendUnld.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String message = getMessage((String) unldType.getSelectedItem(),
                        (String) unldDest.getSelectedItem(), (Integer) unldQuant.getValue());
                gui_main.getUdpSender().send(message,gui_main.getIPmes(),gui_main.getPortMes());
                gui_main.setNextOrderID(gui_main.getNextOrderID()+1);
            }
        });

        // ------------------------ //

    }

    public String getMessage(String type, String dest, Integer quant){
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
                "<Order Number=\"" + gui_main.getNextOrderID() +"\">\n" +
                "<Unload Type=\"" + type + "\" Destination=\"" + dest + "\" Quantity=\"" + quant + "\"/>\n" +
                "</Order>\n" +
                "</ORDERS>\n";
    }

    public JPanel getUnldPanel() {
        return unldPanel;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_TransfPanel {

    private final GUI_Main gui_main;
    String[] pieces = {"P1","P2","P3","P4","P5","P6","P7","P8","P9"};

    private JPanel transfPanel;

    public GUI_TransfPanel(GUI_Main gui_main){
        this.gui_main = gui_main;

        // ---- TRANSFORMATION ORDER ---- //

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        transfPanel = new JPanel(new GridBagLayout());
        //transfPanel.setBounds(10,10,200,200);
        transfPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Transformation Order"));

        JLabel from = new JLabel("From:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        transfPanel.add(from,constraints);

        JComboBox<String> transfFrom = new JComboBox<>(pieces);
        constraints.gridx = 0;
        constraints.gridy = 1;
        transfPanel.add(transfFrom,constraints);

        JLabel to = new JLabel("To:");
        constraints.gridx = 1;
        constraints.gridy = 0;
        transfPanel.add(to,constraints);

        JComboBox<String> transfTo = new JComboBox<>(pieces);
        constraints.gridx = 1;
        constraints.gridy = 1;
        transfPanel.add(transfTo,constraints);

        JLabel transfQuantL = new JLabel("Quantity:");
        constraints.gridx = 2;
        constraints.gridy = 0;
        transfPanel.add(transfQuantL,constraints);

        JSpinner transfQuant = new JSpinner(
                new SpinnerNumberModel(1,1,50,1));
        constraints.gridx = 2;
        constraints.gridy = 1;
        transfPanel.add(transfQuant,constraints);

        JButton sendTransf = new JButton("Send Order");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        transfPanel.add(sendTransf,constraints);

        sendTransf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String message = getMessage((String) transfFrom.getSelectedItem(),
                        (String) transfTo.getSelectedItem(), (Integer) transfQuant.getValue());
                gui_main.getUdpSender().send(message,gui_main.getIPmes(),gui_main.getPortMes());
                gui_main.setNextOrderID(gui_main.getNextOrderID()+1);
            }
        });

        // ---------------------- //
    }

    private String getMessage(String from, String to, int quant) {
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
                "<Transform From=\"" + from + "\" To=\"" + to + "\" Quantity=\"" + quant + "\" Time=\"0\" MaxDelay=\"250\" Penalty=\"100\"/>\n" +
                "</Order>\n" +
                "</ORDERS>\n";
    }

    public JPanel getJPanel(){
        return this.transfPanel;
    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Instant;

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

        JLabel TimeL = new JLabel("Time:");
        constraints.gridx = 0;
        constraints.gridy = 2;
        transfPanel.add(TimeL,constraints);

        JSpinner Time = new JSpinner(
                new SpinnerNumberModel(-1,-1,3600,1));
        constraints.gridx = 0;
        constraints.gridy = 3;
        transfPanel.add(Time,constraints);

        JLabel MaxDelayL = new JLabel("MaxDelay:");
        constraints.gridx = 1;
        constraints.gridy = 2;
        transfPanel.add(MaxDelayL,constraints);

        JSpinner MaxDelay = new JSpinner(
                new SpinnerNumberModel(60,0,3600,1));
        constraints.gridx = 1;
        constraints.gridy = 3;
        transfPanel.add(MaxDelay,constraints);

        JLabel PenaltyL = new JLabel("Penalty:");
        constraints.gridx = 2;
        constraints.gridy = 2;
        transfPanel.add(PenaltyL,constraints);

        JSpinner Penalty = new JSpinner(
                new SpinnerNumberModel(0,0,50,1));
        constraints.gridx = 2;
        constraints.gridy = 3;
        transfPanel.add(Penalty,constraints);

        JLabel TimeUNIX = new JLabel("(Time=-1 => Time=UNIX timestamp)");
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        transfPanel.add(TimeUNIX,constraints);

        JButton sendTransf = new JButton("Send Order");
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        transfPanel.add(sendTransf,constraints);

        sendTransf.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String message = getMessage((String) transfFrom.getSelectedItem(),
                        (String) transfTo.getSelectedItem(), (Integer) transfQuant.getValue(),
                            (Integer) Time.getValue(), (Integer) MaxDelay.getValue(), (Integer) Penalty.getValue());
                gui_main.getUdpClient().send(message,gui_main.getIPmes(),gui_main.getPortMes());
                gui_main.setNextOrderID(gui_main.getNextOrderID()+1);
            }
        });

        // ---------------------- //
    }

    private String getMessage(String from, String to, int quant, int _time, int maxDelay, int penalty) {

        long time;

        if(_time == -1) time = Instant.now().getEpochSecond();
        else time = _time;

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
                "<Transform From=\"" + from + "\" To=\"" + to + "\" Quantity=\"" + quant + "\" " +
                    "Time=\"" + time + "\" MaxDelay=\"" + maxDelay + "\" Penalty=\"" + penalty + "\"/>\n" +
                "</Order>\n" +
                "</ORDERS>\n";
    }

    public JPanel getJPanel(){
        return this.transfPanel;
    }
}

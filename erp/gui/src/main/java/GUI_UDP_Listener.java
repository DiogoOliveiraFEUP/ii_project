import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI_UDP_Listener {

    public GUI_UDP_Listener(GUI_Main gui_main, String title) {

//        gui_main.getJFrame().setVisible(false);

        JFrame f = new JFrame("Enterprise Resource Planning");

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        // ---- UDP LISTENER PANEL ---- //

        JPanel udpPanel = new JPanel(new GridBagLayout());
        udpPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), title));

        JTextArea text = new JTextArea("Listening...");
        //text.setBounds(10,30, 200,200);
        constraints.gridx = 0;
        constraints.gridy = 0;
        udpPanel.add(text,constraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        mainPanel.add(udpPanel,constraints);

        // -------------------  //

        JButton close = new JButton("Close");
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(close,constraints);

        close.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                f.dispose();
                //gui_main.getJFrame().setVisible(true);
            }
        });

        f.add(mainPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);

        Thread udplistener = new Thread(new Runnable() {
            @Override
            public void run() {
                String response = gui_main.getUdpClient().listen();
                text.setText(response);
                f.pack();
            }
        });

        udplistener.start();
    }
}

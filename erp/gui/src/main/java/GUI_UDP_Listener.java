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



    /*
    private final JFrame frame;
    private final ArrayList packet_list;
    JTextArea area;
    DefaultListModel<String> l1;
    JList<String> list;
    public UDP_GUI(ArrayList packet_list){
        this.packet_list = packet_list;

        frame = new JFrame("UDP_Listener");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000,800);

        JPanel panel = new JPanel();

        frame.add(panel);
        l1 = new DefaultListModel<>();
        //l1.addAll(Arrays.asList((String[]) packet_list.toArray()));
        list = new JList<>(l1);
        list.setBounds(0,0,200,800);
        panel.setSize(1000,800);

        ListSelectionModel listSelectionModel = list.getSelectionModel();
        listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());

        area=new JTextArea();
        area.setBounds(200,0,800,800);
        panel.add(area);
        panel.add(list);
        panel.setLayout(null);
        panel.setVisible(true);
        frame.setVisible(true);
    }




    public void refresh(){
        l1.clear();
        //l1.addAll(Arrays.asList((String[]) packet_list.toArray()));
        this.frame.revalidate();
        this.frame.repaint();
    }

    class SharedListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel)e.getSource();
            if(lsm.getMinSelectionIndex()<0){return;}
            area.setText((String) packet_list.get(lsm.getMaxSelectionIndex()));
            area.append("\n");
            area.setCaretPosition(area .getDocument().getLength());
        }
    }
*/
}

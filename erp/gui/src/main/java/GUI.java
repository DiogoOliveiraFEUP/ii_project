import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI{
    private final JFrame frame;
    private final ArrayList packet_list;
    JTextArea area;
    DefaultListModel<String> l1;
    JList<String> list;
    public GUI(ArrayList packet_list){
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

}

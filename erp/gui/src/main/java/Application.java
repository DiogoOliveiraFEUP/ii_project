import javax.swing.*;
import java.awt.*;

public class Application {

        String[] pieces = {"P1","P2","P3","P4","P5","P6","P7","P8","P9"};

        public Application() {
            JFrame f = new JFrame("Enterprise Resource Planning");

            // ---- MAIN PANEL ---- //

            JPanel mainPanel = new JPanel(new GridBagLayout());
            GridBagConstraints constraints = new GridBagConstraints();
            constraints.anchor = GridBagConstraints.CENTER;
            constraints.insets = new Insets(10, 10, 10, 10);

            // -------------------  //


            // ---- TRANSFORMATION ORDER ---- //

            JPanel transfPanel = new JPanel(new GridBagLayout());
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

            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            mainPanel.add(transfPanel,constraints);

            // ---------------------- //

            // ---- UNLOAD ORDER ---- //

            JPanel unldPanel = new JPanel(new GridBagLayout());
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

            JComboBox<String> unldDest = new JComboBox<>(pieces);
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

            constraints.gridx = 1;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            mainPanel.add(unldPanel,constraints);

            // ------------------------ //

            // ---- REQUEST STORES ---- //

            JPanel reqStores = new JPanel(new GridBagLayout());
            reqStores.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "Request Stores"));

            JButton sendReqStores = new JButton("Send Order");
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            reqStores.add(sendReqStores,constraints);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 1;
            mainPanel.add(reqStores,constraints);

            // ------------------------ //

            // ---- REQUEST ORDERS ---- //

            JPanel reqOrders = new JPanel(new GridBagLayout());
            reqOrders.setBorder(BorderFactory.createTitledBorder(
                    BorderFactory.createEtchedBorder(), "Request Orders"));

            JButton sendReqOrders = new JButton("Send Order");
            constraints.gridx = 0;
            constraints.gridy = 0;
            constraints.gridwidth = 1;
            reqOrders.add(sendReqOrders,constraints);

            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 1;
            constraints.gridy = 1;
            mainPanel.add(reqOrders,constraints);

            // ----------------------- //

            // ---- NEXT ORDER ID ---- //

            JPanel nextOrder = new JPanel(new GridBagLayout());
            constraints.insets = new Insets(10, 5, 10, 5);

            JLabel nextOrderID = new JLabel("Next Order ID:");
            constraints.gridx = 0;
            constraints.gridy = 0;
            nextOrder.add(nextOrderID,constraints);

            JSpinner nextID = new JSpinner(
                    new SpinnerNumberModel(1,1,100,1));
            constraints.gridx = 1;
            constraints.gridy = 0;
            nextOrder.add(nextID,constraints);

            JButton nextOrderSet = new JButton("Set");
            constraints.gridx = 2;
            constraints.gridy = 0;
            nextOrder.add(nextOrderSet,constraints);


            constraints.insets = new Insets(10, 10, 10, 10);
            constraints.fill = GridBagConstraints.NONE;
            constraints.anchor = GridBagConstraints.LINE_START;
            constraints.gridx = 0;
            constraints.gridy = 2;
            constraints.gridwidth = 2;
            mainPanel.add(nextOrder,constraints);

            // ----------------------- //




            f.add(mainPanel);
            f.pack();
            f.setLocationRelativeTo(null);
            f.setVisible(true);
        }

        public static void main(String[] args) {
            new Application();
        }


    /*
    UDP_GUI UDPGui;

    public void refresh(){
        UDPGui.refresh();
    }

    Application(){
        ArrayList packet_list = new ArrayList<String>();
        UDPGui = new UDP_GUI(packet_list);
        System.out.println("Please input a port to listen to\n");
        Scanner kb_scanner = new Scanner(System.in);
        int port = kb_scanner.nextInt();
        try {
            Thread udp_listener = new UDP_Listener(port,packet_list, this);
            udp_listener.start();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        while(true){
            byte next_c = (byte) kb_scanner.next().charAt(0);
            if(next_c == 'O'){
                for(Object packet: packet_list){
                    System.out.println(packet);
                }
            }else if(next_c == 'U'){
                UDPGui.refresh();
            }else if(next_c == 'C') {

            }
        }
    }

    public static void main(String[] args) {
        new Application();
    }*/
}

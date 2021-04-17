import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GUI_Main {

    private InetAddress IPmes;
    private int portMes;
    private InetAddress IPerp;
    private int portErp;

    private UDP_Client udpClient;

    private int nextOrderID = 1;

    GUI_Footer gui_footer = new GUI_Footer(this);

    JFrame f;

    public GUI_Main(){

        try {
            IPmes = InetAddress.getByName("127.0.0.1");
            portMes = 1234;
            IPerp = InetAddress.getByName("127.0.0.1");
            portErp = 4321;
        } catch (Exception e) {
            e.printStackTrace();
        }

        udpClient = new UDP_Client(portErp);

        f = new JFrame("Enterprise Resource Planning");

        // ---- MAIN PANEL ---- //

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        JPanel transfPanel = (new GUI_TransfPanel(this)).getJPanel();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        mainPanel.add(transfPanel,constraints);

        JPanel unldPanel = (new GUI_UnldPanel(this)).getUnldPanel();
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        mainPanel.add(unldPanel,constraints);

        JPanel storesPanel = (new GUI_StoresPanel(this)).getStoresPanel();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 1;
        mainPanel.add(storesPanel,constraints);

        JPanel ordersPanel = (new GUI_OrdersPanel(this)).getOrdersPanel();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 1;
        mainPanel.add(ordersPanel,constraints);

        JPanel footer = gui_footer.getFooter();
        constraints.insets = new Insets(10, 10, 10, 10);
        constraints.fill = GridBagConstraints.NONE;
        constraints.anchor = GridBagConstraints.LINE_START;
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 2;
        mainPanel.add(footer,constraints);

        // -------------------  //

        f.add(mainPanel);
        f.pack();
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }

    public InetAddress getIPmes() {
        return IPmes;
    }

    public void setIPmes(InetAddress IPmes) {
        this.IPmes = IPmes;
    }

    public int getPortMes() {
        return portMes;
    }

    public void setPortMes(int portMes) {
        this.portMes = portMes;
    }

    public InetAddress getIPerp() {
        return IPerp;
    }

    public void setIPerp(InetAddress IPerp) {
        this.IPerp = IPerp;
    }

    public int getPortErp() {
        return portErp;
    }

    public void setPortErp(int portErp) {
        this.portErp = portErp;
    }

    public UDP_Client getUdpClient() {
        return udpClient;
    }

    public int getNextOrderID() {
        return this.nextOrderID;
    }

    public void setNextOrderID(int nextOrderID) {
        this.nextOrderID = nextOrderID;
        gui_footer.getNextID().setValue(nextOrderID);
    }

    public JFrame getJFrame() {
        return f;
    }
}

package GUI;

import javax.swing.*;
import java.awt.*;
import java.net.InetAddress;

public class GUI_Main {

    private InetAddress IPmes;
    private int portMes;
    private InetAddress IPerp;
    private int portErp;


    private int nextOrderID = 1;


    JFrame f;

    public GUI_Main(MachinedTableData machinedData, UnloadTableData unloadTableData){




        f = new JFrame("MES Graphical User Interface");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---- MAIN PANEL ---- //
        JTabbedPane tabbedPane = new JTabbedPane();

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        //constraints.insets = new Insets(10, 10, 10, 10);

        GUI_MachinedParts gui_machinedParts = new GUI_MachinedParts( machinedData);
        JPanel machinedPartsPanel = gui_machinedParts.getPanel();
        machinedPartsPanel.setName("Machined");
        tabbedPane.add(machinedPartsPanel);

        GUI_UnloadedParts gui_unloadedParts = new GUI_UnloadedParts(unloadTableData);
        JPanel unloadedPartsPanel = gui_unloadedParts.getPanel();
        unloadedPartsPanel.setName("Unloaded");
        tabbedPane.add(unloadedPartsPanel);



        // -------------------  //

        f.add(tabbedPane);
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


    public int getNextOrderID() {
        return this.nextOrderID;
    }

    public void setNextOrderID(int nextOrderID) {
        this.nextOrderID = nextOrderID;
    }

    public JFrame getJFrame() {
        return f;
    }
}

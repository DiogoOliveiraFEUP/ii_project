import javax.swing.*;
import java.awt.*;

public class GUI_UnldPanel {

    String[] pieces = {"P1","P2","P3","P4","P5","P6","P7","P8","P9"};

    private JPanel unldPanel;

    public GUI_UnldPanel(){

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

        // ------------------------ //

    }

    public JPanel getUnldPanel() {
        return unldPanel;
    }
}

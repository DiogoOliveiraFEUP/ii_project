import javax.swing.*;
import java.awt.*;

public class GUI_Footer {

    JPanel footer;

    public GUI_Footer(){

        // ---- NEXT ORDER ID ---- //

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.insets = new Insets(10, 10, 10, 10);

        footer = new JPanel(new GridBagLayout());
        constraints.insets = new Insets(10, 5, 10, 5);

        JLabel nextOrderID = new JLabel("Next Order ID:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        footer.add(nextOrderID,constraints);

        JSpinner nextID = new JSpinner(
                new SpinnerNumberModel(1,1,100,1));
        constraints.gridx = 1;
        constraints.gridy = 0;
        footer.add(nextID,constraints);

        JButton nextOrderSet = new JButton("Set");
        constraints.gridx = 2;
        constraints.gridy = 0;
        footer.add(nextOrderSet,constraints);

        // ----------------------- //

    }

    public JPanel getFooter() {
        return footer;
    }
}

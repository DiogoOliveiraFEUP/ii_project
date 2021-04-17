import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class GUI_Footer {

    private JPanel footer;
    private JSpinner nextID;

    public GUI_Footer(GUI_Main gui_main){

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

        nextID = new JSpinner(
                new SpinnerNumberModel(1,1,100,1));
        constraints.gridx = 1;
        constraints.gridy = 0;
        footer.add(nextID,constraints);

        nextID.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                gui_main.setNextOrderID((Integer) nextID.getValue());
            }
        });


        // ----------------------- //

    }

    public JPanel getFooter() {
        return footer;
    }

    public JSpinner getNextID(){
        return nextID;
    }
}

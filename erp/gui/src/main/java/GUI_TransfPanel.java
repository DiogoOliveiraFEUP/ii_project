import javax.swing.*;
import java.awt.*;

public class GUI_TransfPanel {

    String[] pieces = {"P1","P2","P3","P4","P5","P6","P7","P8","P9"};

    private JPanel transfPanel;

    public GUI_TransfPanel(){

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

        JButton sendTransf = new JButton("Send Order");
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 3;
        transfPanel.add(sendTransf,constraints);

        // ---------------------- //
    }

    public JPanel getJPanel(){
        return this.transfPanel;
    }
}

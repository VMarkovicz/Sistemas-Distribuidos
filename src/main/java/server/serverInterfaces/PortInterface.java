package server.serverInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PortInterface extends JDialog{
    private JPanel PortInterface;
    private JTextField PortField;
    private JButton OKButton;
    private JLabel PORTLabel;
    @Getter
    private int Port;

    public PortInterface(JFrame parent){
        super(parent);
        setContentPane(PortInterface);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Port = PortField.getText().trim().isEmpty() ? null : Integer.parseInt(PortField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

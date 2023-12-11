package client.clientInterfaces;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SocketConnection extends JDialog{
    private JTextField IPField;
    private JTextField PortField;
    private JButton OKButton;
    private JLabel PORTLabel;
    private JLabel IPLabel;
    private JPanel SocketConnection;

    @Getter
    private String IpServer;
    @Getter
    private int Port;

    public SocketConnection(JFrame parent){
        super(parent);
        setContentPane(SocketConnection);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                IpServer = IPField.getText().trim().isEmpty() ? null : IPField.getText();
//                Port = PortField.getText().trim().isEmpty() ? null : Integer.parseInt(PortField.getText());
                IpServer = "localhost";
                Port = 2;
                dispose();
            }
        });
        setVisible(true);
    }
}

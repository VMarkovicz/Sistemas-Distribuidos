package client.clientInterfaces;

import lombok.Getter;

import javax.swing.*;
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
                IpServer = IPField.getText();
                Port = Integer.parseInt(PortField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

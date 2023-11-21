package server.serverInterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerInterface extends JDialog{
    private JPanel ServerInterface;
    private JLabel UsersField;
    private JLabel Label;

    public ServerInterface(JFrame parent){
        super(parent);
        setContentPane(ServerInterface);
        setMinimumSize(new Dimension(500, 500));
        Label.setSize(500, 75);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        setVisible(true);
    }
}

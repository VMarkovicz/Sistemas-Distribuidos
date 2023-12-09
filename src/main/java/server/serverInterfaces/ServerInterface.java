package server.serverInterfaces;

import server.dataTransferObject.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ServerInterface extends JFrame{
    private JPanel ServerInterface;
    private JLabel UsersField;
    private JLabel Label;
    public ConnectecIPs connectedIPs = new ConnectecIPs();

    public ServerInterface(){

        setContentPane(ServerInterface);
        setMinimumSize(new Dimension(500, 500));
        Label.setSize(500, 75);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public void setConnectedUsers(){
        StringBuilder result = new StringBuilder();
        result.append("<html>");
        for (String element : connectedIPs.getConnectedIPs()) {
            result.append("IP: ").append(element).append("<br>");
        }
        result.append("</html>");
        UsersField.setText(result.toString());
    }
}

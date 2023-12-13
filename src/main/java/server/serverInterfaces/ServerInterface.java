package server.serverInterfaces;

import javax.swing.*;
import java.awt.*;

public class ServerInterface extends JFrame{
    private JPanel ServerInterface;
    private JLabel UsersField;
    public ConnectedIPs connectedIPs = new ConnectedIPs();

    public ServerInterface(){

        setContentPane(ServerInterface);
        setMinimumSize(new Dimension(500, 500));
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

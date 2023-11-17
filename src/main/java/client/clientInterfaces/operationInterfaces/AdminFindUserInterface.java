package client.clientInterfaces.operationInterfaces;

import jwt.JWTManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminFindUserInterface extends JDialog{
    private JPanel AdminFindUser;
    private JTextField IDToFind;
    private JButton findButton;
    @Getter
    private Long idToFind;
    public AdminFindUserInterface(JFrame parent){
        super(parent);
        setContentPane(AdminFindUser);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        findButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idToFind = Long.parseLong(IDToFind.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

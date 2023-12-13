package client.clientInterfaces.operationInterfaces;

import jwt.JWTManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class AdminDeleteUserInterface extends JDialog{
    private JPanel AdminDeleteUser;
    private JTextField IDToDelete;
    private JButton deleteButton;
    @Getter
    private Long idToDelete;
    public AdminDeleteUserInterface(JFrame parent, String token){
        super(parent);
        setContentPane(AdminDeleteUser);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idToDelete = IDToDelete.getText().trim().isEmpty() ? null : Objects.equals(IDToDelete.getText(), "MY_ID") ? JWTManager.getRegistro(token) : Long.parseLong(IDToDelete.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

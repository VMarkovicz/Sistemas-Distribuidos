package client.clientInterfaces.operationInterfaces;

import jwt.JWTManager;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUpdateUserInterface extends JDialog{
    private JPanel AdminUpdateUser;
    private JTextField IDToUpdate;
    private JTextField NameField;
    private JTextField EmailField;
    private JTextField PasswordField;
    private JTextField TypeField;
    private JButton updateButton;
    @Getter
    private Long idToUpdate;
    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;
    @Getter
    private Boolean isAdmin;

    public AdminUpdateUserInterface(JFrame parent, String token){
        super(parent);
        setContentPane(AdminUpdateUser);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                idToUpdate = IDToUpdate.getText().trim().isEmpty() ? null : Long.parseLong(IDToUpdate.getText());
                name = NameField.getText().trim().isEmpty() ? null : NameField.getText();
                email = EmailField.getText().trim().isEmpty() ? null : EmailField.getText();
                password = PasswordField.getText().trim().isEmpty() ? null : PasswordField.getText();
                if(TypeField.getText().trim().isEmpty()){
                    isAdmin = JWTManager.getTipo(token);
                } else if (TypeField.getText().trim().equals("true")) {
                    isAdmin = true;
                } else if (TypeField.getText().trim().equals("false")) {
                    isAdmin = false;
                }
                dispose();
            }
        });
        setVisible(true);
    }
}

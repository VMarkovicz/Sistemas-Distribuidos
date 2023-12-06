package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginInterface extends JDialog{
    private JTextField EmailField;
    private JTextField PasswordField;
    private JButton loginButton;
    private JPanel LoginInterface;
    @Getter
    private String email;
    @Getter
    private String password;

    public LoginInterface(JFrame parent){
        super(parent);
        setContentPane(LoginInterface);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                email = EmailField.getText().trim().isEmpty() ? null : EmailField.getText();
                password = PasswordField.getText().trim().isEmpty() ? null : PasswordField.getText();
//                email = "admin@admin";
//                password = "admin";
                dispose();
            }
        });
        setVisible(true);
    }
}

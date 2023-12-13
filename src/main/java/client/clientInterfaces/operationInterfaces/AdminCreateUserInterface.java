package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateUserInterface extends JDialog {

    private JPanel AdminCreateUser;
    private JTextField NameField;
    private JTextField EmailField;
    private JTextField PasswordField;
    private JButton registerButton;
    @Getter
    private String name;
    @Getter
    private String email;
    @Getter
    private String password;

    public AdminCreateUserInterface(JFrame parent){
        super(parent);
        setContentPane(AdminCreateUser);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = NameField.getText().trim().isEmpty() ? null : NameField.getText();
                email = EmailField.getText().trim().isEmpty() ? null : EmailField.getText();
                password = PasswordField.getText().trim().isEmpty() ? null : PasswordField.getText();

                dispose();
            }
        });
        setVisible(true);
    }
}

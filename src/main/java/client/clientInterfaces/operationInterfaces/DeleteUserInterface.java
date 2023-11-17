package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteUserInterface extends JDialog{
    private JTextField EmailField;
    private JTextField PasswordField;
    private JButton deleteButton;
    private JPanel DeleteUser;
    @Getter
    private String email;
    @Getter
    private String password;

    public DeleteUserInterface(JFrame parent){
        super(parent);
        setContentPane(DeleteUser);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                email = EmailField.getText();
                password = PasswordField.getText();
                dispose();
            }
        });
        setVisible(true);
    }
}

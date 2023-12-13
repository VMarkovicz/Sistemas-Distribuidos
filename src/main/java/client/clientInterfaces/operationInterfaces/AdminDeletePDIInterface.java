package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDeletePDIInterface extends JDialog{
    private JPanel AdminDeletePDI;
    private JTextField PDIToDelete;
    private JButton deleteButton;

    @Getter
    private Long pdiToDelete;

    public AdminDeletePDIInterface(JFrame parent){
        super(parent);
        setContentPane(AdminDeletePDI);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdiToDelete = PDIToDelete.getText().trim().isEmpty() ? null : Long.parseLong(PDIToDelete.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

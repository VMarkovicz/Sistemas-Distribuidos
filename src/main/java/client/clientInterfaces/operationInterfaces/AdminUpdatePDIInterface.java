package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminUpdatePDIInterface extends JDialog{
    private JPanel AdminUpdatePDI;
    private JTextField NameField;
    private JTextField WarningField;
    private JCheckBox accessibleCheckBox;
    private JButton updateButton;
    private JTextField IDField;
    @Getter
    private Long id;
    @Getter
    private String name;
    @Getter
    private String Warning;
    @Getter
    private Boolean Accessible;

    public AdminUpdatePDIInterface(JFrame parent){
        super(parent);
        setContentPane(AdminUpdatePDI);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                id = IDField.getText().trim().isEmpty() ? null : Long.parseLong(IDField.getText());
                name = NameField.getText().trim().isEmpty() ? null : NameField.getText();
                Warning = WarningField.getText();
                Accessible = accessibleCheckBox.isSelected();
                dispose();
            }
        });
        setVisible(true);
    }
}

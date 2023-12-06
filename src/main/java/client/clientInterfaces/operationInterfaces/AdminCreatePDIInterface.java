package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreatePDIInterface extends JDialog{
    private JPanel AdminCreatePDI;
    private JTextField NameField;
    private JTextField XField;
    private JTextField YField;
    private JTextField WarningField;
    private JCheckBox accessibleCheckBox;
    private JButton createButton;
    @Getter
    private String name;
    @Getter
    private Double posX;
    @Getter
    private Double posY;
    @Getter
    private String Warning;
    @Getter
    private Boolean Accessible;

    public AdminCreatePDIInterface(JFrame parent){
        super(parent);
        setContentPane(AdminCreatePDI);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = NameField.getText().trim().isEmpty() ? null : NameField.getText();
                posX = XField.getText().trim().isEmpty() ? null : Double.parseDouble(XField.getText());
                posY = YField.getText().trim().isEmpty() ? null : Double.parseDouble(YField.getText());
                Warning = WarningField.getText();
                Accessible = accessibleCheckBox.isSelected();
                dispose();
            }
        });
        setVisible(true);
    }
}

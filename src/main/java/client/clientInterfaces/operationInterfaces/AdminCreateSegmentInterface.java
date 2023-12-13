package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminCreateSegmentInterface extends JDialog{
    private JPanel AdminCreateSegment;
    private JTextField PDI_INICIAL_Field;
    private JTextField PDI_FINAL_Field;
    private JTextField WarningField;
    private JCheckBox accessibleCheckBox;
    private JButton createButton;
    @Getter
    private Long pdi_inicial;
    @Getter
    private Long pdi_final;
    @Getter
    private String Warning;
    @Getter
    private Boolean Accessible;

    public AdminCreateSegmentInterface(JFrame parent){
        super(parent);
        setContentPane(AdminCreateSegment);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdi_inicial = PDI_INICIAL_Field.getText().trim().isEmpty() ? null : Long.parseLong(PDI_INICIAL_Field.getText());
                pdi_final = PDI_FINAL_Field.getText().trim().isEmpty() ? null : Long.parseLong(PDI_FINAL_Field.getText());
                Warning = WarningField.getText();
                Accessible = accessibleCheckBox.isSelected();
                dispose();
            }
        });
        setVisible(true);
    }
}

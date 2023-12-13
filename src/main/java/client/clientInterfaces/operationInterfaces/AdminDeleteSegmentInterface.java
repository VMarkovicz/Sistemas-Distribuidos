package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminDeleteSegmentInterface extends JDialog{
    private JPanel AdminDeleteSegment;
    private JTextField PDI_INICIAL_Field;
    private JTextField PDI_FINAL_Field;
    private JButton deleteButton;
    @Getter
    private Long pdi_inicial;
    @Getter
    private Long pdi_final;

    public AdminDeleteSegmentInterface(JFrame parent){
        super(parent);
        setContentPane(AdminDeleteSegment);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdi_inicial = PDI_INICIAL_Field.getText().trim().isEmpty() ? null : Long.parseLong(PDI_INICIAL_Field.getText());
                pdi_final = PDI_FINAL_Field.getText().trim().isEmpty() ? null : Long.parseLong(PDI_FINAL_Field.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

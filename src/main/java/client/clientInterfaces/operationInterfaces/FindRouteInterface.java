package client.clientInterfaces.operationInterfaces;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FindRouteInterface extends JDialog{
    private JTextField InitialPDIField;
    private JTextField FinalPDIField;
    private JButton findRouteButton;
    private JPanel FindRouteInterface;
    @Getter
    private Long pdi_inicial;
    @Getter
    private Long pdi_final;

    public FindRouteInterface(JFrame parent){
        super(parent);
        setContentPane(FindRouteInterface);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        findRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdi_inicial = InitialPDIField.getText().trim().isEmpty() ? null : Long.parseLong(InitialPDIField.getText());
                pdi_final = FinalPDIField.getText().trim().isEmpty() ? null : Long.parseLong(FinalPDIField.getText());
                dispose();
            }
        });
        setVisible(true);
    }
}

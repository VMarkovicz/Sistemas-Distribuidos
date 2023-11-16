package client.clientInterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OperationSelect extends JDialog{
    private JPanel OperationSelect;
    private JButton LOGINButton;
    private JButton LOGOUTButton;
    private JButton ADMINCADASTRARUSUARIOButton;
    private JButton CADASTRARUSUARIOButton;
    private JButton ADMINDELETARUSUARIOButton;
    private JButton DELETARUSUARIOButton;
    private JButton BUSCARUSUARIOButton;
    private JButton ADMINBUSCARUSUARIOSButton;
    private JButton ADMINBUSCARUSUARIOButton;
    private JButton ADMINATUALIZARUSUARIOButton;
    private JButton ATUALIZARUSUARIOButton;

    public OperationSelect(JFrame parent){
        super(parent);
        setContentPane(OperationSelect);
        setMinimumSize(new Dimension(500, 500));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ADMINCADASTRARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        CADASTRARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ADMINDELETARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        DELETARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        BUSCARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ADMINBUSCARUSUARIOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ADMINBUSCARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ADMINATUALIZARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        ATUALIZARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        setVisible(true);
    }
}

package client.clientInterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReplyInterface extends JDialog{
    private JPanel Reply;
    private JButton OKButton;
    private JLabel Message;

    public ReplyInterface(JFrame parent, String message){
        super(parent);
        setContentPane(Reply);
        setMinimumSize(new Dimension(500, 200));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Message.setText(message);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
}

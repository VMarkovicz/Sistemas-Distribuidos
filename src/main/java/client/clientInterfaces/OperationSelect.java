package client.clientInterfaces;

import client.Client;
import jwt.JWTManager;
import lombok.Getter;
import protocols.requisitions.RequisitionOp;
import server.models.User;

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
    @Getter
    private String Operation = null;

    public OperationSelect(JFrame parent){
        super(parent);
        setContentPane(OperationSelect);
        setMinimumSize(new Dimension(500, 600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        LOGINButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.LOGIN;
                dispose();
            }
        });
        LOGOUTButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.LOGOUT;
                dispose();
            }
        });
        ADMINCADASTRARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.ADMIN_CADASTRAR_USUARIO;
                dispose();
            }
        });
        CADASTRARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.CADASTRAR_USUARIO;
                dispose();
            }
        });
        ADMINDELETARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.ADMIN_DELETAR_USUARIO;
                dispose();
            }
        });
        DELETARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.DELETAR_USUARIO;
                dispose();
            }
        });
        BUSCARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.BUSCAR_USUARIO;
                dispose();
            }
        });
        ADMINBUSCARUSUARIOSButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.ADMIN_BUSCAR_USUARIOS;
                dispose();
            }
        });
        ADMINBUSCARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.ADMIN_BUSCAR_USUARIO;
                dispose();
            }
        });
        ADMINATUALIZARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.ADMIN_ATUALIZAR_USUARIO;
                dispose();
            }
        });
        ATUALIZARUSUARIOButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Operation = RequisitionOp.ATUALIZAR_USUARIO;
                dispose();
            }
        });
        setVisible(true);
    }
}

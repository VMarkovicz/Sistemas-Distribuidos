package client.clientInterfaces.operationInterfaces;

import server.dataTransferObject.RouteDTO;

import javax.swing.*;
import java.awt.*;

import java.util.List;

public class RoutesInterface extends JDialog{
    private JLabel IDField;
    private JLabel INITIAL_PDI;
    private JLabel FINAL_PDI;
    private JLabel Distance;
    private JLabel Warning;
    private JLabel Direction;
    private JPanel Routes;

    public RoutesInterface(JFrame parent, List<RouteDTO> list){
        super(parent);
        setContentPane(Routes);
        setMinimumSize(new Dimension(700, 350));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        commandListFormatted(list);
        setVisible(true);
    }

    public void commandListFormatted(List<RouteDTO> list){
        StringBuilder id = new StringBuilder();
        StringBuilder initial_pdi = new StringBuilder();
        StringBuilder final_pdi = new StringBuilder();
        StringBuilder distance = new StringBuilder();
        StringBuilder warning = new StringBuilder();
        StringBuilder direction = new StringBuilder();

        id.append("<html>ID<br>");
        initial_pdi.append("<html>INITIAL_PDI<br>");
        final_pdi.append("<html>FINAL_PDI<br>");
        distance.append("<html>Distance<br>");
        warning.append("<html>Warning<br>");
        direction.append("<html>Direction<br>");
        int i = 1;
        for (RouteDTO element : list) {
            id.append(i).append("<br>");
            initial_pdi.append(element.nome_inicio()).append("<br>");
            final_pdi.append(element.nome_final()).append("<br>");
            distance.append(element.distancia()).append("<br>");
            warning.append(element.aviso()).append("<br>");
            direction.append(element.direcao()).append("<br>");
            i++;
        }

        id.append("</html>");
        initial_pdi.append("</html>");
        final_pdi.append("</html>>");
        distance.append("</html>");
        warning.append("</html>");
        direction.append("</html>");

        IDField.setText(id.toString());
        INITIAL_PDI.setText(initial_pdi.toString());
        FINAL_PDI.setText(final_pdi.toString());
        Distance.setText(distance.toString());
        Warning.setText(warning.toString());
        Direction.setText(direction.toString());

    }
}

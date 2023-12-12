package server.controller;


import server.dataTransferObject.NodeDTO;
import server.dataTransferObject.RouteDTO;
import server.dataTransferObject.Utils.Compass;
import server.dataTransferObject.Utils.Dijkstra;
import server.dataTransferObject.Utils.Direction;
import server.exception.BadReqException;
import server.exception.NotFoundException;

import java.util.ArrayList;
import java.util.List;

public class RouteManager {
    private static RouteManager instance = null;

    public static RouteManager getInstance() {
        if (instance == null) {
            instance = new RouteManager();
        }
        return instance;
    }

    public List<RouteDTO> calculateRoute(Long pdi_inicial, Long pdi_final) throws NotFoundException, BadReqException {
        var pdi_inicial_Instance = PDIManager.getInstance().findPDI(pdi_inicial);
        var pdi_final_Instance = PDIManager.getInstance().findPDI(pdi_final);
        if(!pdi_inicial_Instance.acessivel()){
            throw new BadReqException("Initial Pdi is ano accessible.");
        }
        if(!pdi_final_Instance.acessivel()){
            throw new BadReqException("Final Pdi is ano accessible.");
        }
        return transformRoutes(Dijkstra.dijkstra(pdi_inicial, pdi_final));
    }

    private List<RouteDTO> transformRoutes(List<NodeDTO> nodeList) throws NotFoundException {
        List<RouteDTO> routeDTOList = new ArrayList<>();

        NodeDTO first = nodeList.get(0);
        Compass pastOrientation = calculateCompass(first);
        for (int i = 0; i < nodeList.size(); i++){
            if (i == 0){
                routeDTOList.add(getFirstCommand(nodeList.get(i)));
            }
            else {
                routeDTOList.add(getCommand(nodeList.get(i), pastOrientation));
                pastOrientation = calculateCompass(nodeList.get(i));
            }
        }
        return routeDTOList;
    }

    private RouteDTO getFirstCommand(NodeDTO node) throws NotFoundException {
        var pdi_inicial = PDIManager.getInstance().findPDI(node.getPdiInicial());
        var pdi_final = PDIManager.getInstance().findPDI(node.getPdiFinal());
        return new RouteDTO(pdi_inicial.nome(),
                            pdi_final.nome(),
                            node.getDistancia(),
                            node.getAviso(),
                            Direction.FRONT.getDescription());
    }

    private RouteDTO getCommand(NodeDTO node, Compass pastOrientation) throws NotFoundException {
        var pdi_inicial = PDIManager.getInstance().findPDI(node.getPdiInicial());
        var pdi_final = PDIManager.getInstance().findPDI(node.getPdiFinal());
        var direcao = calculateDirection(calculateCompass(node), pastOrientation);
        if (direcao == null){
            direcao = Direction.FRONT;
        }
        return new RouteDTO(pdi_inicial.nome(),
                            pdi_final.nome(),
                            node.getDistancia(),
                            node.getAviso(),
                            direcao.getDescription());
    }

    private Direction calculateDirection(Compass currentOrientation, Compass pastOrientation) throws NotFoundException {
        Compass[] orientations = {Compass.NORTH, Compass.EAST, Compass.SOUTH, Compass.WEST};

        // Find the indices of past and current orientations
        int pastIndex = -1;
        int currentIndex = -1;

        for (int i = 0; i < orientations.length; i++) {
            if (pastOrientation.equals(orientations[i])) {
                pastIndex = i;
            }
            if (currentOrientation.equals(orientations[i])) {
                currentIndex = i;
            }
        }

        // Calculate the turn direction
        int turn = (currentIndex - pastIndex + 4) % 4; // Adding 4 to handle negative results

        if (turn == 1) {
            return Direction.RIGHT;
        } else if (turn == 3) {
            return Direction.LEFT;
        } else if (turn == 2) {
            return Direction.FRONT;
        }
        return null;
    }

    private Compass calculateCompass(NodeDTO node) throws NotFoundException {
        var pdi_inicial = PDIManager.getInstance().findPDI(node.getPdiInicial());
        var pdi_final = PDIManager.getInstance().findPDI(node.getPdiFinal());

        double dX = pdi_final.posicao().x() - pdi_inicial.posicao().x();
        double dY = pdi_final.posicao().y() - pdi_inicial.posicao().y();

        if (dX > 0) {
            return Compass.EAST;
        } else if (dX < 0) {
            return Compass.WEST;
        } else if (dY > 0) {
            return Compass.NORTH;
        } else if (dY < 0) {
            return Compass.SOUTH;
        } else {
            return null;
        }
    }
}

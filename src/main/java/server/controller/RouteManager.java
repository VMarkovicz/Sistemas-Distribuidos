package server.controller;


import server.dataTransferObject.RouteDTO;
import server.dataTransferObject.Utils.Compass;
import server.dataTransferObject.Utils.Direction;
import server.exception.NotFoundException;
import server.models.Segment;

import java.util.ArrayList;
import java.util.List;

public class RouteManager {
    private static RouteManager instance = null;

    public static List<RouteDTO> transformRoutes(List<Segment> segmentList) throws NotFoundException {
        List<RouteDTO> routeDTOList = new ArrayList<>();

        Segment first = segmentList.get(0);
        Compass pastOrientation = calculateCompass(first);

        for (int i = 0; i < segmentList.size(); i++){
            if (i == 0){
                routeDTOList.add(getFirstCommand(segmentList.get(i)));
            }
            else {
                routeDTOList.add(getCommand(segmentList.get(i), pastOrientation));
                pastOrientation = calculateCompass(segmentList.get(i));
            }
        }
        return routeDTOList;
    }

    private static RouteDTO getFirstCommand(Segment segment) throws NotFoundException {
        var pdi_inicial = PDIManager.getInstance().findPDI(segment.getPdi_inicial());
        var pdi_final = PDIManager.getInstance().findPDI(segment.getPdi_final());
        return new RouteDTO(pdi_inicial.nome(),
                            pdi_final.nome(),
                            segment.getDistancia(),
                            segment.getAviso(),
                            Direction.FRONT);
    }

    private static RouteDTO getCommand(Segment segment, Compass pastOrientation) throws NotFoundException {
        var pdi_inicial = PDIManager.getInstance().findPDI(segment.getPdi_inicial());
        var pdi_final = PDIManager.getInstance().findPDI(segment.getPdi_final());
        return new RouteDTO(pdi_inicial.nome(),
                            pdi_final.nome(),
                            segment.getDistancia(),
                            segment.getAviso(),
                            calculateDirection(calculateCompass(segment), pastOrientation));
    }

    private static Direction calculateDirection(Compass currentOrientation, Compass pastOrientation) throws NotFoundException {
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

    public static Compass calculateCompass(Segment segment) throws NotFoundException {
        var pdi_inicial = PDIManager.getInstance().findPDI(segment.getPdi_inicial());
        var pdi_final = PDIManager.getInstance().findPDI(segment.getPdi_final());

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

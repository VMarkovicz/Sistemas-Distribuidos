package server.controller;


import server.dataTransferObject.Utils.Compass;

public class RouteManager {
    private static RouteManager instance = null;

    public static Compass calculateDirection(int x1, int y1, int x2, int y2) {
        int dX = x2 - x1;
        int dY = y2 - y1;

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

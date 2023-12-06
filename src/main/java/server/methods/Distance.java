package server.methods;

public class Distance {

    public static Double calculateDistance(Double x1, Double y1, Double x2, Double y2){
        return (Math.sqrt(Math.pow((x1-x2), 2) + Math.pow((y1-y2), 2)));
    }
}

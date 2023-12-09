package server.dataTransferObject.Utils;


public enum Direction {
    FRONT("Frente"),
    LEFT("Esquerda"),
    RIGHT("Direita");

    private final String desctiption;

    Direction(String desctiption) {
        this.desctiption = desctiption;
    }

    public String getDescription() {
        return desctiption;
    }
}


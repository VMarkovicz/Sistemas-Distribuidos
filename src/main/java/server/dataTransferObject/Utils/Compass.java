package server.dataTransferObject.Utils;

public enum Compass {
    NORTH("Norte"),
    SOUTH("Sul"),
    EAST("Leste"),
    WEST("Oeste");

    private final String desctiption;

    Compass(String desctiption) {
        this.desctiption = desctiption;
    }

    public String getDescription() {
        return desctiption;
    }
}
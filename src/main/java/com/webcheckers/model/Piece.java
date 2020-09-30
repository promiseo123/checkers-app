package com.webcheckers.model;

public class Piece {

    // --------------------------------- VARIABLES --------------------------------- //

    public enum TYPE {SINGLE, KING}
    public enum COLOR {RED, WHITE}

    private TYPE type;
    private COLOR color;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public  Piece(String type, String color) {
        if (type == "SINGLE") {
            this.type = TYPE.SINGLE;
        } else this.type = TYPE.KING;

        if (color == "RED") {
            this.color = COLOR.RED;
        } else this.color = COLOR.WHITE;
    }

    // --------------------------------- METHODS --------------------------------- //

    public TYPE getType() {
        return this.type;
    }

    public COLOR getColor() {
        return this.color;
    }
}

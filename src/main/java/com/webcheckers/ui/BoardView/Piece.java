package com.webcheckers.ui.BoardView;

public class Piece {

    // --------------------------------- VARIABLES --------------------------------- //

    public enum TYPE {SINGLE, KING}
    public enum COLOR {RED, WHITE}

    private TYPE type;
    private COLOR color;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public  Piece(Piece.TYPE type, Piece.COLOR color) {
        this.type = type;
        this.color = color;
    }

    // --------------------------------- METHODS --------------------------------- //

    public TYPE getType() {
        return this.type;
    }

    public COLOR getColor() {
        return this.color;
    }
}

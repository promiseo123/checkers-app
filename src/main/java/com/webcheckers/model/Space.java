package com.webcheckers.model;

public class Space {

    // --------------------------------- VARIABLES --------------------------------- //

    private int cellIdx;
    private COLOR color;
    private Piece piece;

    public enum COLOR {BLACK, WHITE}

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public Space(int cellIdx, String color) {
        this.cellIdx = cellIdx;

        if (color.equals("BLACK")) {
            this.color = COLOR.BLACK;
        } else this.color = COLOR.WHITE;
    }

    public Space(int cellIdx, String color, String playerColor) {
        this.cellIdx = cellIdx;

        if (color.equals("BLACK")) {
            this.color = COLOR.BLACK;
        } else this.color = COLOR.WHITE;

        if (playerColor.equals("RED")) {
            this.piece = new Piece("SINGLE", "RED");
        } else this.piece = new Piece("SINGLE", "WHITE");
    }

    // --------------------------------- METHODS --------------------------------- //

    public void setPlayer(String playerColor) {
        if (playerColor.equals("RED")) {
            this.piece = new Piece("SINGLE", "RED");
        } else this.piece = new Piece("SINGLE", "WHITE");
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public boolean isValid() {
        return this.color == COLOR.BLACK;
    }

    public Piece getPiece() {
        return this.piece;
    }
}

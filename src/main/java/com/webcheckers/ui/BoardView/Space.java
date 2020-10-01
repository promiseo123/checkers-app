package com.webcheckers.ui.BoardView;

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

    }

    // --------------------------------- METHODS --------------------------------- //

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getCellIdx() {
        return this.cellIdx;
    }

    public boolean isValid() {
        return ((this.color == COLOR.BLACK) && (this.piece == null));
    }

    public Piece getPiece() {
        return this.piece;
    }
}

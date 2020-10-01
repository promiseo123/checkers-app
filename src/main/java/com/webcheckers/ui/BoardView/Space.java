package com.webcheckers.ui.BoardView;

public class Space {

    // --------------------------------- VARIABLES --------------------------------- //

    private int cellIdx;
    private COLOR color;
    private Piece piece;

    public enum COLOR {BLACK, WHITE}

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public Space(int cellIdx, Space.COLOR color) {
        this.cellIdx = cellIdx;
        this.color = color;
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

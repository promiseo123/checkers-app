package com.webcheckers.ui.BoardView;

public class Space {

    // --------------------------------- VARIABLES --------------------------------- //

    private int cellIdx;
    private COLOR color;
    private Piece piece;

    public enum COLOR {BLACK, WHITE}

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Space: Constructor to make a Space with the given cellIdx and color
     *
     * @param cellIdx       The column number of this Space in the board
     * @param color         The color of this space
     */
    public Space(int cellIdx, Space.COLOR color) {
        this.cellIdx = cellIdx;
        this.color = color;
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * setPiece: Sets what piece is atop this board space
     *
     * @param piece     The piece atop this space
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    /**
     * getCellIdx: Returns this Space's column number in the board
     *
     * @return      this.cellIdx
     */
    public int getCellIdx() {
        return this.cellIdx;
    }

    /**
     * isValid: Returns whether this is a valid space to put a piece on
     *
     * @return      If it's a valid space
     */
    public boolean isValid() {
        return ((this.color == COLOR.BLACK) && (this.piece == null));
    }

    /**
     * getPiece: Returns the piece atop this space
     *
     * @return      this.piece
     */
    public Piece getPiece() {
        return this.piece;
    }
}

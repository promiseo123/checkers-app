package com.webcheckers.ui.BoardView;

import com.webcheckers.model.Board;

import java.util.HashMap;

/**
 * Space: Represents a single space on the Checkers board. Contains basic info about the state/identity of
 *        said Space.
 */
public class Space {

    // --------------------------------- VARIABLES --------------------------------- //

    private int rowNum;
    private int cellIdx;
    private COLOR color;
    private Piece piece;
    private Board board;

    public enum COLOR {BLACK, WHITE}

    private HashMap<String, Space> nearbySpaces;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Space: Constructor to make a Space with the given cellIdx and color
     *
     * @param cellIdx       The column number of this Space in the board
     * @param color         The color of this space
     */
    public Space(Board board, int rowNum, int cellIdx, Space.COLOR color) {
        this.board = board;
        this.rowNum = rowNum;
        this.cellIdx = cellIdx;
        this.color = color;

        this.nearbySpaces = new HashMap<>();
    }

    // --------------------------------- METHODS --------------------------------- //

    public void populateNearbySpaces() {
        if ((0 < this.rowNum) && (0 < this.cellIdx)) {
            this.nearbySpaces.put("UpLeft", this.board.getSpaceAt(this.rowNum-1, this.cellIdx-1));
        }

        if ((0 < this.rowNum) && (this.cellIdx < 7)) {
            this.nearbySpaces.put("UpRight", this.board.getSpaceAt(this.rowNum-1, this.cellIdx+1));
        }

        if ((this.rowNum < 7) && (0 < this.cellIdx)) {
            this.nearbySpaces.put("DownLeft", this.board.getSpaceAt(this.rowNum+1, this.cellIdx-1));
        }

        if ((this.rowNum < 7) && (this.cellIdx < 7)) {
            this.nearbySpaces.put("DownRight", this.board.getSpaceAt(this.rowNum+1, this.cellIdx+1));
        }
    }



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

    public boolean isInRange(Space space) {
        return (this.nearbySpaces.containsValue(space));
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

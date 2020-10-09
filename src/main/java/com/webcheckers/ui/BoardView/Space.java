package com.webcheckers.ui.BoardView;

import com.webcheckers.model.Board;

import java.util.HashMap;

/**
 * Space: Represents a single space on the Checkers board. Contains basic info about the state/identity of
 *        said Space.
 *
 * @author: Anthony DelPrincipe ajd6295
 */
public class Space {

    // --------------------------------- VARIABLES --------------------------------- //

    private int rowNum;
    private int cellIdx;
    private COLOR color;
    private Piece piece;
    private Board board;

    public enum COLOR {BLACK, WHITE}

    // A HashMap to keep track of the four Spaces in the immediate diagonal directions
    // if they exist, the keys will be "UpLeft", "UpRight", "DownLeft", "DownRight"
    private HashMap<String, Space> nearbySpaces;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Space: Constructor to make a Space with the given cellIdx and color
     *
     * @param board         The board that this Space is a part of
     * @param rowNum        The number of the Row that this space is in
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

    // --------------------------------- PUBLIC METHODS --------------------------------- //

    /**
     * populateNearbySpaces: Fills up the nearbySpaces HashMap with the surrounding four spaces
     *                       if applicable (doesn't add appropriate Spaces if the Space is on
     *                       on edge/corner)
     */
    public void populateNearbySpaces() {

        // For each fo the four diagonals, it checks to make sure isn't on the edge/row, then adds
        // the neighbor if there's a space there

        // Checks it's not on top row or left column
        if ((0 < this.rowNum) && (0 < this.cellIdx)) {
            this.nearbySpaces.put("UpLeft", this.board.getSpaceAt(this.rowNum-1, this.cellIdx-1));
        }

        // Checks it's not on top row or right column
        if ((0 < this.rowNum) && (this.cellIdx < 7)) {
            this.nearbySpaces.put("UpRight", this.board.getSpaceAt(this.rowNum-1, this.cellIdx+1));
        }

        // Checks it's not on bottom row or left column
        if ((this.rowNum < 7) && (0 < this.cellIdx)) {
            this.nearbySpaces.put("DownLeft", this.board.getSpaceAt(this.rowNum+1, this.cellIdx-1));
        }

        // Checks it's not on bottom row or right column
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

    /**
     * isInRange: Checks to see if the Space passed in via "space" parameter is a nearby Space
     *
     * @param space     The Space to see if we're near
     * @return          If we're near the space (it's one of our immediate diagonal neighbors)
     */
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

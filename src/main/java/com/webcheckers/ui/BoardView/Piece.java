package com.webcheckers.ui.BoardView;

/**
 * Piece: Represents a single piece on a game board, along with all related functionality and info.
 */
public class Piece {

    // --------------------------------- VARIABLES --------------------------------- //

    private TYPE type;
    private COLOR color;

    public enum TYPE {SINGLE, KING}
    public enum COLOR {RED, WHITE}

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Piece: Constructor to initialize a game piece with a given type and color
     *
     * @param type      The type of this piece being created
     * @param color     The color of this piece being created
     */
    public  Piece(Piece.TYPE type, Piece.COLOR color) {
        this.type = type;
        this.color = color;
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * getType: Returns the type of this Piece
     *
     * @return      this.type
     */
    public TYPE getType() {
        return this.type;
    }

    /**
     * getColor: Returns the color of this Piece
     *
     * @return      this.color
     */
    public COLOR getColor() {
        return this.color;
    }
}

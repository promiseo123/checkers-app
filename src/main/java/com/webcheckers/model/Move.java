package com.webcheckers.model;

import com.webcheckers.ui.BoardView.Space;

/**
 * Move: A class to represent a move made by the player
 *
 * @author: Anthony DelPrincipe ajd6295
 */
public class Move {

    // --------------------------------- VARIABLES --------------------------------- //

    private Position start;
    private Position end;

    public enum TYPE {SIMPLE, MULTI}

    private Move.TYPE type;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Move: Constructor to make a move, takes in the start and end Positions as parameters
     *
     * @param start     The starting position of this move
     * @param end       The ending position of this move
     */
    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    // --------------------------------- PUBLIC METHODS --------------------------------- //

    /**
     * setType: Denote the Move as a simple or multi (piece-taking) move
     *
     * @param type      The type of move (SIMPLE/MULTI) this Move should be denoted as
     */
    public void setType(Move.TYPE type) {
        this.type = type;
    }

    /**
     * getStart: Returns the start position of this Position
     *
     * @return      The start Position of this move
     */
    public Position getStart() {
        return this.start;
    }

    /**
     * getStart: Returns the row of the start position of this Position
     *
     * @return      The row of the start Position of this move
     */
    public int getStartRow() {
        return this.start.getRow();
    }

    /**
     * getStart: Returns the cell of the start position of this Position
     *
     * @return      The cell of the start Position of this move
     */
    public int getStartCell() {
        return this.start.getCell();
    }

    /**
     * getEnd: Returns the end position of this Position
     *
     * @return      The end Position of this move
     */
    public Position getEnd() {
        return this.end;
    }

    /**
     * getEnd: Returns the row of the end position of this Position
     *
     * @return      The row of the end Position of this move
     */
    public int getEndRow() {
        return this.end.getRow();
    }

    /**
     * getEnd: Returns the cell of the end position of this Position
     *
     * @return      The cell of the end Position of this move
     */
    public int getEndCell() {
        return this.end.getCell();
    }

    /**
     * getType: Returns the type of Move this is
     *
     * @return      this.type
     */
    public TYPE getType() {
        return this.type;
    }

    /**
     * typeIs: Returns true or false for if this move's TYPE is equal to the inputted TYPE
     *
     * @param type      The TYPE being compared against
     * @return          If they match or not
     */
    public boolean typeIs(Move.TYPE type) {
        return (this.type == type);
    }

}

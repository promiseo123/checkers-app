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
     * getEnd: Returns the end position of this Position
     *
     * @return      The end Position of this move
     */
    public Position getEnd() {
        return this.end;
    }

    /**
     * getType: Returns the type of Move this is
     *
     * @return      this.type
     */
    public TYPE getType() {
        return this.type;
    }

}

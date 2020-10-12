package com.webcheckers.model;

/**
 * Position: A class to represent a position on the board where the row/cell variables corrospond to a Space
 *           location on the board
 *
 * @author: Anthony DelPrincipe ajd6295
 */
public class Position {

    // --------------------------------- VARIABLES --------------------------------- //

    private int row;
    private int cell;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Position: Constructor to make an instance of a Position
     *
     * @param row       The row number this Position denotes
     * @param cell      The cell number this Position denotes
     */
    public Position(int row, int cell) {
        this.row = row;
        this.cell = cell;
    }

    // --------------------------------- PUBLIC METHODS --------------------------------- //

    /**
     * getRow: Returns the row number of this Position
     *
     * @return      This Position's row number
     */
    public int getRow() {
        return this.row;
    }

    /**
     * getCell: Returns the cell number of this Position
     *
     * @return      This Position's cell number
     */
    public int getCell() {
        return this.cell;
    }

}

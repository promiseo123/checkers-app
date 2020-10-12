package com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Row: Represents a UI-tier implementation of a Row on the game of checkers that can be parsed and red
 *      by the webapp.
 */
public class Row implements Iterable {

    // --------------------------------- VARIABLES --------------------------------- //

    private int index;
    private List<Space> spaces = new ArrayList<>();

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Row: Constructor for a row of the board. Assigns the index, and then goes through each spot
     *      on the row of the same index in the Space[][] board and copies it to it's own spaces
     *      ArrayList. Uses the reverse param to determine if the ordering should be reversed.
     *
     * @param index     The index of this row in the board
     * @param board     The Space[][] board
     * @param reverse   If the order of spaces should be reversed or not
     */
    public Row(int index, Space[][] board, boolean reverse) {
        this.index = index;

         // For each of the 8 spaces, assign color based on both the row and col number
        if (reverse) {
            for (int colNum = 0; colNum < 8; colNum++) {
                spaces.add(colNum, board[index][-(colNum-7)]);
            }
        } else {
            for (int colNum = 0; colNum < 8; colNum++) {
                spaces.add(colNum, board[index][colNum]);
            }
        }


    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * getIndex: Returns the index of this row
     *
     * @return      this.index
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * getSpace: Returns the space at the specified cell index (for testing purposes)
     *
     * @param cellIdx       The index of the Space we want
     * @return              The Space at that index
     */
    public Space getSpace(int cellIdx) {
        return this.spaces.get(0);
    }

    /**
     * Iterator: Returns the iterator of the spaces ArrayList
     *
     * @return      spaces iterator
     */
    @Override
    public Iterator iterator() {
        return spaces.iterator();
    }
}

package com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable{

    // --------------------------------- VARIABLES --------------------------------- //

    private List<Row> rows = new ArrayList<>();

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * BoardView: Constructor to make an instance of the board view. Uses the Space[][] board to set it's
     *            ArrayList of rows, and uses the reverse param to determine if they need to be reversed or not
     *
     * @param board         The Space[][] board representing the Checkers game board
     * @param reverse       Whether the ordering should be reversed or not
     */
    public BoardView(Space[][] board, boolean reverse) {
        if (reverse) {
            for (int rowNum = 0; rowNum < 8; rowNum++) {
                rows.add(new Row(-(rowNum-7), board, reverse));
            }
        } else {
            for (int rowNum = 0; rowNum < 8; rowNum++) {
                rows.add(new Row(rowNum, board, reverse));
            }
        }

    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * Iterator: Returns the iterator of the rows ArrayList
     *
     * @return      rows iterator
     */
    @Override
    public Iterator iterator() {
        return rows.iterator();
    }

}

package com.webcheckers.ui.Board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable {

    // --------------------------------- VARIABLES --------------------------------- //

    int index;
    private List<Space> spaces = new ArrayList<>();

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public Row(int index, Space[][] board) {
        this.index = index;

         // For each of the 8 spaces, assign color based on both the row and col number
         for (int colNum = 0; colNum < 8; colNum++) {
             spaces.add(colNum, board[index][colNum]);
         }
    }

    // --------------------------------- METHODS --------------------------------- //

    @Override
    public Iterator iterator() {
        return spaces.iterator();
    }

    public int getIndex() {
        return this.index;
    }
}

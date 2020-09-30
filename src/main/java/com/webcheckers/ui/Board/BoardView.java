package com.webcheckers.ui.Board;

import com.webcheckers.model.Board;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BoardView implements Iterable{

    // --------------------------------- VARIABLES --------------------------------- //

    private List<Row> rows = new ArrayList<>();

    // --------------------------------- CONSTRUCTORS --------------------------------- //

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

    @Override
    public Iterator iterator() {
        return rows.iterator();
    }

}

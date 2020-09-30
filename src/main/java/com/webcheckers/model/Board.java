package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Board implements Iterable{

    // --------------------------------- VARIABLES --------------------------------- //

    private List<Row> rows = new ArrayList<>();

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public Board() {
        for (int rowNum = 0; rowNum < 8; rowNum++) {
            rows.add(new Row(rowNum));
        }
    }

    // --------------------------------- METHODS --------------------------------- //

    @Override
    public Iterator iterator() {
        return null;
    }

}

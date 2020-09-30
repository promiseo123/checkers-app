package com.webcheckers.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Row implements Iterable {

    // --------------------------------- VARIABLES --------------------------------- //

    int index;
    private List<Space> spaces = new ArrayList<>();

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public Row(int index) {
        this.index = index;
        String spaceColor;

        // For each of the 8 spaces, assign color based on both the row and col number
        for (int colNum = 0; colNum < 8; colNum++) {
            if (this.index % 2 == 0) {
                if (colNum % 2 == 0) {
                    spaceColor = "WHITE";
                } else spaceColor = "BLACK";
            } else {
                if (colNum % 2 == 0) {
                    spaceColor = "BLACK";
                } else spaceColor = "WHITE";
            }

            if (index < 3) {
                spaces.add(new Space(colNum, spaceColor, "RED"));
            } else if (index > 4) {
                spaces.add(new Space(colNum, spaceColor, "WHITE"));
            } else {
                spaces.add(new Space(colNum, spaceColor));
            }

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

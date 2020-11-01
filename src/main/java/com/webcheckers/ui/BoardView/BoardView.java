package com.webcheckers.ui.BoardView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A UI tier implementation of a game board that can be parsed and read by the webapp.
 */
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

    /**
     * getRows: Returns the lists of rows of this BoardView (for testing purposes)
     *
     * @return      this.rows
     */
    public List<Row> getRows() {
        return this.rows;
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

    /**
    For debugging only.
    Return a console-friendly representation of the board in its current state.
    LEGEND:
    -: no piece at this space
    r: single red piece
    R: red king piece
    w: single white piece
    W: white king piece
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Row row : rows) {
            for (int i = 0; i < 8; i++) {
                Piece thisPiece = row.getSpace(i).getPiece();
                if (thisPiece == null) {
                    builder.append("-");
                }
                else if (thisPiece.getColor().equals(Piece.COLOR.RED) && thisPiece.getType().equals(Piece.TYPE.SINGLE)) {
                    builder.append("r");
                }
                else if (thisPiece.getColor().equals(Piece.COLOR.RED) && thisPiece.getType().equals(Piece.TYPE.KING)) {
                    builder.append("R");
                }
                else if (thisPiece.getColor().equals(Piece.COLOR.WHITE) && thisPiece.getType().equals(Piece.TYPE.SINGLE)) {
                    builder.append("w");
                }
                else {
                    builder.append("W");
                }
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}

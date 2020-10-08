package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;

public class Board {

    // --------------------------------- VARIABLES --------------------------------- //

    private Space[][] board;
    private BoardView redView;
    private BoardView whiteView;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Board: Constructor for a board that makes all the spaces, places the initial pieces, and
     *        makes a view of said board for each color.
     */
    public Board() {
        this.board = new Space[8][8];
        constructBoard(this.board);

        this.whiteView = new BoardView(board, false);

        this.redView = new BoardView(board, true);

    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * constructBoard: Constructs the 2D matrix of Spaces. Based their color off of the row/col index, and
     *                 places down appropriately-colored pieces in the right spots
     * @param board
     */
    private void constructBoard(Space[][] board) {
        Space.COLOR spaceColor;

        for (int rowNum = 0; rowNum < 8; rowNum++) {
            for (int colNum = 0; colNum < 8; colNum++) {
                if (rowNum % 2 == 0) {
                    if (colNum % 2 == 0) {
                        spaceColor = Space.COLOR.WHITE;
                    } else spaceColor = Space.COLOR.BLACK;
                } else {
                    if (colNum % 2 == 0) {
                        spaceColor = Space.COLOR.BLACK;
                    } else spaceColor = Space.COLOR.WHITE;
                }

                if (rowNum < 3) {
                    Space newSpace = new Space(colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
                    }
                    board[rowNum][colNum] = newSpace;
                } else if (rowNum > 4) {
                    Space newSpace = new Space(colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE));
                    }
                    board[rowNum][colNum] = newSpace;
                } else {
                    Space newSpace = new Space(colNum, spaceColor);
                    board[rowNum][colNum] = newSpace;
                }
            }
        }
    }

    /**
     * getBoardView: Returns the board view for a given player color
     *
     * @param playerColor       The color of the player trying to view the board
     * @return                  The view for that colored player
     */
    public BoardView getBoardView(Player.COLOR playerColor) {
        if (playerColor == Player.COLOR.WHITE) {
            return this.whiteView;
        } else {
            return this.redView;
        }

    }
}

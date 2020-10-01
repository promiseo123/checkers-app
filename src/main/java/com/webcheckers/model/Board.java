package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;

import java.util.Arrays;

public class Board {

    private Space[][] board;
    private BoardView redView;
    private BoardView whiteView;

    public Board() {
        this.board = new Space[8][8];
        constructBoard(this.board);

        this.whiteView = new BoardView(board, false);

        this.redView = new BoardView(board, true);

    }

    private void constructBoard(Space[][] board) {
        String spaceColor;

        for (int rowNum = 0; rowNum < 8; rowNum++) {
            for (int colNum = 0; colNum < 8; colNum++) {
                if (rowNum % 2 == 0) {
                    if (colNum % 2 == 0) {
                        spaceColor = "WHITE";
                    } else spaceColor = "BLACK";
                } else {
                    if (colNum % 2 == 0) {
                        spaceColor = "BLACK";
                    } else spaceColor = "WHITE";
                }

                if (rowNum < 3) {
                    Space newSpace = new Space(colNum, spaceColor, "RED");
                    if (spaceColor.equals("BLACK")) {
                        newSpace.setPiece(new Piece("SINGLE", "RED"));
                    }
                    board[rowNum][colNum] = newSpace;
                } else if (rowNum > 4) {
                    Space newSpace = new Space(colNum, spaceColor, "WHITE");
                    if (spaceColor.equals("BLACK")) {
                        newSpace.setPiece(new Piece("SINGLE", "WHITE"));
                    }
                    board[rowNum][colNum] = newSpace;
                } else {
                    Space newSpace = new Space(colNum, spaceColor);
                    board[rowNum][colNum] = newSpace;
                }
            }
        }
    }

    public BoardView getBoardView(Player.COLOR playerColor) {
        if (playerColor == Player.COLOR.WHITE) {
            return this.whiteView;
        } else {
            return redView;
        }

    }
}

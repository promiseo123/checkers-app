package com.webcheckers.model;

import com.webcheckers.ui.Board.BoardView;
import com.webcheckers.ui.Board.Space;
import com.webcheckers.ui.Board.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Board {

    Space[][] board;
    BoardView redView;
    BoardView whiteView;

    public Board() {
        this.board = new Space[8][8];
        constructBoard(this.board);

        this.whiteView = new BoardView(board, false);
        //Collections.reverse(Arrays.asList(this.board));
        this.redView = new BoardView(board, true);
        //Collections.reverse(Arrays.asList(this.board));
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
                    board[rowNum][colNum] = new Space(colNum, spaceColor, "RED");
                } else if (rowNum > 4) {
                    board[rowNum][colNum] = new Space(colNum, spaceColor, "WHITE");
                } else {
                    board[rowNum][colNum] = (new Space(colNum, spaceColor));
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

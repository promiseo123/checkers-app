package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;

import java.util.ArrayList;

/**
 * Board: A representation of the Checkers board. Both constructs the working model-level board array
 *        as well as the UI tier Iterable of Iterables.
 */
public class Board {

    // --------------------------------- VARIABLES --------------------------------- //

    private Space[][] board;
    private BoardView redView;
    private BoardView whiteView;
    private ArrayList<Move> movesThisTurn;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Board: Constructor for a board that makes all the spaces, places the initial pieces, and
     *        makes a view of said board for each color.
     */
    public Board() {
        this.board = new Space[8][8];
        constructBoard(this.board);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col].populateNearbySpaces();
            }
        }

        this.whiteView = new BoardView(board, false);
        this.redView = new BoardView(board, true);

        this.movesThisTurn = new ArrayList<Move>();

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
                    Space newSpace = new Space(this, rowNum, colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
                    }
                    board[rowNum][colNum] = newSpace;
                } else if (rowNum > 4) {
                    Space newSpace = new Space(this, rowNum, colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE));
                    }
                    board[rowNum][colNum] = newSpace;
                } else {
                    Space newSpace = new Space(this, rowNum, colNum, spaceColor);
                    board[rowNum][colNum] = newSpace;
                }
            }
        }
    }

    public void clearMoves() {
        this.movesThisTurn.clear();
    }

    public boolean isValidMove(Move move) {
        Space startSpace = getSpaceByPosition(move.getStart());
        Space endSpace = getSpaceByPosition(move.getEnd());

        if (endSpace.isValid() && endSpace.isInRange(startSpace)) {
            if (!this.movesThisTurn.isEmpty()) {
                if (getLatestMove().getType() == Move.TYPE.SINGLE) {
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public Move getLatestMove() {
        return this.movesThisTurn.get(this.movesThisTurn.size()-1);
    }

    public void removeLatestMove() {
        this.movesThisTurn.remove(getLatestMove());
    }

    public Space getSpaceByPosition(Position position) {
        return board[position.getRow()][position.getCell()];
    }

    public Space getSpaceAt(int rowNum, int cell) {
        return this.board[rowNum][cell];
    }

    public void makeMove(Move move) {
        this.movesThisTurn.add(movesThisTurn.size(), move);

        Piece movedPiece = getSpaceByPosition(move.getStart()).getPiece();
        getSpaceByPosition(move.getStart()).setPiece(null);
        getSpaceByPosition(move.getEnd()).setPiece(movedPiece);

        updateViews();
    }

    private void updateViews() {
        this.whiteView = new BoardView(this.board, false);
        this.redView = new BoardView(this.board, true);
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

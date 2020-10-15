package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;

import java.util.ArrayList;

/**
 * Board: A representation of the Checkers board. Both constructs the working model-level board array
 *        as well as the UI tier Iterable of Iterables.
 *
 * @author: Anthony DelPrincipe ajd6295
 */
public class Board {

    // --------------------------------- VARIABLES --------------------------------- //

    private Space[][] board;
    private BoardView redView;
    private BoardView whiteView;

    // This array holds a running list of all valid moves the player has made but not submitted
    private ArrayList<Move> movesThisTurn;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Board: Constructor for a board that makes all the spaces, places the initial pieces, and
     *        makes a view of said board for each color.
     */
    public Board() {

        // Make board and create initial states
        this.board = new Space[8][8];
        constructBoard(this.board);
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                board[row][col].populateNearbySpaces();
            }
        }

        // Make the views for each player
        this.whiteView = new BoardView(board, true);
        this.redView = new BoardView(board, false);

        this.movesThisTurn = new ArrayList<Move>();

    }

    // --------------------------------- PUBLIC METHODS --------------------------------- //

    /**
     * clearMoves: clears the list of moves made so far this turn
     */
    public void clearMoves() {
        this.movesThisTurn.clear();
    }

    /**
     * isValidMove: Tests if the move proposed is a valid one or not. Returns an int (0 if valid, other number if not,
     *              and the resulting error message will be figured out based on what number it is in the calling function)
     *
     * @param move      The move that is trying to be made
     * @return          integer "error code" depending on what happened
     */
    public int isValidMove(Move move) {
        Space startSpace = getSpaceByPosition(move.getStart());
        Space endSpace = getSpaceByPosition(move.getEnd());

        // If the space is valid and the end space is reachable
        // -----
        // NOTE: If you are making a multi-jump thing, the "isInRange" check is
        // what you'll wanna change - it currently returns true ONLY if the
        // end space is one of the four squares directly around it (see nearbySpaces
        // variable and populateNearbySpaces() method in the Space class).
        // Delete this huge comment after you implement except that top line :)
        if (endSpace.isValid() && endSpace.isInRange(startSpace)) {

            // We're able to move to the space - fantastic!
            // Check to make sure we can still make a move
            // -----
            // This guards against someone moving a piece one square, us saying
            // "yeah that's a valid move," and them making another move from said
            // space on the same turn. i.e. if we made a single move, then tried to immediately
            // take that piece and move it without submitting our turn
            if (!this.movesThisTurn.isEmpty()) {

                // This check is in place to make double jumps easier - you can do multiple
                // piece-taking jumps in a row, so this check lets you do another move if the last
                // one was a piece-taking MULTI move
                if (getLatestMove().getType() == Move.TYPE.SIMPLE) {
                    return 2;
                }
//                else return 0;

            }

            // We could be dealing with a simple move - we don't want them able to move "backwards"
            if (move.getType().equals(Move.TYPE.SIMPLE)) {
                if (!endSpace.isInRangeSimple(startSpace, startSpace.getPiece().getColor())) {
                    return 3;
                }
            }
            if (move.getType().equals(Move.TYPE.MULTI)) {
                if (!endSpace.isInRange(startSpace)) {

                }
            }

            // If we got here it means we haven't already moved the piece, so of course we can move it now
            return 0;
        }

        // Space wasn't valid or end space wasn't reachable
        return 1;
    }

    /**
     * makeMove: Updates the board's list of current moves and actually makes the move
     *
     * @param move      The move being made
     */
    public void makeMove(Move move) {

        // Make sure we record the move being made in case we need to undo it or something
        this.movesThisTurn.add(movesThisTurn.size(), move);
        if (move.getType()== Move.TYPE.SIMPLE) {
            // "Take" the piece from the start space and move it to the end space
            Piece movedPiece = getSpaceByPosition(move.getStart()).getPiece();
            getSpaceByPosition(move.getStart()).setPiece(null);
            getSpaceByPosition(move.getEnd()).setPiece(movedPiece);
        } else if (move.getType()== Move.TYPE.MULTI) {
            Piece movedPiece = getSpaceByPosition(move.getStart()).getPiece();
//            if (getSpaceAt(move.getStart().getRow()-1, move.getStart().getCell()-1).getPiece().getColor()==)
//            if (move.getEnd().getRow()==move.getStart().getRow()-2) {
                getSpaceAt(move.getStart().getRow()-1,(move.getStart().getCell()+
                        move.getEnd().getCell())/2).setPiece(null);
            System.out.println(getSpaceAt(move.getStart().getRow()-1,(move.getStart().getCell()+
                    move.getEnd().getCell())/2));
            getSpaceByPosition(move.getStart()).setPiece(null);
            getSpaceByPosition(move.getEnd()).setPiece(movedPiece);
//            }
//            else if (move.getEnd().getRow()==move.getStart().getRow()+2) {
//                getSpaceAt(move.getStart().getRow()+1,(move.getStart().getCell()+
//                        move.getStart().getCell())/2).setPiece(null);
//            }
        }


        // Make sure the views are updated so the players can see what happened
        updateViews();
    }

    /**
     * getLatestMove: Returns the latest move made this turn
     *
     * @return      Latest move (end of movesThisTurn array list)
     */
    public Move getLatestMove() {
        return this.movesThisTurn.get(this.movesThisTurn.size()-1);
    }

    /**
     * removeLatestMoves: Removes as many moves as are specified by the paramater "num"
     *
     * @param num:      How many moves to remove
     */
    public void removeLatestMoves(int num) {
        for (int i = 0; i < num; i++) {
            this.movesThisTurn.remove(getLatestMove());
        }

    }

    /**
     * getSpaceAt: Returns the space at the specified row and cell index
     *
     * @param rowNum    The row of the Space to get
     * @param cell      The cell index of the Space to get
     * @return          The cell at the specified row number and cell index
     */
    public Space getSpaceAt(int rowNum, int cell) {
        return this.board[rowNum][cell];
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

    /**
     * getBoard: Returns the board model fo the Board class (for testing purposes)
     *
     * @return      this.board
     */
    public Space[][] getBoard() {
        return this.board;
    }

    /**
     * getMoves: Returns the list of moves made so far (for testing purposes)
     *
     * @return      this.getMoves
     */
    public ArrayList<Move> getMoves() {
        return this.movesThisTurn;
    }

    // --------------------------------- PRIVATE METHODS --------------------------------- //

    /**
     * getSpaceByPosition: Return the space at the specified row number and cell index of the Position parameter
     *
     * @param position      The position who's row number and cell index we want the space at
     * @return              The Space at the row/cell index of the Position object
     */
    private Space getSpaceByPosition(Position position) {
        return board[position.getRow()][position.getCell()];
    }

    /**
     * constructBoard: Constructs the 2D matrix of Spaces. Based their color off of the row/col index, and
     *                 places down appropriately-colored pieces in the right spots
     *
     * @param board     The board to constuct
     */
    private void constructBoard(Space[][] board) {
        Space.COLOR spaceColor;

        // So, for each row...
        for (int rowNum = 0; rowNum < 8; rowNum++) {

            // For each cell in that row...
            for (int colNum = 0; colNum < 8; colNum++) {

                // Make the space white or black depending on the even/oddness of the coordinates
                if (rowNum % 2 == 0) {
                    if (colNum % 2 == 0) {
                        spaceColor = Space.COLOR.WHITE;
                    } else spaceColor = Space.COLOR.BLACK;
                } else {
                    if (colNum % 2 == 0) {
                        spaceColor = Space.COLOR.BLACK;
                    } else spaceColor = Space.COLOR.WHITE;
                }

                // Put pieces where we should (initial starting layout of board)
                if (rowNum < 3) {
                    Space newSpace = new Space(this, rowNum, colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE));
                    }
                    board[rowNum][colNum] = newSpace;
                } else if (rowNum > 4) {
                    Space newSpace = new Space(this, rowNum, colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
                    }
                    board[rowNum][colNum] = newSpace;
                } else {
                    Space newSpace = new Space(this, rowNum, colNum, spaceColor);
                    board[rowNum][colNum] = newSpace;
                }
            }
        }
    }

    /**
     * updateViews: Updates the BoardViews of the red and white player to match the current Board layout
     */
    private void updateViews() {
        if (this.whiteView.equals(new BoardView(this.board, false)) &&
                this.redView.equals(new BoardView(this.board, true))) {
            this.whiteView = new BoardView(this.board, true);
            this.redView = new BoardView(this.board, false);
        } else {
            this.whiteView = new BoardView(this.board, false);
            this.redView = new BoardView(this.board, true);
        }

    }


}

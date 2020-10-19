package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;
import com.webcheckers.ui.PostValidateMoveRoute;

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

        // Check this before anything else
        // Regardless of what move they're making now, check to make sure
        // they're actually allowed to make a move right now
        // (No MULTIs after SIMPLE, no SIMPLEs after MULTI)
        if (!this.movesThisTurn.isEmpty()) {

            // If we've gotten here, we know *a move* has already been made
            // For MULTI, this is allowed if the previous move was also a MULTI, but not
            // if prev. move was SIMPLE.
            // For SIMPLE moves, this is never allowed - only one SIMPLE move per turn!
            if ((move.typeIs(Move.TYPE.MULTI) && getLatestMove().getType() == Move.TYPE.SIMPLE)
            || move.typeIs(Move.TYPE.SIMPLE))

            // Error code for trying to move again after already making SIMPLE move
            return 2;

        }

        // Check to make sure the destination square is even in the range to begin with
        if (!endSpace.isInRange(startSpace)) {

            // Return error code for moving too far away
            return 1;

        }

        // Fantastic - they're allowed to make a move and the end space is in a logical range.
        // Do Move.TYPE specific logic handling
        if (move.typeIs(Move.TYPE.SIMPLE)) {

            // Check to make sure the end piece is in range
            // Red pieces: End space is UpRight or UpLeft
            // White pieces: End space is DownRight or DownLeft
            if (!endSpace.isInRangeSimple(startSpace, startSpace.getPiece().getColor())) {

                // Error code for if it wasn't in range (tried to move backwards)
                return 3;

            } else {

                // Code for a successful move
                // Made a simple move and it was in range
                return 0;

            }

        } else if (move.typeIs(Move.TYPE.MULTI)) {

            // Check to make sure the end piece is in range
            // Red pieces: End space is UpRightTwice or UpLeftTwice
            // White pieces: End space is DownRightTwice or DownLeftTwice
            if (!endSpace.isInRangeMulti(startSpace, startSpace.getPiece().getColor())) {

                // Error code for if it wasn't in range (tried to move backwards)
                return 3;

            } else {

                // --------------------------------------------------------------------
                // In here, you probably have to check to make sure that the space they're jumping over
                // actually has a piece on it! Otherwise they're just randomly jumping two
                // spaces for no reason and that shouldn't be allowed.
                // Probably add an error code 4 for if they try to make a MULTI move
                // when there is no piece in the space they're jumping over
                // (errCode messages found in PostValidateMoveRoute)
                // For now, I'm just saying it's valid no matter what
                // --------------------------------------------------------------------
                if ((getSpaceAt((endSpace.getRowNum()+startSpace.getRowNum())/2,
                        (endSpace.getCellIdx()+startSpace.getCellIdx())/2).getPiece()==null) ||
                        (getSpaceAt((endSpace.getRowNum()+startSpace.getRowNum())/2,
                        (endSpace.getCellIdx()+startSpace.getCellIdx())/2).getPiece().getColor()
                                ==startSpace.getPiece().getColor())) {
                    // There is no opponent piece to take!
                    return 4;
                }
                // It was a valid MULTI move! Fantastic.
                return 0;

            }

        } else {

            // Move didn't have a type! Must mean that they tried to move really far away
            // (See code in PostValidateMoveRoute)
            // Return error code for moving too far
            return 1;

        }

    }

    /**
     * makeMove: Updates the board's list of current moves and actually makes the move
     *
     * @param move      The move being made
     */
    public void makeMove(Move move) {

        // Make sure we record the move being made in case we need to undo it or something
        this.movesThisTurn.add(movesThisTurn.size(), move);

        // This code needs to be executed whether it was a SIMPLE or MULTI move;
        // it simply moves the actual piece. The only extra logic handling
        // if for a MULTI move, since you have to worry about the opponent's
        // piece being taken
        Piece movedPiece = getSpaceByPosition(move.getStart()).getPiece();
        getSpaceByPosition(move.getStart()).setPiece(null);
        getSpaceByPosition(move.getEnd()).setPiece(movedPiece);

        // Logic handling for actually taking a piece if it was a multi move goes in here
        if (move.getType()== Move.TYPE.MULTI) {

            // Your code that you're working on.
            // If you do my suggestion above, (about taking the prev.
            // three lines in the first if clause out of said clause)
            // this if clause would ONLY contain code for removing the piece
            // jumped over. Careful with recording that info so that, if they
            // decide to undo the move, the taken piece will appear again!
            // See my "movesThisTurn" list if you dunno what I'm
            // talking about
            // check if it is an undoMove
            if (getSpaceAt(move.getStart().getRow()-1,(move.getStart().getCell()+
                    move.getEnd().getCell())/2).getPiece()==null) {
                // check if RED player is backing up
                if (movedPiece.getColor()== Piece.COLOR.RED) {
                    // return the opponent WHITE piece originally taken back to its position
                    getSpaceAt(move.getStart().getRow()-1,(move.getStart().getCell()+
                            move.getEnd().getCell())/2).setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE));
                } else {
                    // WHITE player is backing up so return RED piece back
                    getSpaceAt(move.getStart().getRow()-1,(move.getStart().getCell()+
                            move.getEnd().getCell())/2).setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
                }

            } else {
                // it is a forward MULTI move so take the piece
                getSpaceAt(move.getStart().getRow()-1,(move.getStart().getCell()+
                        move.getEnd().getCell())/2).setPiece(null);
            }



//            getSpaceByPosition(move.getStart()).setPiece(null);
//            getSpaceByPosition(move.getEnd()).setPiece(movedPiece);

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

        this.whiteView = new BoardView(this.board, true);
        this.redView = new BoardView(this.board, false);

    }


}

package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * BoardTest: Class to test the Board class works correctly
 *
 * @author: ajd6295
 */
@Tag("Model-tier")
public class BoardTest {

    // Component under testing
    private Board CuT;

    @BeforeEach
    public void setup() {
        CuT = new Board();
    }

    /**
     * test_make_board: Tests the creation of a Board class
     */
    @Test
    public void test_make_board() {

        // Analyze if the board was made correctly
        assertNotNull(CuT);
        assertNotNull(CuT.getBoard());

        // Make sure that the BoardViews are correct
        assertNotNull(CuT.getBoardView(Player.COLOR.RED));
        assertNotNull(CuT.getBoardView(Player.COLOR.WHITE));
        assertNotEquals(CuT.getBoardView(Player.COLOR.RED), CuT.getBoardView(Player.COLOR.WHITE));
    }

    /**
     * test_board_setup: Makes sure that the board is set up by checking Spaces at certain
     *                   coordinates against the space we know should be there
     */
    @Test
    public void test_board_setup() {

        // Make sure that select representative spaces are correct
        // Space 1
        assertEquals(CuT.getSpaceAt(0, 0).getCellIdx(),
                new Space(CuT, 0, 0, Space.COLOR.WHITE).getCellIdx());
        assertEquals(CuT.getSpaceAt(0, 0).getPiece(),
                new Space(CuT, 0, 0, Space.COLOR.WHITE).getPiece());

        // Space 1
        assertEquals(CuT.getSpaceAt(4, 7).getCellIdx(),
                new Space(CuT, 4, 7, Space.COLOR.BLACK).getCellIdx());
        assertEquals(CuT.getSpaceAt(4, 7).getPiece(),
                new Space(CuT, 4, 7, Space.COLOR.BLACK).getPiece());
    }

    /**
     * test_valid_move: Tests to make sure that the board lets a valid move through
     */
    @Test
    public void test_valid_move() {
        Move validMove = new Move(new Position(5, 0), new Position(4, 1));
        CuT.getBoard()[5][0].setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
        validMove.setType(Move.TYPE.SIMPLE);

        CuT.isValidMove(validMove);

        assertEquals(CuT.isValidMove(validMove), 0);
    }

    /**
     * test_invalid_move_too_far: Tests that the board doesn't let a move to a space far away go through
     */
    @Test
    public void test_invalid_move_too_far() {
        Move invalidMove = new Move(new Position(5, 0), new Position(4, 3));

        assertEquals(CuT.isValidMove(invalidMove), 1);
    }

    /**
     * test_invalid_move_already_moved: Tests that the board doesn't let a move go through
     *                                  if one has already been made
     */
    @Test
    public void test_invalid_move_already_moved() {
        Move validMove = new Move(new Position(5, 0), new Position(4, 1));
        validMove.setType(Move.TYPE.SIMPLE);
        CuT.getBoard()[5][0].setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
        CuT.isValidMove(validMove);
        CuT.makeMove(validMove);

        Move invalidMove = new Move(new Position(4, 1), new Position(3, 2));
        invalidMove.setType(Move.TYPE.SIMPLE);
        assertEquals(CuT.isValidMove(invalidMove), 2);
    }

    /**
     * test_get_moves: Makes sure that the list of moves is updated correctly as the turn goes on
     */
    @Test
    public void test_get_moves() {
        assertTrue(CuT.getMoves().isEmpty());

        Move move1 = new Move(new Position(5, 0), new Position(4, 1));
        CuT.getBoard()[5][0].setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
        CuT.makeMove(move1);
        assertFalse(CuT.getMoves().isEmpty());

        Move move2 = new Move(new Position(4, 1), new Position(3, 2));
        CuT.getBoard()[5][1].setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
        CuT.makeMove(new Move(new Position(4, 1), new Position(3, 2)));
        assertNotNull(CuT.getLatestMove());

        CuT.clearMoves();
        assertTrue(CuT.getMoves().isEmpty());

    }

}

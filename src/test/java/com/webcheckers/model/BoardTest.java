package com.webcheckers.model;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.ui.BoardView.Space;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("Model-tier")
public class BoardTest {

    @Test
    public void test_make_board() {
        final Board CuT = new Board();

        // Analyze if the board was made correctly
        assertNotNull(CuT);
        assertNotNull(CuT.getBoard());

        // Make sure that the Board View is correct
        assertNotNull(CuT.getBoardView(Player.COLOR.RED));
        assertNotNull(CuT.getBoardView(Player.COLOR.WHITE));
        assertNotEquals(CuT.getBoardView(Player.COLOR.RED), CuT.getBoardView(Player.COLOR.WHITE));
    }

    @Test
    public void test_board_setup() {
        final Board CuT = new Board();

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

    @Test
    public void test_valid_move() {
        final Board CuT = new Board();
        Move validMove = new Move(new Position(5, 0), new Position(4, 1));

        assertEquals(CuT.isValidMove(validMove), 0);
    }

    @Test
    public void test_invalid_move_too_far() {
        final Board CuT = new Board();
        Move invalidMove = new Move(new Position(5, 0), new Position(4, 3));

        assertEquals(CuT.isValidMove(invalidMove), 1);
    }

    @Test
    public void test_invalid_move_already_moved() {
        final Board CuT = new Board();

        Move validMove = new Move(new Position(5, 0), new Position(4, 1));
        validMove.setType(Move.TYPE.SIMPLE);
        CuT.isValidMove(validMove);
        CuT.makeMove(validMove);

        Move invalidMove = new Move(new Position(4, 1), new Position(3, 2));
        validMove.setType(Move.TYPE.SIMPLE);
        assertEquals(CuT.isValidMove(invalidMove), 2);
    }

    @Test
    public void test_get_moves() {
        final Board CuT = new Board();

        assertTrue(CuT.getMoves().isEmpty());

        CuT.makeMove(new Move(new Position(5, 0), new Position(4, 1)));
        assertFalse(CuT.getMoves().isEmpty());

        CuT.makeMove(new Move(new Position(4, 1), new Position(3, 2)));
        assertNotNull(CuT.getLatestMove());

        CuT.clearMoves();
        assertTrue(CuT.getMoves().isEmpty());

    }

}

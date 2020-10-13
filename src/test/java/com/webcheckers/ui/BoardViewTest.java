package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.model.Board;
import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Row;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * BoardViewTest: Class to test the BoardView class
 *
 * @author: ajd6295
 */
@Tag("UI-tier")
public class BoardViewTest {

    /**
     * test_make_board_view_nonreverse: Tests making a nonreverse BoardView, making it it actually gets created and
     *                                  that the Rows are created correctly
     */
    @Test
    public void test_make_board_view_nonreverse() {
        final Board board = new Board();
        final BoardView reverse = new BoardView(board.getBoard(), true);
        final BoardView CuT = new BoardView(board.getBoard(), false);

        assertNotNull(CuT);
        assertNotEquals(CuT, reverse);

        assertEquals(CuT.getRows().get(0).getSpace(0),
                new Row(0, board.getBoard(), false).getSpace(0));
    }

    /**
     * test_make_board_view_reverse: Tests making a reverse BoardView, making it it actually gets created and
     *                               that the Rows are created correctly
     */
    @Test
    public void test_make_board_view_reverse() {
        final Board board = new Board();
        final BoardView CuT = new BoardView(board.getBoard(), true);
        final BoardView regular = new BoardView(board.getBoard(), false);

        assertNotNull(CuT);
        assertNotEquals(CuT, regular);

        assertEquals(CuT.getRows().get(0).getSpace(0),
                new Row(7, board.getBoard(), true).getSpace(0));
    }

    /**
     * test_get_rows: Tests to make sure that the Rows are created correctly
     */
    @Test
    public void test_get_rows() {
        final Board board = new Board();
        final BoardView CuT = new BoardView(board.getBoard(), true);

        assertNotNull(CuT.getRows());

        assertNotNull(CuT.getRows().get(7));

        assertEquals(CuT.getRows().get(4).getIndex(), 3);
    }

}
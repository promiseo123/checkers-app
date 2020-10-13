package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.webcheckers.model.Board;
import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Piece;
import com.webcheckers.ui.BoardView.Row;
import com.webcheckers.ui.BoardView.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;

/**
 * BoardViewTest: Class to test the BoardView class
 *
 * @author: ajd6295
 */
@Tag("UI-tier")
public class BoardViewTest {

    // Instantiate our classes for testing
    private BoardView CuTReg;
    private BoardView CuTRev;

    // UuT variables. They're essentially psuedo-objects that mock the functionality.
    private Board board;

    @BeforeEach
    public void setup() {
        board = mock(Board.class);

        // --------------------------------------- Long code to manually make board

        Space[][] spaceBoard = new Space[8][8];
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
                    Space newSpace = new Space(board, rowNum, colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.WHITE));
                    }
                    spaceBoard[rowNum][colNum] = newSpace;
                } else if (rowNum > 4) {
                    Space newSpace = new Space(board, rowNum, colNum, spaceColor);
                    if (spaceColor == Space.COLOR.BLACK) {
                        newSpace.setPiece(new Piece(Piece.TYPE.SINGLE, Piece.COLOR.RED));
                    }
                    spaceBoard[rowNum][colNum] = newSpace;
                } else {
                    Space newSpace = new Space(board, rowNum, colNum, spaceColor);
                    spaceBoard[rowNum][colNum] = newSpace;
                }
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                spaceBoard[row][col].populateNearbySpaces();
            }
        }

        // ------------------------------------------------ End long code to manually make board

        when(board.getBoard()).thenReturn(spaceBoard);

        CuTReg = new BoardView(board.getBoard(), false);
        CuTRev = new BoardView(board.getBoard(), true);
    }

    /**
     * test_make_board_view_nonreverse: Tests making a nonreverse BoardView, making it it actually gets created and
     *                                  that the Rows are created correctly
     */
    @Test
    public void test_make_board_view_nonreverse() {

        assertNotNull(CuTReg);
        assertNotEquals(CuTReg, CuTRev);

        assertEquals(CuTReg.getRows().get(0).getSpace(0),
                new Row(0, board.getBoard(), false).getSpace(0));
    }

    /**
     * test_make_board_view_reverse: Tests making a reverse BoardView, making it it actually gets created and
     *                               that the Rows are created correctly
     */
    @Test
    public void test_make_board_view_reverse() {

        assertNotNull(CuTRev);
        assertNotEquals(CuTRev, CuTReg);

        assertEquals(CuTRev.getRows().get(0).getSpace(0),
                new Row(7, board.getBoard(), true).getSpace(0));
    }

    /**
     * test_get_rows: Tests to make sure that the Rows are created correctly
     */
    @Test
    public void test_get_rows() {

        assertNotNull(CuTRev.getRows());

        assertNotNull(CuTRev.getRows().get(7));

        assertEquals(CuTRev.getRows().get(4).getIndex(), 3);
    }

}

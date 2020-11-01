package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;
import com.webcheckers.ui.BoardView.Space;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * GameReplayTester: Class to test the GameReplay class
 *
 * @author: Austin Cepalia (acc5989)
 */
@Tag("Model-tier")
public class GameReplayTester {

    // Component under testing
    private GameReplay CuT;

    @BeforeEach
    public void setup() {
        CuT = new GameReplay();
    }

    /**
     * testMakeGameReplay: Tests the creation of a GameReplay object
     */
    @Test
    public void testMakeGameReplay() {

        // Analyze if the replay object was made correctly
        assertNotNull(CuT);
        assertEquals(0, CuT.getBoardViewCount());
    }

    /**
     * testAddBoardState: Tests the addition of a new BoardView
     */
    @Test
    public void testAddBoardState() {
        CuT.addBoardState(new BoardView(new Space[8][8], false));
        assertEquals(1, CuT.getBoardViewCount());
    }

    /**
     * testGetBoardAtState: Tests the retrieval of a BoardView from the collection
     */
    @Test
    public void testGetBordAtState() {
        BoardView bv = new BoardView(new Space[8][8], false);
        CuT.addBoardState(bv);
        BoardView retrieved_bv = CuT.getBoardViewAtState(0);
        assertEquals(bv, retrieved_bv);
    }

}

package com.webcheckers.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * Test class for Game functionality
 *
 * @author Austin Cepalia, acc5989
 */
@Tag("Model-Tier")
public class GameTester {

    private Player red_player;
    private Player white_player;
    private Game game;

    @BeforeEach
    public void setup() {
        red_player = mock(Player.class);
        white_player =  mock(Player.class);
        game = new Game("ABC123", red_player, white_player);
    }

    @Test
    public void testGetRedPlayer() {
        assertEquals(red_player, game.getRedPlayer());
    }

    @Test
    public void testGetWhitePlayer() {
        assertEquals(white_player, game.getWhitePlayer());
    }

    @Test
    public void testGetOpponent_currentRed() {
        assertEquals(white_player, game.getOpponent(red_player));
    }

    @Test
    public void testGetOpponent_currentWhite() {
        assertEquals(red_player, game.getOpponent(white_player));
    }

    @Test
    public void testIsRedPlayer() {
        assertTrue(game.isRedPlayer(red_player));
    }

    @Test
    public void testGetTurn() {
        // game always starts with red going first
        assertEquals(Game.TURN.RED, game.getTurn());
    }

    @Test
    public void testGetBoard() {
        assertNotNull(game.getBoard());
    }

    @Test
    public void testGetBoardView_red() {
        assertNotNull(game.getBoardView(Player.COLOR.RED));
    }

    @Test
    public void testGetBoardView_white() {
        assertNotNull(game.getBoardView(Player.COLOR.WHITE));
    }

    @Test
    public void testSwitchTurns() {
        game.switchTurns();
        assertEquals(Game.TURN.WHITE, game.getTurn());
        game.switchTurns();
        assertEquals(Game.TURN.RED, game.getTurn());
    }

    @Test
    public void testRemovePlayer_red() {
        game.removePlayer(red_player);
        assertNull(game.getRedPlayer());
    }

    @Test
    public void testRemovePlayer_white() {
        game.removePlayer(white_player);
        assertNull(game.getWhitePlayer());
    }

    @Test
    public void testGenerateRandomGameID() {
        String id = Game.generateRandomGameID();
        assertEquals(10, id.length());
    }

}

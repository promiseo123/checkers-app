package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for GameCenter functionality
 * NOTE: Our implementation currently has GameCenter as a static class. In order to most effectively test this class,
 * this should be changed. This test suite attempts to restore the state of the GameCenter to default after each test.
 *
 * @author Austin Cepalia, acc5989
 */
@Tag("Application-tier")
public class GameCenterTester {

    @Test
    public void testNewGame() {
        GameCenter.newGame("ABC123", new Player("p_red"), new Player("p_white"));
        assertNotNull(GameCenter.getGameByID("ABC123"));
        GameCenter.wipeAllGames();
    }

    @Test
    public void testGetGameByID_notFound() {
        GameCenter.newGame("ABC123", new Player("p_red"), new Player("p_white"));
        assertNull(GameCenter.getGameByID("ABC124"));
        GameCenter.wipeAllGames();
    }

    @Test
    public void testWipeAllGames() {
        GameCenter.newGame("ABC123", new Player("p_red"), new Player("p_white"));
        GameCenter.newGame("DEF456", new Player("p_red"), new Player("p_white"));
        GameCenter.newGame("GEH789", new Player("p_red"), new Player("p_white"));
        GameCenter.wipeAllGames();
        assertNull(GameCenter.getGameByID("ABC123"));
        assertNull(GameCenter.getGameByID("DEF456"));
        assertNull(GameCenter.getGameByID("GEH789"));
        GameCenter.wipeAllGames();
    }

    @Test
    public void testWipeGame() {
        GameCenter.newGame("ABC123", new Player("p_red"), new Player("p_white"));
        assertTrue(GameCenter.wipeGame("ABC123"));
        GameCenter.wipeAllGames();
    }

    @Test
    public void testWipeGame_gameNotFound() {
        GameCenter.newGame("ABC123", new Player("p_red"), new Player("p_white"));
        assertFalse(GameCenter.wipeGame("ABC124"));
        GameCenter.wipeAllGames();
    }

}

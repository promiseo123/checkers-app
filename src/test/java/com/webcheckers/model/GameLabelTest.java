package com.webcheckers.model;

import com.webcheckers.appl.GameCenter;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webcheckers.appl.GameCenter.getAvailableGames;
import static com.webcheckers.appl.GameCenter.newGame;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit test for GameLabel.class
 *
 * @author Promise Omiponle poo9724
 */

@Tag("Model-Tier")
public class GameLabelTest {

    @Test
    void testGetID() {
        final Player RedPlayer = new Player("Player1");
        RedPlayer.setColor(Player.COLOR.RED);
        final Player WhitePlayer = new Player("Player2");
        WhitePlayer.setColor(Player.COLOR.WHITE);
        final String testid = "12345abcd";

        RedPlayer.assignToGame(testid);
        WhitePlayer.assignToGame(testid);
        newGame(testid,RedPlayer,WhitePlayer);
        GameLabel CuT = new GameLabel(testid);
        assertEquals(testid, CuT.getGameID());
    }

    @Test
    void testGetPlayersInGame() {
        final Player RedPlayer = new Player("Player1");
        RedPlayer.setColor(Player.COLOR.RED);
        final Player WhitePlayer = new Player("Player2");
        WhitePlayer.setColor(Player.COLOR.WHITE);
        final String testid = "12345abcd";
        RedPlayer.assignToGame(testid);
        WhitePlayer.assignToGame(testid);
        newGame(testid,RedPlayer,WhitePlayer);
        GameLabel CuT = new GameLabel(testid);
        assertEquals("Player1 vs Player2", CuT.getPlayersInGame());
    }
}

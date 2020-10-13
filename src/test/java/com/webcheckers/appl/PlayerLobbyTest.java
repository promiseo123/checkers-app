package com.webcheckers.appl;

import com.webcheckers.model.Player;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for PlayerLobby functionality
 *
 * @author Promise Omipone, poo9724
 */
@Tag("Application-tier")
public class PlayerLobbyTest {

    private PlayerLobby lobby=new PlayerLobby();

    /**
     * This tests that signing in a player with an invalid name
     * returns a null Player object.
     */
    @Test
    public void testSignIn_Invalid_Name() {
        String name="newgamer@18";
        assertNull(lobby.signIn(name));

    }

    /**
     * This tests that signing in with a Player name that is
     * already taken returns a null Player object.
     */
    @Test
    public void testSignIn_Taken_Name() {
        lobby.getPlayers().add(new Player("gamer"));
        String name="gamer";
        assertNull(lobby.signIn(name));
    }

    /**
     * This tests that if a Player exists in the lobby, the getPlayer(name)
     * function will return the Payer with name as its name.
     */
    @Test
    public void testGetPlayer_Exists() {
        lobby.getPlayers().add(new Player("poggers"));
        lobby.getPlayers().add(new Player("codslayer"));
        assertEquals(new Player("poggers"), lobby.getPlayer("poggers"));
    }

    /**
     * This tests that if a certain player does not exist in the lobby,
     * getPlayer(name) returns a null Player object.
     */
    @Test
    public void testGetPlayer_Invalid() {
        lobby.getPlayers().remove(new Player("poggers"));
        assertNull(lobby.getPlayer("poggers"));
    }

    /**
     * This tests that a certain Player is not included in a new list
     * of Players in the lobby if the player is currently playing a game.
     */
    @Test
    public void testGetOthers() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.getPlayers().add(new Player("poggers"));
        lobby.getPlayers().add(new Player("codslayer"));
        List<Player> testOtherPlayers = new ArrayList<>();
        testOtherPlayers.add(new Player("poggers"));
        testOtherPlayers.add(new Player("codslayer"));
        lobby.getPlayer("gamer").playing();
        assertEquals(testOtherPlayers, lobby.getOtherPlayers("gamer"));
    }

    /**
     * This tests that if there is no other player signed in
     * the list of other players turns out as a null object.
     */
    @Test
    public void testGetOthers_Empty() {
        Player gamer=new Player("gamer");
        gamer.playing();
        lobby.getPlayers().add(gamer);
        assertNull(lobby.getOtherPlayers("gamer"));
    }

    /**
     * This tests that assigning a certain Player to a certain game
     * updates the Player's current game ID correctly.
     */
    @Test
    public void testAssignPlayerToGame() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.assignPlayerToGame("gamer", "game1");
        assertEquals("game1", lobby.getPlayer("gamer").getGameID());
    }

    /**
     * This tests that if a given Player is not currently in the lobby
     * the Player's ID returns as an empty String when it is assigned.
     */
    @Test
    public void testAssignPlayerToGame_NotInLobby() {
        lobby.getPlayers().remove(new Player("gamer"));
        lobby.assignPlayerToGame("gamer", "game1");
        lobby.getPlayers().add(new Player("gamer"));
        assertEquals("", lobby.getPlayer("gamer").getGameID());
    }

    /**
     * This tests that a Player has its inGame state changed to true
     * after being marked as playing.
     */
    @Test
    public void testMarkAsPlaying() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.markPlayerAsPlaying("gamer");
        assertTrue(lobby.getPlayer("gamer").isPlaying());
    }
    /**
     * This tests that a Player not present in the lobby has its inGame
     * state remain False after being attempted to be marked as playing.
     */
    @Test
    public void testMarkAsPlaying_NotInLobby() {
        lobby.getPlayers().remove(new Player("gamer"));
        lobby.markPlayerAsPlaying("gamer");
        lobby.getPlayers().add(new Player("gamer"));
        assertFalse(lobby.getPlayer("gamer").isPlaying());
    }

    /**
     * This tests that a Player has its inGame state changed to
     * false and its currentGameID changed to an empty string
     * after being marked as done playing in the lobby.
     */
    @Test
    public void testMarkAsDone() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.markPlayerAsDonePlaying("gamer");
        assertFalse(lobby.getPlayer("gamer").isPlaying());
        assertEquals("", lobby.getPlayer("gamer").getGameID());
    }

    /**
     * This tests that a Player is assigned the right checker piece
     * color after being marked with a specific color in the lobby.
     */
    @Test
    public void testMarkWithColor() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.markPlayerWithColor("gamer", Player.COLOR.RED);
        assertEquals(Player.COLOR.RED, lobby.getPlayer("gamer").getColor());
    }
}

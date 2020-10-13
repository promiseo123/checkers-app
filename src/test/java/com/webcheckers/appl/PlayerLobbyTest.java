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

    @Test
    public void testSignIn_Invalid_Name() {
        String name="newgamer@18";
        assertNull(lobby.signIn(name));

    }

    @Test
    public void testSignIn_Taken_Name() {
        lobby.getPlayers().add(new Player("gamer"));
        String name="gamer";
        assertNull(lobby.signIn(name));
    }

    @Test
    public void testGetPlayer_Exists() {
        lobby.getPlayers().add(new Player("poggers"));
        lobby.getPlayers().add(new Player("codslayer"));
        assertEquals(new Player("poggers"), lobby.getPlayer("poggers"));
    }

    @Test
    public void testGetPlayer_Invalid() {
        lobby.getPlayers().remove(new Player("poggers"));
        assertNull(lobby.getPlayer("poggers"));
    }

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

    @Test
    public void testGetOthers_Empty() {
        PlayerLobby lobby2=new PlayerLobby();
        Player gamer=new Player("gamer");
        gamer.playing();
        lobby2.getPlayers().add(gamer);
        assertNull(lobby.getOtherPlayers("gamer"));
    }

    @Test
    public void testAssignPlayerToGame() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.assignPlayerToGame("gamer", "game1");
        assertEquals("game1", lobby.getPlayer("gamer").getGameID());
    }

    @Test
    public void testAssignPlayerToGame_NotInLobby() {
        lobby.getPlayers().remove(new Player("gamer"));
        lobby.assignPlayerToGame("gamer", "game1");
        lobby.getPlayers().add(new Player("gamer"));
        assertEquals("", lobby.getPlayer("gamer").getGameID());
    }

    @Test
    public void testMarkAsPlaying() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.markPlayerAsPlaying("gamer");
        assertTrue(lobby.getPlayer("gamer").isPlaying());
    }

    @Test
    public void testMarkAsPlaying_NotInLobby() {
        lobby.getPlayers().remove(new Player("gamer"));
        lobby.markPlayerAsPlaying("gamer");
        lobby.getPlayers().add(new Player("gamer"));
        assertFalse(lobby.getPlayer("gamer").isPlaying());
    }

    @Test
    public void testMarkAsDone() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.markPlayerAsDonePlaying("gamer");
        assertFalse(lobby.getPlayer("gamer").isPlaying());
        assertEquals("", lobby.getPlayer("gamer").getGameID());
    }

    @Test
    public void testMarkWithColor() {
        lobby.getPlayers().add(new Player("gamer"));
        lobby.markPlayerWithColor("gamer", Player.COLOR.RED);
        assertEquals(Player.COLOR.RED, lobby.getPlayer("gamer").getColor());
    }
}

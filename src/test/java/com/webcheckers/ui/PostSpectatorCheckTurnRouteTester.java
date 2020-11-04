package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

/**
 * Unit test for PostSpectatorCheckTurnRouteTester.class
 *
 * @author Jack Thomas
 */
@Tag("UI-Tier")
public class PostSpectatorCheckTurnRouteTester {

    //Unit under Test
    private PostSpectatorCheckTurnRoute CuT;
    //parameters to be mocked
    private Request request;
    private Response response;
    private TemplateEngine engine;
    private PlayerLobby lobby;
    private Player currentPlayer;
    private Session mockSession;
    private Player redPlayer;
    private Player whitePlayer;
    private Game game;
    //friendly
    private Gson gson;

    @BeforeEach
    public void setup() {
        //mock dependencies
        game = mock(Game.class);
        request = mock(Request.class);
        response = mock(Response.class);
        mockSession = mock(Session.class);
        engine = mock(TemplateEngine.class);
        gson = new Gson();
        currentPlayer = mock(Player.class);
        redPlayer = mock(Player.class);
        whitePlayer = mock(Player.class);
        lobby = mock(PlayerLobby.class);
        GameCenter.newGame("TestID", redPlayer, whitePlayer);
        //create CuT
        CuT = new PostSpectatorCheckTurnRoute(lobby,engine,gson);
        //When()s
        when(request.queryParams("gameID")).thenReturn("TestID");
        when(request.session()).thenReturn(mockSession);
        when(mockSession.attribute("player")).thenReturn(currentPlayer);
    }

    @Test
    void testHandle() throws Exception {
        //Verify Json
        when(game.getTurn()).thenReturn(Game.TURN.WHITE);
        when(game.isRedPlayer(currentPlayer)).thenReturn(false);
        String jsonTest = gson.toJson(Message.info("false"));
        assertEquals(CuT.handle(request,response),jsonTest);
    }
}

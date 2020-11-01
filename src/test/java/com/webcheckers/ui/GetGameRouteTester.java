package com.webcheckers.ui;


import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.ui.BoardView.BoardView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GetGameRoute
 *
 * @author: Anthony DelPrincipe ajd6295
 */

@Tag("UI-Tier")
public class GetGameRouteTester {

    private GetGameRoute CuT;
    private TemplateEngine templateEngine;
    private Game game;
    private PlayerLobby lobby;
    private Player currentUser;
    private Player opponent;
    private Session session;
    private Request request;
    private Response response;
    private BoardView boardView;

    public static final String PLAYER_KEY = "player";

    @BeforeEach
    public void setup() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        currentUser = mock(Player.class);
        opponent = mock(Player.class);
        lobby = mock(PlayerLobby.class);
        game = mock(Game.class);
        boardView = mock(BoardView.class);
        templateEngine = mock(TemplateEngine.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(PLAYER_KEY)).thenReturn(currentUser);
        GameCenter.newGame("TESTGAMEID", currentUser, opponent);

        when(currentUser.getGameID()).thenReturn("TESTGAMEID");
        when(currentUser.getColor()).thenReturn(Player.COLOR.RED);
        when(currentUser.getName()).thenReturn(null);
        when(currentUser.nameEquals(null)).thenReturn(true);

        when(game.getOpponent(currentUser)).thenReturn(opponent);
        when(game.getTurn()).thenReturn(Game.TURN.RED);
        when(game.getBoardView(Player.COLOR.RED)).thenReturn(boardView);

        CuT = new GetGameRoute(new Gson(), lobby, templateEngine);
    }

    @Test
    void testInvalidCreateParams() {
        // Credit to Jack Thomas
        assertThrows(NullPointerException.class, () -> {
            new GetGameRoute(null, null, null);
        });
    }

    @Test
    public void testHandleNoUser() throws Exception {
        currentUser = null;
        assertNull(CuT.handle(request, response));
    }

    @Test
    public void testHandleNotPlaying() throws Exception {
        when(currentUser.readyToPlay()).thenReturn(false);
        when(currentUser.isPlaying()).thenReturn(false);

        assertNull(CuT.handle(request, response));
    }

    @Test
    public void testHandleStartGame() throws Exception {
        when(currentUser.readyToPlay()).thenReturn(true);
        when(currentUser.isPlaying()).thenReturn(false);

        CuT.handle(request, response);

        assertFalse(currentUser.isWaiting());
    }

    @Test
    public void testHandleResigned() throws Exception {
        when(currentUser.readyToPlay()).thenReturn(false);
        when(currentUser.isPlaying()).thenReturn(true);
        when(opponent.isPlaying()).thenReturn(false);

        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", opponent.getName() + " has resigned.");
        Gson g = new Gson();

        testHelper.assertViewModelAttribute("modeOptionsAsJSON", g.toJson(modeOptions));
    }

    @Test
    public void testHandleGameEnd() throws Exception {
        when(currentUser.readyToPlay()).thenReturn(false);
        when(currentUser.isPlaying()).thenReturn(true);
        when(opponent.isPlaying()).thenReturn(true);
        GameCenter.getGameByID("TESTGAMEID").isOver(true);
        when(currentUser.stateEquals(Player.STATE.WON)).thenReturn(true);

        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        // Test currentUser won ----------------------------------------------
        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        final Map<String, Object> modeOptions = new HashMap<>(2);
        modeOptions.put("isGameOver", true);
        modeOptions.put("gameOverMessage", "You won! You have captured all of the opponent's pieces.");
        Gson g = new Gson();

        testHelper.assertViewModelAttribute("modeOptionsAsJSON", g.toJson(modeOptions));

        // Test currentUser lost ----------------------------------------------
        when(currentUser.stateEquals(Player.STATE.WON)).thenReturn(false);

        CuT.handle(request, response);

        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        final Map<String, Object> modeOptions2 = new HashMap<>(2);
        modeOptions2.put("isGameOver", true);
        modeOptions2.put("gameOverMessage", "You lost. Your opponent has captured all of your pieces.");

        testHelper.assertViewModelAttribute("modeOptionsAsJSON", g.toJson(modeOptions2));
    }

}

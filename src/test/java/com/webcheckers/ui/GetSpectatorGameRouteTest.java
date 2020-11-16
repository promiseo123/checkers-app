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
import spark.Request;
import spark.Response;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit tests for GetSpectatorGameRoute
 *
 * @author: Promise Omiponle poo9724
 */

@Tag("UI-Tier")
public class GetSpectatorGameRouteTest {
    private GetSpectatorGameRoute CuT;
    private TemplateEngine templateEngine;
    private Game game;
    private PlayerLobby lobby;
    private Player currentUser;
    private Player currentTurn;
    private Player opponent;
    private Session session;
    private Request request;
    private Response response;
    private BoardView boardView;

    public static final String PLAYER_KEY = "player";
    public static final String GAME_ID_PARAM = "gameID";

    @BeforeEach
    public void setup() {
        response = mock(Response.class);
        request = mock(Request.class);
        session = mock(Session.class);
        currentUser = mock(Player.class);
        currentTurn = mock(Player.class);
        opponent = mock(Player.class);
        lobby = mock(PlayerLobby.class);
        game = mock(Game.class);
        boardView = mock(BoardView.class);
        templateEngine = mock(TemplateEngine.class);

        when(request.session()).thenReturn(session);
        when(session.attribute(PLAYER_KEY)).thenReturn(currentUser);
        GameCenter.newGame("TESTGAMEID", currentTurn, opponent);

        when(currentTurn.getGameID()).thenReturn("TESTGAMEID");
        when(currentTurn.getColor()).thenReturn(Player.COLOR.RED);
        when(currentTurn.getName()).thenReturn(null);
        when(currentTurn.nameEquals(null)).thenReturn(true);

        when(game.getOpponent(currentTurn)).thenReturn(opponent);
        when(game.getTurn()).thenReturn(Game.TURN.RED);
        when(game.getBoardView(Player.COLOR.RED)).thenReturn(boardView);

        CuT = new GetSpectatorGameRoute(new Gson(), lobby, templateEngine);
    }

    @Test
    void testInvalidCreateParams() {
        // Credit to Jack Thomas
        assertThrows(NullPointerException.class, () -> {
            new GetSpectatorGameRoute(null, null, null);
        });
    }

    @Test
    public void testHandleNoUser() throws Exception {
        // Credit to Anthony DelPrincipe
        currentUser = null;
        assertNull(CuT.handle(request, response));
    }

    @Test
    public void testHandleNotSpectating() throws Exception {
        when(currentUser.readyToPlay()).thenReturn(false);
        when(currentUser.isSpectating()).thenReturn(false);

        assertNull(CuT.handle(request, response));
    }
}

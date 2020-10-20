package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for PostSignoutRoute functionality
 *
 * @author Austin Cepalia, @acc5989
 */
@Tag("UI-tier")
class PostSignoutRouteTest {

    //Unit under Test
    private PostSignoutRoute CuT;

    //friendly objects
    private PlayerLobby lobby;

    //parameters to be mocked
    private Request request;
    private Response response;
    private Session session;
    private Player player;

    @BeforeEach
    public void setup() {

        lobby = new PlayerLobby();
        player = new Player("Player1");

        //mock dependencies
        request = mock(Request.class);
        session = mock(Session.class);
        session.attribute(GetHomeRoute.PLAYER_KEY, player);
        when(request.session()).thenReturn(session);
        response=mock(Response.class);

        //create signoutRoute
        lobby.getPlayers().add(player);
        CuT = new PostSignoutRoute(lobby);
    }

    @Test
    void handleTest() {
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
        assertEquals(CuT.handle(request,response), null);
    }


    @Test
    void updateModelTest() {
        when(session.attribute(GetHomeRoute.PLAYER_KEY)).thenReturn(player);
        CuT.handle(request, response);
        assertTrue(lobby.getPlayers().size() == 0);
    }
}
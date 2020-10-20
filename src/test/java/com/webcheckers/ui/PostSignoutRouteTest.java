package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
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
    private PlayerLobby lobby=new PlayerLobby();
    private Gson gson;

    //parameters to be mocked
    private Request request;
    private Response response;
    private Session session;

    @BeforeEach
    public void setup() {
        //mock dependencies
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response=mock(Response.class);
        //create signoutRoute
        lobby.getPlayers().add(new Player("Player1"));
        CuT = new PostSignoutRoute(lobby);
    }

    @Test
    void handleTest() {
        String jsonTest = gson.toJson(Message.info("true"));
        assertEquals(CuT.handle(request,response),jsonTest);
    }

    @Test
    void updateModelTest() {
        assertTrue(lobby.getPlayers().size() == 0);
    }
}
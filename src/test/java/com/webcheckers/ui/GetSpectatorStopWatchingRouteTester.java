package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import spark.*;

@Tag("UI-Tier")
/*
  Unit test for GetSpectatorStopWatching.class

  @author Jack Thomas
 */
public class GetSpectatorStopWatchingRouteTester {

    //Unit under Test
    private GetSpectatorStopWatchingRoute CuT;
    //parameters to be mocked
    private Player spectator;
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;
    private PlayerLobby lobby;

    @BeforeEach
    public void setup() {
        //mock dependencies
        lobby = mock(PlayerLobby.class);
        request = mock(Request.class);
        response = mock(Response.class);
        session = mock(Session.class);
        spectator = mock(Player.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        //create CuT
        CuT = new GetSpectatorStopWatchingRoute(lobby, engine);

        when(request.session()).thenReturn(session);
        when(session.attribute("player")).thenReturn(spectator);
        when(spectator.getName()).thenReturn("TestSpec");
    }

    @Test
    void ctestInvalidParams(){
        //test for invalid initialization
        assertThrows(NullPointerException.class,()->{new GetSigninRoute(null);});
    }

    @Test
    void handleTest() {
        assertThrows(HaltException.class,()->{CuT.handle(request,response);});
    }
}

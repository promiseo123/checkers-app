package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the home page.
 *
 * @author: Ferdous Z.
 */

@Tag("UI-Tier")
public class GetHomeRouteTester {

    private GetHomeRoute CuT;  // This is our Unit under Test

    // UuT variables. They're essentially psuedo-objects that mock the functionality.
    private PlayerLobby playerLobby;
    private TemplateEngine templateEngine;
    private Player player;
    private Session session;
    private Request request;
    private Response response;

    /*
    Setup the test benchmark for GetHomeRoute. This will essentially set up all the prerequisites necessary
    so that it can handle the tests per session.
     */
    @BeforeEach
    public void setup() {
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        response = mock(Response.class);
        templateEngine = mock(TemplateEngine.class);
        playerLobby = mock(PlayerLobby.class);
        player = mock(Player.class);

        CuT = new GetHomeRoute(playerLobby, templateEngine);

        // So far, requests, sessions, responses, the T.E., the lobby, and the players
        // are now "psuedo-initiated per test"
    }

    /**
     * This shows the psuedo route CuT's view of Home on a new session...
     * This should first prove that you can legitimately view Home in the first place.
     */
    @Test
    public void getHome() {

        assertNotNull(playerLobby);

        when(session.attribute("currentUser")).thenReturn(player);
        when(playerLobby.getPlayer(session.attribute("currentUser"))).thenReturn(player);

        // these essentially grabs the player per session.
        // Since its per session, this is more of currentUser instead of the regular Player flag
        // for the reason that it checks getting Home first.....

        try {
            CuT.handle(request, response);
        } catch (Exception e) {
            fail();  // added try catch if anything happens.
        }

        assertNotNull(templateEngine);

        final TemplateEngineTester temp_assist = new TemplateEngineTester();
        when(templateEngine.render(any(ModelAndView.class))).thenAnswer(temp_assist.makeAnswer());
        // creates and tests a comparative TempEngine to CuT's, and compare the Models and such.

        //from here, we analyze the attributes and compare if its held accountable to a T.

        temp_assist.assertViewModelExists();
        temp_assist.assertViewModelIsaMap();

        temp_assist.assertViewModelAttribute(GetHomeRoute.PLAYER_KEY, Objects.nonNull(playerLobby.signIn(request.params("PlayerName"))));
        // it compares if the playerkey match the psuedo request's analysis  of the player name...
    }


}

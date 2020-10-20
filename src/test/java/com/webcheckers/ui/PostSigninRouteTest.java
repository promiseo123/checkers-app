package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test class for PostSigninRoute functionality
 *
 * @author Promise Omipone, poo9724
 */
@Tag("UI-tier")
class PostSigninRouteTest {


    //Unit under Test
    private PostSigninRoute CuT;

    //friendly objects
    private PlayerLobby lobby=new PlayerLobby();

    //parameters to be mocked
    private Request request;
    private Response response;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        //mock dependencies
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        response=mock(Response.class);
        //create CuT
        lobby.getPlayers().add(new Player("Gamer1"));
        CuT = new PostSigninRoute(lobby, engine);
    }

//    @Test
//    void ctestInvalidParams(){
//        //test for invalid initialization
//        assertThrows(IllegalArgumentException.class,()->{new PostSigninRoute(null,null);});
//    }

    @Test
    void handleTest() {
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());
        when(request.queryParams("PlayerName")).thenReturn("Gamer1");
        CuT.handle(request, response);
        //verify view model
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("title", "Sign In");
        testHelper.assertViewName("signin.ftl");
    }
}
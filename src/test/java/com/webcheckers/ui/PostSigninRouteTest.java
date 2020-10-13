package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Tag("UI-tier")
class PostSigninRouteTest {


    //Unit under Test
    private PostSigninRoute CuT;
    //parameters to be mocked
    private Request request;
    private Session session;
    private TemplateEngine engine;

    @BeforeEach
    public void setup() {
        //mock dependencies
        request = mock(Request.class);
        session = mock(Session.class);
        when(request.session()).thenReturn(session);
        engine = mock(TemplateEngine.class);
        //create CuT
        CuT = new PostSigninRoute(new PlayerLobby(),engine);
    }

    @Test
    void ctestInvalidParams(){
        //test for invalid initialization
        assertThrows(IllegalArgumentException.class,()->{new PostSigninRoute(null,null);});
    }

    @Test
    void handleTest() {
        TemplateEngineTester testHelper = new TemplateEngineTester();
        when(engine.render(any(ModelAndView.class))).thenAnswer(testHelper.makeAnswer());

        //verify view model
        testHelper.assertViewModelExists();
        testHelper.assertViewModelIsaMap();

        testHelper.assertViewModelAttribute("Title", "Sign In");
        testHelper.assertViewName("signin.ftl");
    }
}
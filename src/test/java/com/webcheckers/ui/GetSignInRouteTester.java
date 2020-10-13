package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import spark.ModelAndView;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-Tier")
/*
  Unit test for GetSigninRoute.class

  @author Jack Thomas
 */
public class GetSignInRouteTester {

    //Unit under Test
    private GetSigninRoute CuT;
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
        CuT = new GetSigninRoute(engine);
    }

    @Test
    void ctestInvalidParams(){
        //test for invalid initialization
        assertThrows(IllegalArgumentException.class,()->{new GetSigninRoute(null);});
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

package com.webcheckers.ui;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import com.google.gson.Gson;
import com.webcheckers.util.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;
import spark.TemplateEngine;

/**
 * Unit test for PostResignRoute.class
 *
 * @author Jack Thomas
 */
@Tag("UI-Tier")
public class PostResignRouteTester {

    //Unit under Test
    private PostResignRoute CuT;
    //parameters to be mocked
    private Request request;
    private Response response;
    private TemplateEngine engine;
    //friendly
    private Gson gson;

    @BeforeEach
    public void setup() {
        //mock dependencies
        request = mock(Request.class);
        response = mock(Response.class);
        engine = mock(TemplateEngine.class);
        gson = new Gson();
        //create CuT
        CuT = new PostResignRoute(gson,engine);
    }

    @Test
    void testHandle() throws Exception {
        //Verify Json
        String jsonTest = gson.toJson(Message.info("true"));
        assertEquals(CuT.handle(request,response),jsonTest);
    }
}

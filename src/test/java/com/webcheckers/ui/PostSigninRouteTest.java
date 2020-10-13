package com.webcheckers.ui;

import org.junit.jupiter.api.Tag;
import spark.Request;
import spark.Session;
import spark.TemplateEngine;

@Tag("UI-tier")
class PostSigninRouteTest {

    //Unit under Test
    private PostSigninRoute CuT;
    //parameters to be mocked
    private Request request;
    private Session session;
    private TemplateEngine engine;
}
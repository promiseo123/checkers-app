package com.webcheckers.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.TemplateEngine;

import com.webcheckers.util.Message;

public class GetSigninRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private static final Message WELCOME_MSG = Message.info("Welcome to the world of online Checkers.");

    private final TemplateEngine templateEngine;

    public GetSigninRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        //
        LOG.config("GetHomeRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked.");
        //
        Map<String, Object> vm = new HashMap<>();

        // render the View
        return templateEngine.render(new ModelAndView(vm , "sign-in.ftl"));
    }
}

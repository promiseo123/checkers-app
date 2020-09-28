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

/**
 * The UI Controller to GET the Signin page.
 *
 * @author Jack Thomas
 */
public class GetSigninRoute implements Route{
    private static final Logger LOG = Logger.getLogger(GetSigninRoute.class.getName());

    private final TemplateEngine templateEngine;

    //Title attribute key
    private final String TTL = "title";
    //Title value
    private final String TITLE = "Sign In";

    /**
     *Constructor for Get signin route
     *
     * Sets up template engine
     */
    public GetSigninRoute(final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        LOG.config("GetSigninRoute is initialized.");
    }

    /**
     * Builds and displays the signin page when called
     *
     * Parameters: HTTP request and response
     * Returns:templateEngine.render
     */
    @Override
    public Object handle(Request request, Response response) {
        LOG.finer("GetSigninRoute is invoked.");
        //setup view map
        Map<String, Object> vm = new HashMap<>();
        vm.put(TTL,TITLE);

        // render the View
        return templateEngine.render(new ModelAndView(vm , "signin.ftl"));
    }
}

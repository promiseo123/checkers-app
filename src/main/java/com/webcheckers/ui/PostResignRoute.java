package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

import static spark.Spark.halt;

/**
 * The UI Controller to POST the /resignGame route
 *
 * @author Jack Thomas
 */
public class PostResignRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostResignRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final Gson gson;

    /**
     * Constructor for PostResignRoute route
     *
     * @param gson             the Gson engine for interacting with ajax
     * @param templateEngine    The template engine used in view/model interactions
     */
    public PostResignRoute(final Gson gson, final PlayerLobby playerLobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = playerLobby;
        this.gson = gson;

        LOG.config("PostResignRoute is initialized.");
    }


    /**
     * Operates resignation
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     *
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session session = request.session();
        Player currentUser = session.attribute(GetHomeRoute.PLAYER_KEY);
        try{
            //get resign message
            Message message = Message.info("true");
            return gson.toJson(message);
        }
        catch (Exception E){
            Message message = Message.error("Error Resigning");
            return gson.toJson(message);}
    }
}

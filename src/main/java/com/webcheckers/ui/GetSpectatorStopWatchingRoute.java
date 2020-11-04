package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.halt;

/**
 * The Route to handle ending spectation
 *
 * @author Jack Thomas
 */

public class GetSpectatorStopWatchingRoute implements Route{
    // --------------------------------- VARIABLES --------------------------------- //

    private static final String MESSAGE_ATTR = "message";
    private final TemplateEngine templateEngine;
    private final PlayerLobby playerLobby;
    private final String NAME_PARAM = "PlayerName";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     *Constructor for POST signin
     *
     * Sets up template engine
     */
    public GetSpectatorStopWatchingRoute(final PlayerLobby playerLobby, final TemplateEngine templateEngine){
        this.templateEngine = templateEngine;
        this.playerLobby = playerLobby;
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * Removes scaffolding supporting the Spectating state and redirects user
     * home.
     *
     * Parameters: HTTP request and response
     * Returns:null
     */
    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();
        String gameID = request.queryParams("gameID");
        Player spectator = session.attribute("player");
        playerLobby.markPlayerAsDoneSpectating(spectator.getName());
        response.redirect(WebServer.HOME_URL);
        halt();
        return null;
    }
}

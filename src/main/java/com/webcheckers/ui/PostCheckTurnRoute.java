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

/**
 * The UI Controller to POST the /checkTurn route.
 *
 * @author Anthony DelPrincipe ajd6295
 */
public class PostCheckTurnRoute implements Route {

    // --------------------------------- VARIABLES --------------------------------- //

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for PostCheckTurnRoute route, sets up lobby and template engine for this route
     *
     * @param lobby             The PlayerLobby of all players
     * @param templateEngine    The template engine used in view/model interactions
     */
    public PostCheckTurnRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;

        LOG.config("PostCheckTurnRoute is initialized.");
    }

    // --------------------------------- PUBLIC METHODS --------------------------------- //

    /**
     * handle: Incomplete implementation so far, will handle when a user who is waiting for their turn
     *         "asks" whether it's their turn or not
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @return              Nothing yet, *should* return valid JSON version of a info Message
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        // Make the session and gson thing
        final Session session = request.session();
        Gson g = new Gson();
        Message message;

        Player currentUser = session.attribute(PLAYER_KEY);
        String gameID = request.queryParams("gameID");

        // Figure out what the player color is and store it
        Player.COLOR playerColor = Player.COLOR.WHITE;
        if (GameCenter.getGameByID(gameID).isRedPlayer(currentUser)) {
            playerColor = Player.COLOR.RED;
        }

        // Get the turn color, and then create a Message depending on whether it equals the player color
        // True = the turn color is the player color and so it's their turn, false otherwise
        Game.TURN turnColor = GameCenter.getGameByID(gameID).getTurn();
        if (turnColor.toString().equals(playerColor.toString())) {
            message = Message.info("true");
        } else {
            message = Message.info("false");
        }

        return g.toJson(message);
    }
}

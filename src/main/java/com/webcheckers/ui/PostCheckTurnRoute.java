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

    // --------------------------------- METHODS --------------------------------- //

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

        final Session session = request.session();

        Player currentUser = session.attribute(PLAYER_KEY);
        String gameID = request.queryParams("gameID");

        String playerColor = "";
        String turnColor = GameCenter.getGameByID(gameID).getTurn().toString();
        if (GameCenter.getGameByID(gameID).getRedPlayer().toString().equals(currentUser.getName())) {
            playerColor = "RED";
        } else {
            playerColor = "WHITE";
        }

        Message message;
        if (turnColor.equals(playerColor)) {
            message = Message.info("true");
        } else {
            message = Message.info("true");
        }

        Gson g = new Gson();
        return g.toJson(message);
    }
}

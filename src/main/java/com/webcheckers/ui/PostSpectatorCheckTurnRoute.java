package com.webcheckers.ui;

import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Preconditions;
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
 * The UI Controller to POST the /checkSpectateTurn route.
 *
 * @author Jack Thomas jbt5011
 */
public class PostSpectatorCheckTurnRoute implements Route {

    // ------------------------------------ Fields ------------------------------------ //

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;
    private final Gson gson;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for PostSpectatorCheckTurnRoute route, sets up lobby and template engine for this route
     *
     * @param lobby             The PlayerLobby of all players
     * @param templateEngine    The template engine used in view/model interactions
     */
    public PostSpectatorCheckTurnRoute(final PlayerLobby lobby, final TemplateEngine templateEngine, Gson gson) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;
        this.gson = gson;

        LOG.config("PostCheckTurnRoute is initialized.");
    }

    // --------------------------------- PUBLIC METHODS --------------------------------- //

    /**
     * Checks spectated game for current turn
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @return              Json info/error message
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session session = request.session();
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
        Game game = GameCenter.getGameByID(gameID);
        Game.TURN turnColor = game.getTurn();
        if (turnColor.toString().equals(playerColor.toString())) {
            message = Message.info("true");
        } else {
            message = Message.info("false");
        }

        return gson.toJson(message);
    }
}

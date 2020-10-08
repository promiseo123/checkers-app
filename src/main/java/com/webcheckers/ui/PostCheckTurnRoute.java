package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Logger;

public class PostCheckTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostCheckTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;

    public static final String PLAYER_KEY = "player";
    public static final String CURRENT_USER_KEY = "currentUser";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostCheckTurnRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;

        LOG.config("GetHomeRoute is initialized.");
    }

    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session session = request.session();

        Player currentUser = session.attribute(PLAYER_KEY);
        String gameID = request.queryParams("gameID");

        String playerColor = "";
        String turnColor = GameCenter.getGameByID(gameID).getTurn().toString();
        if (GameCenter.getGameByID(gameID).getRedPlayer().toString() == currentUser.getName()) {
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

        return message.getText();
    }
}

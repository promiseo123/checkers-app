package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Create the Spark Route (UI controller) to handle all {@code GET /} HTTP requests.
     *
     * @param templateEngine
     *   the HTML template rendering engine
     */
    public PostSubmitTurnRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;

        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    // --------------------------------- METHODS --------------------------------- //

    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session session = request.session();

        Message message = Message.info("Test: Is valid");

        return message;
    }

}

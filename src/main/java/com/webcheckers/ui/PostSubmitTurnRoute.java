package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the /submitTurn route.
 *
 * @author Anthony DelPrincipe ajd6295
 */
public class PostSubmitTurnRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for PostSubmitTurnRoute route, sets up lobby and template engine for this route
     *
     * @param lobby             The PlayerLobby of all players
     * @param templateEngine    The template engine used in view/model interactions
     */
    public PostSubmitTurnRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;

        LOG.config("PostSubmitTurnRoute is initialized.");
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * handle: No implementation yet, will handle when the user finalizes their move by pressing "submit"
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @return              Nothing yet, *should* return valid JSON version of a info Message
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        final Session session = request.session();

        Message message = Message.info("Test: Is valid");

        return message;
    }

}

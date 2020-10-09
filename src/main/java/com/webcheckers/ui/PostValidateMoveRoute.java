package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the /validateMove route
 *
 * @author Anthony DelPrincipe ajd6295
 */
public class PostValidateMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for PostValidateMoveRoute route, sets up lobby and template engine for this route
     *
     * @param lobby             The PlayerLobby of all players
     * @param templateEngine    The template engine used in view/model interactions
     */
    public PostValidateMoveRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;

        LOG.config("PostValidateMoveRoute is initialized.");
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * handle: No implementation yet, will handle validating a move to make sure it's legal with the current board setup
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @return              Nothing yet, *should* return valid JSON version of a info Message
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {
        // Get the session and make the gson
        final Session session = request.session();
        Gson g = new Gson();

        // Get the move made from the request
        Move move = g.fromJson(request.queryParams("actionData"), Move.class);
        move.setType(Move.TYPE.SINGLE);

        // Get the board we're dealing with
        Player currentUser = session.attribute(PLAYER_KEY);
        Board board = GameCenter.getGameByID(currentUser.getGameID()).getBoard(); // Could do some information expert stuff here

        Message message;
        // Check if the move is valid or not
        if (board.isValidMove(move)) {
            board.makeMove(move);
            message = Message.info("");
        } else {
            message = Message.error("I'll tell you why later");
        }


        // If it is valid, record it! They might wanna go back



        return g.toJson(message);
    }

}

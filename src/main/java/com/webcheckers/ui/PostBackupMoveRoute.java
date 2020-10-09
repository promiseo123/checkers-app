package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the /backupMove route
 *
 * @author Anthony DelPrincipe ajd6295
 */
public class PostBackupMoveRoute implements Route {

    private static final Logger LOG = Logger.getLogger(PostBackupMoveRoute.class.getName());

    private final TemplateEngine templateEngine;
    private final PlayerLobby lobby;

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for PostBackupMoveRoute route, sets up lobby and template engine for this route
     *
     * @param lobby             The PlayerLobby of all players
     * @param templateEngine    The template engine used in view/model interactions
     */
    public PostBackupMoveRoute(final PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
        this.lobby = lobby;

        LOG.config("PostBackupMoveRoute is initialized.");
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * handle: No implementation yet, will handle undoing a move when the user clicks the undo button
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @return              Nothing yet, *should* return valid JSON version of a info Message
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        // Set the session and make a gson
        final Session session = request.session();
        Gson g = new Gson();

        // Get the board we're dealing with
        Player currentUser = session.attribute(PLAYER_KEY);
        Game game = GameCenter.getGameByID(currentUser.getGameID());
        Move move = game.getBoard().getLatestMove();
        Move undoMove = new Move(move.getEnd(), move.getStart());


        game.getBoard().makeMove(undoMove);
        game.getBoard().removeLatestMove();
        game.getBoard().removeLatestMove();

        Message message = Message.info("Test: Is valid");

        return g.toJson(message);
    }

}

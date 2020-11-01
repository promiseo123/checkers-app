package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
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

    // --------------------------------- VARIABLES --------------------------------- //

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
     * handle: Handles when the user finalizes their move by pressing "submit"
     *
     * @param request       The HTTP request
     * @param response      The HTTP response
     * @return              Returns a valid JSON version of an info Message
     */
    @Override
    public Object handle(Request request, Response response) throws Exception {

        // Get session and make Gson
        final Session session = request.session();
        Gson g = new Gson();

        // Get the board we're dealing with
        Player currentUser = session.attribute(PLAYER_KEY);
        Game game = GameCenter.getGameByID(currentUser.getGameID());

        assert game != null;

        // Store the state of the game board in the game's GameReplay object
        game.addBoardStateToReplay();


        // Switch who's turn it is, clear the moves that have been made this turn
        game.switchTurns();
        game.getBoard().clearMoves();

        // If this turn ended the game, it must be because the currentUser made a winning move
        if (game.hasEnded()) {
            currentUser.playerWon(true);
            game.getOpponent(currentUser).playerWon(false);
        }


        // Dunno how this would fail, so I just automatically return success case
        Message message = Message.info("");

        return g.toJson(message);
    }

}

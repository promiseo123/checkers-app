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

    // --------------------------------- VARIABLES --------------------------------- //

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

        System.out.println("valid");

        // Get the session and make the gson/message
        final Session session = request.session();
        Gson g = new Gson();
        Message message = null;

        // Get the move made from the request
        Move move = g.fromJson(request.queryParams("actionData"), Move.class);

        // Get the board we're dealing with
        Player currentUser = session.attribute(PLAYER_KEY);
        Board board = GameCenter.getGameByID(currentUser.getGameID()).getBoard();

        // Get these to make it easier to work with them
        int endRow=move.getEndRow();
        int startRow=move.getStartRow();
        int endCell=move.getEndCell();
        int startCell=move.getStartCell();

        // Figure out if it's a SIMPLE or a MULTI move.
        // If it's neither (tried to move across the board or something)
        // The correct error code should still be returned from isValidMove()
        // cuz it'll see that the move doesn't have a type
        if ((-1 <= endRow-startRow && endRow-startRow <= 1)
                && (-1 <= endCell-startCell && endCell-startCell <= 1)) {
            move.setType(Move.TYPE.SIMPLE);
        } else if ((-2 <= endRow-startRow && endRow-startRow <= 2)
                && (-2 <= endCell-startCell && endCell-startCell <= 2)){
            move.setType(Move.TYPE.MULTI);
        }

        // Get the error code from the validity checking
        int errCode = board.isValidMove(move);


        // Make the message based off of the error code
        // For now, 0=success, 1=the space was too far away, 2=they already made a move, 3=tried to move backwards
        if (errCode == 0) {
            board.makeMove(move);
            message = Message.info("");
        } else if (errCode == 1) {
            message = Message.error("Space is too far away!");
        } else if (errCode == 2) {
            message = Message.error("You've already made your move! Submit move or undo.");
        } else if (errCode == 3) {
            message = Message.error("Can't move backwards!");
        } else if (errCode == 4) {
            message = Message.error("There is no piece to take!");
        }else if (errCode == 99) {
            message = Message.error("Something went really wrong");
        }

        return g.toJson(message);
    }

}

package com.webcheckers.ui;

import com.google.gson.Gson;
import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Board;
import com.webcheckers.model.Game;
import com.webcheckers.model.Move;
import com.webcheckers.model.Player;
import com.webcheckers.util.Exceptions.InvalidMoveException;
import com.webcheckers.util.Exceptions.SpaceOutOfRangeException;
import com.webcheckers.util.Message;
import spark.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * The UI Controller to POST the /validateMove route
 *
 * @author Anthony DelPrincipe ajd6295
 * @author Austin Cepalia acc5989
 */
public class PostValidateMoveRoute implements Route {

    // --------------------------------- VARIABLES --------------------------------- //

    private static final Logger LOG = Logger.getLogger(PostSubmitTurnRoute.class.getName());

    public static final String PLAYER_KEY = "player";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for PostValidateMoveRoute route, does nothing special at all.
     */
    public PostValidateMoveRoute() {
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
    public Object handle(Request request, Response response) {

        // Get the session and make the gson/message
        final Session session = request.session();
        Gson g = new Gson();

        // Get the move made from the request
        Move move = g.fromJson(request.queryParams("actionData"), Move.class);

        // Get the board we're dealing with
        Player currentUser = session.attribute(PLAYER_KEY);
        Game game = GameCenter.getGameByID(currentUser.getGameID());
        Board board = game.getBoard();

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

        // Validate movement and catch any exceptions that may occur
        try {
            if (board.isValidMove(move)) {
                //TODO: This probably SHOULD NOT be in Validate
                boolean gameOver = board.makeMove(move);
                game.isOver(gameOver);

                //TODO: Maybe a blank message should just be null? Check on front end?
                return g.toJson("");
            }
        } catch (InvalidMoveException ex) {
            game.isOver(false);
            return g.toJson(Message.error(ex.getMessage()));
        }
        return null;
    }

}

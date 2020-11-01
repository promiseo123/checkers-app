package com.webcheckers.ui;

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

import static spark.Spark.halt;

/**
 * The UI Controller to GET the /startGame route
 */
public class GetStartGameRoute implements Route {

    // --------------------------------- VARIABLES --------------------------------- //

    private static final Logger LOG = Logger.getLogger(GetStartGameRoute.class.getName());

    private PlayerLobby lobby;
    private TemplateEngine templateEngine;
    private final static String ERROR = "That player is already in a game.";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for GetStartGame route, sets up lobby and template engine for this route
     *
     * @param lobby             The PlayerLobby of all players
     * @param templateEngine    The template engine used in view/model interactions
     */
    public GetStartGameRoute(PlayerLobby lobby, TemplateEngine templateEngine) {
        this.lobby = lobby;
        this.templateEngine = templateEngine;
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * handle: Begins the build process for the game page, determines whether a game can/will actually be started or not
     *         and then defers to the GetHomeRoute
     *
     * @param request       HTTP Request
     * @param response      HTTP Response
     * @return              Either null or the template engine render
     */
    @Override
    public Object handle(Request request, Response response){

        LOG.finer("GetStartGameRoute is invoked.");
        final Session session = request.session();

        // Get the player who requested to start the game (RED)
        Player currentUser = session.attribute(GetHomeRoute.PLAYER_KEY);

        // Making sure the current user actually exists and something didn't go horribly wrong
        if (currentUser != null) {
            String opponentName = request.queryParams("desiredOpponent");

            // Okay, there's a lot of checking going on here
            // If a player wants to start a game, we need to make sure that the opponent isn't currently
            // in a game or waiting to be in a game, and the current user also isn't waiting to be
            // put in a game either (someone requested to play with them, and their home
            // view has yet to update)
            if (!currentUser.isWaiting()
                    && !lobby.getPlayer(opponentName).isPlaying()
                    && !lobby.getPlayer(opponentName).isWaiting()) {

                // Denote that these players are waiting to (possibly) be put into a game
                currentUser.waitingStatus(true);
                lobby.getPlayer(opponentName).waitingStatus(true);

                // Generate a game ID and create Game with it
                final String gameID = Game.generateRandomGameID();
                GameCenter.newGame(gameID, currentUser, lobby.getPlayer(opponentName));

                // Assign both players to game with correct color specification
                lobby.assignPlayerToGame(currentUser.getName(), gameID);
                lobby.markPlayerWithColor(currentUser.getName(), Player.COLOR.RED);

                lobby.assignPlayerToGame(opponentName, gameID);
                lobby.markPlayerWithColor(opponentName, Player.COLOR.WHITE);
            } else {
                // Other player wasn't able to play a game due to whatever?
                // Render the home page with an error message
                Map<String, Object> mv = new HashMap<>();
                Message message = Message.error(ERROR);
                mv.put("message", message);
                mv.put("title", "Welcome!");
                mv.put(GetHomeRoute.CURRENT_USER_KEY, currentUser);
                return templateEngine.render(new ModelAndView(mv, "home.ftl"));
            }

            // Fantastic, everything's all set to render the game! Redirect to GetGameRoute and do it.
            response.redirect(WebServer.GAME_URL);
            halt();
        }
        return null;
    }
}

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

import static spark.Spark.halt;

/**
 * The UI Controller to GET the StartGame render page.
 */
public class GetStartGameRoute implements Route {

    // --------------------------------- VARIABLES --------------------------------- //

    private PlayerLobby lobby;
    private TemplateEngine templateEngine;
    private final static String ERROR = "That player is already in a game.";

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Constructor for GetStartGame route, sets up lobby and template engine
     *
     * @param lobby     The PlayerLobby of all players
     * @param engine    The template engine used in view/model interactions
     */
    public GetStartGameRoute(PlayerLobby lobby, TemplateEngine engine) {
        this.lobby = lobby;
        this.templateEngine = engine;
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
        final Session session = request.session();
        final String gameID = Game.generateRandomGameID();

        // Get the player who requested to start the game (RED)
        Player currentUser = session.attribute(GetHomeRoute.PLAYER_KEY);

        if (currentUser != null) {
            String opponentName = request.queryParams("desiredOpponent");

            currentUser.waitingStatus(true);
            lobby.getPlayer(opponentName).waitingStatus(true);

            if (!lobby.getPlayer(opponentName).isPlaying() && !lobby.getPlayer(opponentName).isWaiting()) {
                GameCenter.newGame(gameID, currentUser, lobby.getPlayer(opponentName));

                lobby.assignPlayerToGame(currentUser.getName(), gameID);
                lobby.markPlayerWithColor(currentUser.getName(), Player.COLOR.RED);

                lobby.assignPlayerToGame(opponentName, gameID);
                lobby.markPlayerWithColor(opponentName, Player.COLOR.WHITE);
            } else {
                Map<String, Object> mv = new HashMap<>();
                Message message = Message.error(ERROR);
                mv.put("message", message);
                mv.put("title", "Welcome!");
                mv.put(GetHomeRoute.CURRENT_USER_KEY, currentUser);

                return templateEngine.render(new ModelAndView(mv, "home.ftl"));
            }

            // First, check if the user should be redirected to a game
            if (currentUser.readyToPlay()) {

                Game game = GameCenter.getGameByID(currentUser.getGameID());

                Map<String, Object> mv = new HashMap<>();
                mv.put("title", "New Game");
                mv.put("gameID", currentUser.getGameID());
                mv.put("currentUser", currentUser);
                mv.put("viewMode", "PLAY");
                mv.put("modeOptionsAsJSON", null);
                if (currentUser.getColor() == Player.COLOR.RED) {
                    mv.put("redPlayer", currentUser);
                    mv.put("whitePlayer",  game.getWhitePlayer());
                }
                else {
                    mv.put("whitePlayer", currentUser);
                    mv.put("redPlayer", game.getRedPlayer());
                }

                mv.put("activeColor", game.getTurn().toString());
                mv.put("board", game.getBoardView(currentUser.getColor()));

                lobby.markPlayerAsPlaying(currentUser.getName());

                currentUser.waitingStatus(false);
                lobby.getPlayer(opponentName).waitingStatus(false);

                return templateEngine.render(new ModelAndView(mv, "game.ftl"));
            }
            halt();
        }
        return null;
    }
}

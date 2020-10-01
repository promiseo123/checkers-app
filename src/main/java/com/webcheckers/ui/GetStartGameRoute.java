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

public class GetStartGameRoute implements Route {

    private PlayerLobby lobby;
    private TemplateEngine templateEngine;
    private final static String ERROR = "That player is already in a game.";

    public GetStartGameRoute(PlayerLobby lobby, TemplateEngine engine) {
        this.lobby = lobby;
        this.templateEngine = engine;
    }

    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();
        final String gameID = Game.generateRandomGameID();

        // Get the player who requested to start the game (RED)
        Player thisPlayer = session.attribute(GetHomeRoute.PLAYER_KEY);

        if (thisPlayer != null) {
            String opponentName = request.queryParams("desiredOpponent");

            if (!lobby.getPlayer(opponentName).isPlaying()) {
                GameCenter.newGame(gameID, thisPlayer, lobby.getPlayer(opponentName));

                lobby.assignPlayerToGame(thisPlayer.getName(), gameID);
                lobby.markPlayerWithColor(thisPlayer.getName(), Player.COLOR.RED);

                lobby.assignPlayerToGame(opponentName, gameID);
                lobby.markPlayerWithColor(opponentName, Player.COLOR.WHITE);
            }
            else {
                Map<String, Object> mv = new HashMap<>();
                Message message = Message.error(ERROR);
                mv.put("message", message);
                mv.put("title", "Welcome!");
                mv.put(GetHomeRoute.CURRENT_USER_KEY, thisPlayer);

                return templateEngine.render(new ModelAndView(mv, "home.ftl"));
            }

            // Go home. Let that controller worry about redirecting users to games.
            response.redirect(WebServer.HOME_URL);
            halt();
        }

        return null;

    }
}

package com.webcheckers.ui;

import com.webcheckers.appl.GameCenter;
import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static spark.Spark.halt;

public class GetStartGameRoute implements Route {

    private final TemplateEngine templateEngine;
    private PlayerLobby lobby;

    public GetStartGameRoute(PlayerLobby lobby, final TemplateEngine templateEngine) {
        this.lobby = lobby;
        this.templateEngine = Objects.requireNonNull(templateEngine, "templateEngine is required");
    }

    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();
        final String gameID = Game.generateRandomGameID();

        Map<String, Object> vm = new HashMap<>();

        // Get the player who requested to start the game (RED)
        Player thisPlayer = session.attribute(GetHomeRoute.PLAYER_KEY);

        if (thisPlayer != null) {
            String opponentName = request.queryParams("desiredOpponent");

            GameCenter.newGame(gameID, thisPlayer, lobby.getPlayer(opponentName));

            lobby.assignPlayerToGame(thisPlayer.getName(), gameID);
            lobby.markPlayerWithColor(thisPlayer.getName(), Player.COLOR.RED);

            lobby.assignPlayerToGame(opponentName, gameID);
            lobby.markPlayerWithColor(opponentName, Player.COLOR.WHITE);

            // Go home. Let that controller worry about redirecting users to games.
            response.redirect(WebServer.HOME_URL);
            halt();
        }

        return null;

    }
}

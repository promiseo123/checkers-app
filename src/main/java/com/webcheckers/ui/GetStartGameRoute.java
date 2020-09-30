package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

import static spark.Spark.halt;

public class GetStartGameRoute implements Route {

    private PlayerLobby lobby;

    //TODO maybe: keep track of which players are in which games (via gameIDs)

    public GetStartGameRoute(PlayerLobby lobby) {
        this.lobby = lobby;
    }

    @Override
    public Object handle(Request request, Response response){
        final Session session = request.session();
        final String gameID = Game.generateRandomGameID();

        // Get the player who requested to start the game (RED)
        Player thisPlayer = session.attribute(GetHomeRoute.PLAYER_KEY);

        if (thisPlayer != null) {
            lobby.assignPlayerToGame(thisPlayer.getName(), gameID);
            lobby.markPlayerWithColor(thisPlayer.getName(), Player.COLOR.RED);
            String opponentName = request.queryParams("desiredOpponent");
            lobby.assignPlayerToGame(opponentName, gameID);
            lobby.markPlayerWithColor(opponentName, Player.COLOR.WHITE);


            // Go home. Let that controller worry about redirecting users to games.
            response.redirect(WebServer.HOME_URL);
            halt();
        }
        return null;

    }
}

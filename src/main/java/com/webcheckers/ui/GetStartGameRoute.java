package com.webcheckers.ui;

import com.webcheckers.appl.PlayerLobby;
import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Session;

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

        // Get the player who requested to start the game
        Player thisPlayer = session.attribute(GetHomeRoute.PLAYER_KEY);

        if (thisPlayer != null) {
            System.out.println(thisPlayer.getName());
            lobby.assignPlayerToGame(thisPlayer.getName(), gameID);
            String opponentName = request.queryParams("desiredOpponent");
            System.out.println(opponentName);
            lobby.assignPlayerToGame(opponentName, gameID);
        }
        return null;

        

    }
}

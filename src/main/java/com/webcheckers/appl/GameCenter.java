package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;

import java.util.HashMap;
import java.util.Map;

public class GameCenter {

    private static Map<String, Game> gamesMap = new HashMap<>();

    public static void newGame(String gameID, Player red, Player white) {
        Game game = new Game(gameID, red, white);
        gamesMap.put(gameID, game);
    }

    public static Game getGameByID(String gameID) {
        return gamesMap.get(gameID);
    }

}

package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.Player;
import spark.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * GameCenter: Class to keep track of all Games in a current session
 */
public class GameCenter {

    // --------------------------------- VARIABLES --------------------------------- //

    // Hash map of gameIDs to the games themselves
    private static Map<String, Game> gamesMap = new HashMap<>();

    // Hash map of gameIDs to the games themselves
    private static Map<String, HashMap<String, Object>[]> gameMVMap = new HashMap<>();

    // --------------------------------- METHODS --------------------------------- //

    /**
     * newGame: Make a new game with a game ID and the two player(red/white)
     *
     * @param gameID    Game ID of game
     * @param red       Red player
     * @param white     White player
     */
    public static void newGame(String gameID, Player red, Player white) {
        Game game = new Game(gameID, red, white);
        gamesMap.put(gameID, game);
    }

    /**
     * getGameByID: Returns a game by the id
     *
     * @param gameID    The game ID of the game you want to get
     * @return          The game
     */
    public static Game getGameByID(String gameID) {
        return gamesMap.get(gameID);
    }

}

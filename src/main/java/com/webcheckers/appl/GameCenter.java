package com.webcheckers.appl;

import com.webcheckers.model.Game;
import com.webcheckers.model.GameLabel;
import com.webcheckers.model.Player;
import spark.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
     * @param gameID Game ID of game
     * @param red    Red player
     * @param white  White player
     */
    public static void newGame(String gameID, Player red, Player white) {
        Game game = new Game(gameID, red, white);
        gamesMap.put(gameID, game);
    }

    /**
     * getGameByID: Returns a game by the id
     *
     * @param gameID The game ID of the game you want to get
     * @return The game
     */
    public static Game getGameByID(String gameID) {
        if (gamesMap.containsKey(gameID)) {
            return gamesMap.get(gameID);
        }
        return null;
    }


    /**
     * wipeAllGames: Clears all games from memory
     */
    public static void wipeAllGames() {
        gamesMap.clear();
    }

    /**
     * wipeGame: Clears a specified game from memory
     *
     * @param gameID The gameID of the game to wipe
     * @return true if game was wiped, false if game was not found.
     */
    public static boolean wipeGame(String gameID) {
        if (gamesMap.containsKey(gameID)) {
            gamesMap.remove(gameID);
            return true;
        }
        return false;

    }

    public static List<GameLabel> getAvailableGames(){
        List<GameLabel> gameLabels = new ArrayList<>();
        for (String gameID : gamesMap.keySet()) {
            if (!GameCenter.getGameByID(gameID).hasEnded()) {
                GameLabel gameLabel = new GameLabel(gameID);
                gameLabels.add(gameLabel);
            }
        }
        return gameLabels;
    }


}

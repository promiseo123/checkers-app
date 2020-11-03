package com.webcheckers.model;

import com.webcheckers.appl.GameCenter;

import java.util.Objects;

/**
 * GameLabel represents a current game with its gameID and Players in the game
 *
 * @author Promise Omiponle poo9724
 */
public class GameLabel {

    private String gameID;
    private String playersInGame;

    /**
     * Construct a GameLabel by assigning the gameID and creating a string
     * representation of the Players in the game to be used as a link to
     * spectating the game
     *
     * @param gameID the ID of the game to be spectated
     */
    public GameLabel(String gameID) {
        this.gameID=gameID;
        Player redPlayer= Objects.requireNonNull(GameCenter.getGameByID(gameID)).getRedPlayer();
        Player whitePlayer= Objects.requireNonNull(GameCenter.getGameByID(gameID)).getWhitePlayer();
        this.playersInGame= redPlayer.getName()+" vs "+whitePlayer.getName();
    }

    /**
     * Get the ID of this GameLabel
     *
     * @return this GameLabel's gameID
     */
    public String getGameID() {
        return this.gameID;
    }

    /**
     * Returns the string representation of the Players in game
     *
     * @return string representation of the Players in game
     */
    public String getPlayersInGame() {
        return this.playersInGame;
    }
}

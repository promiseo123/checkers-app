package com.webcheckers.model;

import com.webcheckers.appl.GameCenter;

import java.util.Objects;

public class GameLabel {

    private String gameID;
    private String playersInGame;

    public GameLabel(String gameID) {
        this.gameID=gameID;
        Player redPlayer= Objects.requireNonNull(GameCenter.getGameByID(gameID)).getRedPlayer();
        Player whitePlayer= Objects.requireNonNull(GameCenter.getGameByID(gameID)).getWhitePlayer();
        this.playersInGame= redPlayer.getName()+" vs "+whitePlayer.getName();
    }

    public String getGameID() {
        return this.gameID;
    }

    public String getPlayersInGame() {
        return this.playersInGame;
    }
}

package com.webcheckers.model;

public class Player {
    private String name;
    private String currentGameID;
    private boolean inGame;

    public Player(String name) {
        this.name=name;
        this.currentGameID = "";
        inGame = false;
    }

    public void assignToGame(String gameID) {
        this.currentGameID = gameID;
        inGame = true;
    }

    public void donePlaying() {
        inGame = false;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

    public String debugString() {
        return name + " with currentGameID " + currentGameID;
    }

    @Override
    public boolean equals(Object other) {
        if (other instanceof Player) {

            // Two players are equal if they have the same name
            return ((Player) other).name.equals(this.name);
        }
        return false;
    }
}

package com.webcheckers.model;

public class Player {
    private String name;
    private String currentGameID;
    private boolean inGame;
    private COLOR color;

    public enum COLOR {RED, WHITE, NONE};


    public Player(String name) {
        this.name=name;
        this.currentGameID = "";
        inGame = false;
        color = COLOR.NONE;
    }

    public void assignToGame(String gameID) {
        this.currentGameID = gameID;
    }


    public boolean readyToPlay() {

        // They have a gameID but are not marked as playing
        return !currentGameID.equals("") && !inGame;
    }

    public void playing() {
        inGame = true;
    }

    public void donePlaying() {
        inGame = false;
        color = COLOR.NONE;
    }

    public void setColor(COLOR color) {
        this.color = color;
    }

    public COLOR getColor() {
        return color;
    }

    public String getName() {
        return this.name;
    }

    public String getGameID() { return this.currentGameID; }

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

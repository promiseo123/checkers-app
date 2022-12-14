package com.webcheckers.model;

/**
 * Player: Represents a player in the WebApp that can play Checkers. Contains the name of the
 *         player as well as info about any games they are contained in.
 */
public class Player {

    // --------------------------------- VARIABLES --------------------------------- //

    private String name;
    private String currentGameID;
    private boolean inGame;
    private boolean waiting;
    private COLOR color;
    private STATE state;

    public enum COLOR {RED, WHITE, NONE}
    public enum STATE {NOTPLAYING, SPECTATING, PLAYING, WON, LOST}

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    /**
     * Player: Constructor for Player that assigns the name, current GameID,
     *         whether they're in-game or not, and their color if they are.
     *         Note: All variables are default except for the name.
     *
     * @param name  Name of the Player
     */
    public Player(String name) {
        this.name = name;
        this.currentGameID = "";
        this.waiting = false;
        this.inGame = false;
        this.color = COLOR.NONE;
        this.state = STATE.NOTPLAYING;
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * assignToGame: Assigns this player to the game given by the gameID
     *
     * @param gameID    The gameID of the game the player is to be assigned to
     */
    public void assignToGame(String gameID) {
        this.currentGameID = gameID;
    }

    /**
     * readyToPlay: Returns if the player is ready to play
     *              Note: Only returns true if they have a gameID, but are not currently inGame
     * @return      If they're ready to play or not
     */
    public boolean readyToPlay() {

        // They have a gameID but are not marked as playing
        return !currentGameID.equals("") && !inGame;
    }

    /**
     * playing: Sets the playing status of the Player to true
     */
    public void playing() {
        inGame = true;
        this.state = STATE.PLAYING;
    }

    /**
     * spectating: Sets the state of the Player to spectating
     */
    public void spectating() {
        this.state = STATE.SPECTATING;
    }

    /**
     * isSpectating: Returns if this Player is spectating a game or not
     */
    public boolean isSpectating() {
        return this.state == STATE.SPECTATING;
    }

    /**
     * doneSpectating: Sets the state of the Player to Not Playing
     */
    public void doneSpectating() {
        this.state = STATE.NOTPLAYING;
    }

    /**
     * isPlaying: Returns if this Player is playing a game or not
     */
    public boolean isPlaying() {
        return this.inGame;
    }

    /**
     * isWaiting: Returns if this Player is waiting to play a game or not
     */
    public boolean isWaiting() {
        return this.waiting;
    }

    /**
     * waitingStatus: Sets the waiting status of the current player to whatever is passed in
     */
    public void waitingStatus(boolean waiting) {
        this.waiting = waiting;
    }

    /**
     * donePlaying: Sets the playing status of the Player to false, removes their color
     */
    public void donePlaying() {
        inGame = false;
        this.state = STATE.NOTPLAYING;
        color = COLOR.NONE;
    }

    /**
     * playerWon: Updates the Player's state to WON or LOST depending on the boolean passed in
     *
     * @param hasWon        Boolean for if they have won or not
     */
    public void playerWon(boolean hasWon) {
        this.state = STATE.LOST;
        if (hasWon) {
            this.state = STATE.WON;
        }
    }

    /**
     * Removes this player from an active game
     *
     */
    public void removeFromGame() { this.currentGameID = ""; }

    /**
     * setColor: Sets the color of the Player
     *
     * @param color     The color they are to be assigned to
     */
    public void setColor(COLOR color) {
        this.color = color;
    }

    /**
     * getColor: Get the color of this Player
     *
     * @return  this.color
     */
    public COLOR getColor() {
        return color;
    }

    /**
     * getName: Gets the name of this Player
     *
     * @return  this.name
     */
    public String getName() {
        return this.name;
    }

    /**
     * nameEquals: Returns whether this Player's name is equal to the name param
     *
     * @param name      name to compare against
     * @return          If this Player's name is the same as name param
     */
    public boolean nameEquals(String name) {
        return this.name.equals(name);
    }

    /**
     * stateEquals: Returns whether this PLayer's state is equal to the state param
     *
     * @param state     State to compare against
     * @return          Whether they are equal or not
     */
    public boolean stateEquals(Player.STATE state) {
        return state == this.state;
    }

    /**
     * getGameID: Gets the current game ID of this Player
     *
     * @return  this.currentGameID
     */
    public String getGameID() {
        return this.currentGameID;
    }

    /**
     * toString: Returns the string version of the player's name
     *
     * @return      This Player's name
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * debugString: used for degubbing purposes, returns this Player's name
     *              along with their currentGameID in string form
     *
     * @return      String of name and currentGameID
     */
    public String debugString() {
        return name + " with currentGameID " + currentGameID;
    }

    /**
     * equals: Judges whether this Player is equal to the other Object based on the Player names
     *
     * @param other     Another object to be compared to
     * @return          If they're equal or not
     */
    @Override
    public boolean equals(Object other) {
        if (other instanceof Player) {

            // Two players are equal if they have the same name
            return ((Player) other).name.equals(this.name);
        }
        return false;
    }
}

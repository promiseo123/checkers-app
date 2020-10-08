package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby {

    // --------------------------------- VARIABLES --------------------------------- //

    private List<Player> players = new ArrayList<>();

    // --------------------------------- METHODS --------------------------------- //

    /**
     * signIn: Evaluates the inputted username and determined when it's valid or not.
     *         If it is, create a new player with that name. If not, return null.
     *
     * @param name      Inputted name
     * @return          Player object with that name
     */
    public Player signIn(String name){
        /*
        Evaluates given playername and returns Player if valid
         */
        if (name.matches("[A-Za-z0-9\\s]+")&&!this.players.contains(new Player(name))){
            //String consists of at least 1 alphanumeric character.
            Player newPlayer = new Player(name);
            players.add(newPlayer);
            return newPlayer;
        }
        else return null;
    }

    /**
     * getPlayers: Returns the ArrayList of all current players
     *
     * @return      this.players
     */
    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * getPlayer: Returns a player based on the inputted name by cycling through
     *            all players and comparing the names
     *
     * @param name      Name you wanna look up
     * @return          Player of that name (if found), otherwise null
     */
    public Player getPlayer(String name) {
        for (Player player : players) {
            if (player.nameEquals(name)) {
                return player;
            }
        }
        return null;
    }

    /**
     * getOtherPlayers: Returns a list of all players except the one of
     *                  the specified name
     *
     * @param ignoreName    Player to be ignored
     * @return              ArrayList of all other players
     */
    public List<Player> getOtherPlayers(String ignoreName) {

        // Perhaps there is a better way to do this (collection method of some sort?)
        List<Player> otherPlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.nameEquals(ignoreName)) {
                if (!player.isPlaying()) {
                    otherPlayers.add(player);
                }
            }
        }
        if (otherPlayers.size() >= 1) {
            return otherPlayers;
        }
        return null;
    }

    /**
     * assignPlayerToGame: Finds the specified player and assigns them to
     *                     the game with the given ID
     *
     * @param playerName    Player name to add
     * @param gameID        Game ID of game to add them to
     */
    public void assignPlayerToGame(String playerName, String gameID) {
        for (Player player : players) {
            if (player.nameEquals(playerName)) {
                player.assignToGame(gameID);
                break;
            }
        }
    }

    /**
     * markPlayerAsPlaying: Mark the specified player as in a game
     *
     * @param playerName    The player to mark as playing
     */
    public void markPlayerAsPlaying(String playerName) {
        for (Player player : players) {
            if (player.nameEquals(playerName)) {
                player.playing();
                break;
            }
        }
    }

    /**
     * markPlayerWithColor: Marks the specified player with a given color
     *
     * @param playerName    The name of the player to mark
     * @param color         The color to mark them as
     */
    public void markPlayerWithColor(String playerName, Player.COLOR color) {
        for (Player player : players) {
            if (player.nameEquals(playerName)) {
                player.setColor(color);
                break;
            }
        }
    }

}

package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * PlayerLobby keeps track of all Players that sign in to the game.
 *
 * @author Promise Omiponle
 */
public class PlayerLobby {
    /** holds all the Players in a list. */
    private List<Player> players= new ArrayList<>();

    /**
     * Sign a Player in if the Player is not already.
     *
     * @param name the "unique" name of the Player
     * @return the Player that was signed in
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
     * Get the list of all the signed-in Players
     *
     * @return the list of Players
     */
    public List<Player> getPlayers() {
        return this.players;
    }
    /**
     * Get the list of all Players not including the current one viewing the Home Screen
     *
     * @return list of all other Players
     */
    public List<Player> getOtherPlayers(String ignoreName) {

        // Perhaps there is a better way to do this (collection method of some sort?)
        List<Player> otherPlayers = new ArrayList<>();
        for (Player player : players) {
            if (!player.getName().equals(ignoreName)) {
                otherPlayers.add(player);
            }
        }
        if (otherPlayers.size() >= 1) {
            return otherPlayers;
        }
        return null;
    }

}

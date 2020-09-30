package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby {

    private List<Player> players = new ArrayList<>();

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

    public List<Player> getPlayers() {
        return this.players;
    }

    public Player getPlayer(String name) {
        for (Player player : players) {
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }

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

    public void assignPlayerToGame(String playerName, String gameID) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                player.assignToGame(gameID);
                break;
            }
        }
    }

    public void markPlayerAsPlaying(String playerName) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                player.playing();
                break;
            }
        }
    }

    public void markPlayerWithColor(String playerName, Player.COLOR color) {
        for (Player player : players) {
            if (player.getName().equals(playerName)) {
                player.setColor(color);
                break;
            }
        }
    }

}

package com.webcheckers.appl;

import com.webcheckers.model.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerLobby {

    private List<Player> players= new ArrayList<>();
    public Player signIn(String name){
        /*
        Evaluates given playername and returns Player if valid
         */
        if (name.matches("[A-Za-z0-9\\\\s]+")&&!this.players.contains(new Player(name))){
            //String consists of at least 1 alphanumeric character.
            Player newplayer = new Player(name);
            players.add(newplayer);
            return newplayer;
        }
        else return null;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

}

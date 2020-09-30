package com.webcheckers.appl;

import com.webcheckers.model.Player;

public class PlayerLobby {
    public Player signIn(String name){
        /*
        Evaluates given playername and returns Player if valid
         */
        if (name.matches("[A-Za-z0-9]+")){ //String consists of at least 1 alphanumeric character.
            Player newplayer = new Player(name);
            //$nameofarraylist.add(newPlayer);
            return newplayer;
        }
        else return null;
    }

}

package com.webcheckers.model;

import java.util.Random;

public class Game {

    private String gameID;
    private Player redPlayer;
    private Player whitePlayer;
    private TURN turn;
    public enum TURN {red, white};

    private Board board;

    public Game(String gameID, Player redPlayer, Player whitePlayer) {
        this.gameID = gameID;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = TURN.red;
        this.board = new Board();

    }

    public Player getRedPlayer() {
        return redPlayer;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public TURN getTurn() {
        return turn;
    }

    public Board getBoard() {
        return board;
    }

    public void switchTurns() {
        if (turn == TURN.red) {
            turn = TURN.white;
        }
        else {
           turn = TURN.red;
        }
    }


    public static String generateRandomGameID() {
        Random random = new Random();
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            builder.append(alphabet.charAt(random.nextInt(alphabet.length())));
            builder.append(random.nextInt(10));
        }
        return builder.toString();
    }
}

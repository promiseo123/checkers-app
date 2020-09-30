package com.webcheckers.model;

import java.util.Random;

public class Game {

    private String gameID;
    private Player redPlayer;
    private Player whitePlayer;
    private TURN turn;
    public enum TURN {red, white};

    private BoardView board;

    /**
     * Game: Constructor for a game that assigns relevant variables
     *
     * @param gameID        The game ID
     * @param redPlayer     The red player
     * @param whitePlayer   The white player
     */
    public Game(String gameID, Player redPlayer, Player whitePlayer) {
        this.gameID = gameID;
        this.redPlayer = redPlayer;
        this.whitePlayer = whitePlayer;
        this.turn = TURN.red;
        this.board = new BoardView();

    }

    /**
     * getRedPlayer: Returns the red player
     *
     * @return      The red player
     */
    public Player getRedPlayer() {
        return redPlayer;
    }

    /**
     * getWhitePlayer: Returns the white player
     *
     * @return      The white player
     */
    public Player getWhitePlayer() {
        return whitePlayer;
    }

    /**
     * getTurn: Returns who's turn it is (red's turn or white's turn?)
     *
     * @return      Who's turn it is
     */
    public TURN getTurn() {
        return turn;
    }

    /**
     * getBoard: Returns the board for this game
     *
     * @return  this.board
     */
    public BoardView getBoard() {
        return this.board;
    }

    /**
     * switchTurns: Switches who's turn it is (red to white, or white to red)
     */
    public void switchTurns() {
        if (turn == TURN.red) {
            turn = TURN.white;
        }
        else {
           turn = TURN.red;
        }
    }

    /**
     * Generates a random sequence of characters to make a unique ID for a game
     *
     * @return      Unique ID
     */
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

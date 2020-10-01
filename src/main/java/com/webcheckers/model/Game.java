package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;

import java.util.Random;

public class Game {

    private String gameID;
    private Player redPlayer;
    private Player whitePlayer;
    private TURN turn;
    public enum TURN {RED, WHITE};

    private Board board;

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
        this.turn = TURN.RED;
        this.board = new Board();

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
    public Board getBoard() {
        return this.board;
    }

    public BoardView getBoardView(Player.COLOR color) {
        return this.board.getBoardView(color);
    }

    /**
     * switchTurns: Switches who's turn it is (red to white, or white to red)
     */
    public void switchTurns() {
        if (turn == TURN.RED) {
            turn = TURN.WHITE;
        }
        else {
           turn = TURN.RED;
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

package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;

import java.util.Random;

/**
 * Game: Represents a single Checkers game. Contains general game information: state of the board,
 *       participating players, turn order, etc.
 */
public class Game {

    // --------------------------------- VARIABLES --------------------------------- //

    private String gameID;
    private Player redPlayer;
    private Player whitePlayer;
    private TURN turn;
    public enum TURN {RED, WHITE}
    private Board board;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

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

    // --------------------------------- METHODS --------------------------------- //

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
     * Returns the opposite player
     *
     * @return      other player
     */
    public Player getOpponent(Player current){
        if (isRedPlayer(current)) return getWhitePlayer();
        else return getRedPlayer();
    }

    /**
     * isRedPlayer: Returns whether the player passed in is the redPlayer in this game
     *
     * @param player    The Player who we want to see if they're the red player
     * @return          If the Player passed in is the red player of the current Game
     */
    public boolean isRedPlayer(Player player) {
        return player.equals(this.redPlayer);
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

    /**
     * getBoardView: Returns the board view for the given color
     *
     * @param color     The color of the player whose view is being requested
     * @return          The view of the specified player
     */
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
     * removes specified player from this game by color
     */
    public void removePlayer (Player player){
        String color = player.getColor().toString();
        System.out.println(color);
        if (color.equals("RED")) this.redPlayer = null;
        else if (color.equals("WHITE")) this.whitePlayer = null;
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

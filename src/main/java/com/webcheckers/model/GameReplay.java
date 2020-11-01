package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;

import java.util.HashMap;
import java.util.Map;

/**
 * GameReplay: A collection of BoardViews, belonging to a Game, that is populated as the game is played each time a move is submitted.
 * BoardViews are identified by a state, where state=0 represents the starting state, state=1 represents the board after the first move is made, etc.
 * BoardViews are always from the perspective of the Red player.
 *
 * @author: Austin Cepalia (acc5989)
 */
public class GameReplay {

    // --------------------------------- VARIABLES --------------------------------- //

    private int currentStateNumber;
    private Map<Integer, BoardView> boardStates;

    // --------------------------------- CONSTRUCTORS --------------------------------- //

    public GameReplay() {
        currentStateNumber = 0;
        boardStates = new HashMap<>();
    }

    // --------------------------------- METHODS --------------------------------- //

    /**
     * Add a new BoardView to the collection. The state number is tracked and applied automatically.
     * @param view The BoardView to add
     */
    public void addBoardState(BoardView view) {
        boardStates.put(currentStateNumber, view);

        /* Enable for debugging
        System.out.println("Board state " + currentStateNumber + " added.");
        System.out.println("Current board:");
        System.out.println(getBoardViewAtState(currentStateNumber));
         */

        currentStateNumber++;
    }

    /**
     *
     * @param stateNumber The state number of the BoardView to obtain.
     * @return Desired BoardView for that state.
     */
    public BoardView getBoardViewAtState(int stateNumber) {
        return boardStates.get(stateNumber);
    }

    /**
     * How many BoardViews are stored in the collection?
     * @return number of BoardViews in the collection
     */
    public int getBoardViewCount() {
        return boardStates.size();
    }

    /**
     * For debugging: Print a list of all the BoardViews in this GameReplay
     */
    public void printAllBoardStates() {
        int i = 0;
        for (BoardView view : boardStates.values()) {
            System.out.println("BoardState " + i + ":");
            System.out.println(view);
            i++;
        }
    }
}

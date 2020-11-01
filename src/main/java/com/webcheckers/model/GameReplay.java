package com.webcheckers.model;

import com.webcheckers.ui.BoardView.BoardView;

import java.util.HashMap;
import java.util.Map;

public class GameReplay {

    private int currentStateNumber;
    private Map<Integer, BoardView> boardStates;

    public GameReplay() {
        currentStateNumber = 0;
        boardStates = new HashMap<Integer, BoardView>();
    }

    public void addBoardState(BoardView view) {
        boardStates.put(currentStateNumber, view);
        currentStateNumber++;
    }

    public BoardView getBoardViewAtState(int stateNumber) {
        return boardStates.get(stateNumber);
    }
}

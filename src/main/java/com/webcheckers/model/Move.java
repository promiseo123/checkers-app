package com.webcheckers.model;

import com.webcheckers.ui.BoardView.Space;

public class Move {

    private Position start;
    private Position end;

    public enum TYPE {SINGLE, MULTI}

    private Move.TYPE type;

    public Move(Position start, Position end) {
        this.start = start;
        this.end = end;
    }

    public void setType(Move.TYPE type) {
        this.type = type;
    }

    public Position getStart() {
        return this.start;
    }

    public Position getEnd() {
        return this.end;
    }

    public TYPE getType() {
        return this.type;
    }

}

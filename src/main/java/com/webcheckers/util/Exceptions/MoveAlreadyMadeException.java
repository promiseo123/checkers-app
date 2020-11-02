package com.webcheckers.util.Exceptions;

/**
 * This exception is thrown when the player attempts to make a move that was already made.
 *
 * @author Austin Cepalia acc5989
 */

import java.util.logging.Logger;

public class MoveAlreadyMadeException extends InvalidMoveException {

    private static final Logger LOG = Logger.getLogger(MoveAlreadyMadeException.class.getName());

    public MoveAlreadyMadeException(String message) {
        super(message);
    }

    @Override
    protected void logWhatHappened() {
        LOG.warning("A player's attempted move threw a MoveAlreadyMadeException.");
    }
}

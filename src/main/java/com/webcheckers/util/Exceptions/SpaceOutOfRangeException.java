package com.webcheckers.util.Exceptions;

import java.util.logging.Logger;

/**
 * This exception is thrown when the player attempts to move a piece to a space out of range.
 *
 * @author Austin Cepalia acc5989
 */

public class SpaceOutOfRangeException extends InvalidMoveException {
    private static final Logger LOG = Logger.getLogger(SpaceOutOfRangeException.class.getName());

    public SpaceOutOfRangeException(String message) {
        super(message);
    }

    @Override
    protected void logWhatHappened() {
        LOG.warning("A player's attempted move threw a SpaceOutOfRangeException.");
    }
}

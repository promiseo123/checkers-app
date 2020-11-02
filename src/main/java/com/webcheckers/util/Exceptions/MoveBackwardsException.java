package com.webcheckers.util.Exceptions;

/**
 * This exception is thrown when the player attempts to move backwards on a piece that is not allowed to do that.
 *
 * @author Austin Cepalia acc5989
 */

import java.util.logging.Logger;

public class MoveBackwardsException extends InvalidMoveException {
    private static final Logger LOG = Logger.getLogger(MoveBackwardsException.class.getName());

    public MoveBackwardsException(String message) {
        super(message);
    }

    @Override
    protected void logWhatHappened() {
        LOG.warning("A player's attempted move threw a MoveBackwardsException.");
    }
}

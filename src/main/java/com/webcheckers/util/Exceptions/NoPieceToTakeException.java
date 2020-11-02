package com.webcheckers.util.Exceptions;

/**
 * This exception is thrown when the player attempts to take a piece that does not exist.
 *
 * @author Austin Cepalia acc5989
 */

import java.util.logging.Logger;

public class NoPieceToTakeException extends InvalidMoveException {
    private static final Logger LOG = Logger.getLogger(NoPieceToTakeException.class.getName());

    public NoPieceToTakeException(String message) {
        super(message);
    }

    @Override
    protected void logWhatHappened() {
        LOG.warning("A player's attempted move threw a NoPieceToTakeException.");
    }
}

package com.webcheckers.util.Exceptions;

/**
 * The base exception class from which all other custom InvalidMoveException types inherit.
 * This is used to satisfy the O in SOLID.
 * Subclasses are responsible for logging what happened.
 *
 * @author Austin Cepalia acc5989
 */

public abstract class InvalidMoveException extends RuntimeException {
    public InvalidMoveException(String message) {
        super(message);
        logWhatHappened();
    }
    protected abstract void logWhatHappened();
}

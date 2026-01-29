package com.flipfit.exception;

/**
 * The Class UserNotFoundException.
 *
 * @author team IOTA

 */
public class UserNotFoundException extends Exception {

    /**
     * Instantiates a new user not found exception.
     *
     * @param message the message
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}

package com.flipfit.exception;

/// Classs level Comminting
/**
 * The Class UserNotFoundException.
 *
 * @author krishna
 * @ClassName "UserNotFoundException"
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

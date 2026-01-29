package com.flipfit.exception;

/**
 * Exception thrown when a provided email address is invalid or fails
 * verification.
 * 
 * @author team IOTA
 */
public class InvalidEmailException extends Exception {

    /**
     * Constructs a new InvalidEmailException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidEmailException(String message) {
        super(message);
    }
}

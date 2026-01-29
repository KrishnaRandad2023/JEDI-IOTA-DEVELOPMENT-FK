package com.flipfit.exception;

/**
 * Exception thrown when a provided mobile number is invalid or fails
 * verification.
 * 
 * @author team IOTA
 */
public class InvalidMobileException extends Exception {

    /**
     * Constructs a new InvalidMobileException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidMobileException(String message) {
        super(message);
    }
}

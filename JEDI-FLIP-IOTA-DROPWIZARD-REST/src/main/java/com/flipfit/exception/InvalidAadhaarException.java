package com.flipfit.exception;

/**
 * Exception thrown when a provided Aadhaar number is invalid or fails
 * verification.
 * 
 * @author team IOTA
 */
public class InvalidAadhaarException extends Exception {

    /**
     * Constructs a new InvalidAadhaarException with the specified detail message.
     *
     * @param message the detail message
     */
    public InvalidAadhaarException(String message) {
        super(message);
    }
}

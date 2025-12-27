package com.airtribe.learntrack.exception;

/**
 * Custom exception thrown when user provides invalid input.
 */
public class InvalidInputException extends Exception {

    public InvalidInputException() {
        super("Invalid input provided");
    }

    public InvalidInputException(String message) {
        super(message);
    }

    public InvalidInputException(String fieldName, String reason) {
        super("Invalid " + fieldName + ": " + reason);
    }
}

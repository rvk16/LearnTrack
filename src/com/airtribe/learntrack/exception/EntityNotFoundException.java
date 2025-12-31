package com.airtribe.learntrack.exception;

/**
 * Custom exception thrown when a requested entity (Student, Course, Enrollment)
 * is not found in the system.
 */
public class EntityNotFoundException extends Exception {

    public EntityNotFoundException() {
        super("Entity not found");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String entityType, int id) {
        super(entityType + " with ID " + id + " not found");
    }
}

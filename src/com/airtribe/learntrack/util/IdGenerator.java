package com.airtribe.learntrack.util;

/**
 * Utility class for generating unique IDs for entities.
 * Uses static fields and methods to maintain counters across the application.
 */
public class IdGenerator {
    // Static counters for each entity type
    private static int studentIdCounter = 0;
    private static int courseIdCounter = 0;
    private static int enrollmentIdCounter = 0;

    // Private constructor to prevent instantiation
    private IdGenerator() {
    }

    /**
     * Generates the next unique ID for a Student.
     * @return the next student ID
     */
    public static int getNextStudentId() {
        return ++studentIdCounter;
    }

    /**
     * Generates the next unique ID for a Course.
     * @return the next course ID
     */
    public static int getNextCourseId() {
        return ++courseIdCounter;
    }

    /**
     * Generates the next unique ID for an Enrollment.
     * @return the next enrollment ID
     */
    public static int getNextEnrollmentId() {
        return ++enrollmentIdCounter;
    }

    /**
     * Gets the current student ID counter value (for display purposes).
     * @return current student counter
     */
    public static int getCurrentStudentCount() {
        return studentIdCounter;
    }

    /**
     * Gets the current course ID counter value (for display purposes).
     * @return current course counter
     */
    public static int getCurrentCourseCount() {
        return courseIdCounter;
    }

    /**
     * Gets the current enrollment ID counter value (for display purposes).
     * @return current enrollment counter
     */
    public static int getCurrentEnrollmentCount() {
        return enrollmentIdCounter;
    }

    /**
     * Resets all counters (useful for testing).
     */
    public static void resetCounters() {
        studentIdCounter = 0;
        courseIdCounter = 0;
        enrollmentIdCounter = 0;
    }
}

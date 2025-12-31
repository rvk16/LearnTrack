package com.airtribe.learntrack.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Utility class for generating unique IDs for entities.
 * Uses AtomicInteger for thread-safe ID generation across the application.
 */
public class IdGenerator {
    // Thread-safe counters for each entity type
    private static final AtomicInteger studentIdCounter = new AtomicInteger(0);
    private static final AtomicInteger courseIdCounter = new AtomicInteger(0);
    private static final AtomicInteger enrollmentIdCounter = new AtomicInteger(0);

    // Private constructor to prevent instantiation
    private IdGenerator() {
    }

    /**
     * Generates the next unique ID for a Student.
     * @return the next student ID
     */
    public static int getNextStudentId() {
        return studentIdCounter.incrementAndGet();
    }

    /**
     * Generates the next unique ID for a Course.
     * @return the next course ID
     */
    public static int getNextCourseId() {
        return courseIdCounter.incrementAndGet();
    }

    /**
     * Generates the next unique ID for an Enrollment.
     * @return the next enrollment ID
     */
    public static int getNextEnrollmentId() {
        return enrollmentIdCounter.incrementAndGet();
    }

    /**
     * Gets the current student ID counter value (for display purposes).
     * @return current student counter
     */
    public static int getCurrentStudentCount() {
        return studentIdCounter.get();
    }

    /**
     * Gets the current course ID counter value (for display purposes).
     * @return current course counter
     */
    public static int getCurrentCourseCount() {
        return courseIdCounter.get();
    }

    /**
     * Gets the current enrollment ID counter value (for display purposes).
     * @return current enrollment counter
     */
    public static int getCurrentEnrollmentCount() {
        return enrollmentIdCounter.get();
    }

    /**
     * Resets all counters (useful for testing).
     * WARNING: This method should only be used in test environments.
     * Resetting IDs in production can cause data integrity issues.
     */
    public static void resetCounters() {
        studentIdCounter.set(0);
        courseIdCounter.set(0);
        enrollmentIdCounter.set(0);
    }
}

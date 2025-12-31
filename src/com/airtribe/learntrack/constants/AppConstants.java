package com.airtribe.learntrack.constants;

/**
 * Application-wide constants.
 */
public final class AppConstants {

    // Private constructor to prevent instantiation
    private AppConstants() {
    }

    // Application info
    public static final String APP_NAME = "LearnTrack";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_DESCRIPTION = "Student & Course Management System";

    // Display formatting
    public static final String LINE_SEPARATOR = "=".repeat(50);
    public static final String THIN_SEPARATOR = "-".repeat(50);

    // Messages
    public static final String WELCOME_MESSAGE = "Welcome to " + APP_NAME + " - " + APP_DESCRIPTION;
    public static final String GOODBYE_MESSAGE = "Thank you for using " + APP_NAME + ". Goodbye!";
    public static final String INVALID_OPTION_MESSAGE = "Invalid option. Please try again.";
    public static final String NO_STUDENTS_MESSAGE = "No students found in the system.";
    public static final String NO_COURSES_MESSAGE = "No courses found in the system.";
    public static final String NO_ENROLLMENTS_MESSAGE = "No enrollments found.";

    // Prompts
    public static final String ENTER_CHOICE_PROMPT = "Enter your choice: ";
    public static final String PRESS_ENTER_PROMPT = "Press Enter to continue...";
}

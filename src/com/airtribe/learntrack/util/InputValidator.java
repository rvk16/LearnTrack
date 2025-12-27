package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;
import java.util.regex.Pattern;

/**
 * Utility class for validating user input.
 */
public class InputValidator {

    // Email validation regex pattern
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    // Private constructor to prevent instantiation
    private InputValidator() {
    }

    /**
     * Validates that a string is not null or empty.
     * @param value the string to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if validation fails
     */
    public static void validateNotEmpty(String value, String fieldName) throws InvalidInputException {
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidInputException(fieldName, "cannot be empty");
        }
    }

    /**
     * Validates that an email has a valid format using regex.
     * @param email the email to validate
     * @throws InvalidInputException if validation fails
     */
    public static void validateEmail(String email) throws InvalidInputException {
        if (email == null || email.trim().isEmpty()) {
            return; // Email is optional
        }
        if (!EMAIL_PATTERN.matcher(email.trim()).matches()) {
            throw new InvalidInputException("email", "must be a valid email format");
        }
    }

    /**
     * Validates that a number is positive.
     * @param value the number to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if validation fails
     */
    public static void validatePositive(int value, String fieldName) throws InvalidInputException {
        if (value <= 0) {
            throw new InvalidInputException(fieldName, "must be a positive number");
        }
    }

    /**
     * Validates that a number is non-negative.
     * @param value the number to validate
     * @param fieldName the name of the field (for error messages)
     * @throws InvalidInputException if validation fails
     */
    public static void validateNonNegative(int value, String fieldName) throws InvalidInputException {
        if (value < 0) {
            throw new InvalidInputException(fieldName, "cannot be negative");
        }
    }

    /**
     * Parses a string to integer with validation.
     * @param value the string to parse
     * @param fieldName the name of the field (for error messages)
     * @return the parsed integer
     * @throws InvalidInputException if parsing fails
     */
    public static int parseInteger(String value, String fieldName) throws InvalidInputException {
        if (value == null) {
            throw new InvalidInputException(fieldName, "must be a valid number");
        }
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName, "must be a valid number");
        }
    }

    /**
     * Validates menu option is within valid range.
     * @param option the selected option
     * @param min minimum valid option
     * @param max maximum valid option
     * @throws InvalidInputException if option is out of range
     */
    public static void validateMenuOption(int option, int min, int max) throws InvalidInputException {
        if (option < min || option > max) {
            throw new InvalidInputException("option", "must be between " + min + " and " + max);
        }
    }
}

package com.cognizant.ormlearn.service.exception;

/**
 * CountryNotFoundException - Custom Exception Class
 *
 * Handbook: com.cognizant.ormlearn.service.exception.CountryNotFoundException
 * (Hands on 6 - Find a country based on country code)
 *
 * Thrown when a country is not found for a given country code.
 *
 * Extends Exception (checked exception) - the caller MUST handle or declare it.
 *
 * This follows the standard custom exception pattern:
 *   - Extends Exception for a checked exception (must be caught or declared)
 *   - Takes a String message in the constructor
 *   - Passes the message to the parent Exception via super(message)
 */
public class CountryNotFoundException extends Exception {

    // Serial version UID for serialization compatibility
    private static final long serialVersionUID = 1L;

    /**
     * Constructor with a descriptive error message.
     *
     * @param message describes why the exception was thrown
     *                e.g., "Country not found for code: XY"
     */
    public CountryNotFoundException(String message) {
        super(message);
    }
}

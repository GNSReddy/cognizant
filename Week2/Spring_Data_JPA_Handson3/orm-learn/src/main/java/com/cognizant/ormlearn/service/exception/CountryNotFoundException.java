package com.cognizant.ormlearn.service.exception;

/**
 * CountryNotFoundException - Thrown when a country code is not found.
 * Handbook: Spring Data JPA Hands-on 3
 */
public class CountryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException(String message) {
        super(message);
    }
}

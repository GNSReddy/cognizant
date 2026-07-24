package com.cognizant.springlearn.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception thrown when a country code is not found.
 *
 * @ResponseStatus causes Spring MVC to respond with HTTP 404 NOT_FOUND
 * and the specified reason message whenever this exception propagates
 * out of a controller method.
 *
 * The caller of the REST service receives:
 * {
 *   "timestamp": "...",
 *   "status": 404,
 *   "error": "Not Found",
 *   "message": "Country not found",
 *   "path": "/countries/az"
 * }
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Country not found")
public class CountryNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public CountryNotFoundException() {
        super("Country not found");
    }

    public CountryNotFoundException(String message) {
        super(message);
    }
}

package com.cognizant.springlearn;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Global Exception Handler for all controllers.
 *
 * <p>@ControllerAdvice makes this class intercept exceptions thrown by any controller,
 * providing a centralized, consistent error response format across the entire application.</p>
 *
 * <p>Extends ResponseEntityExceptionHandler to override standard Spring MVC exception handling.
 * This is more powerful than individual @ResponseStatus annotations as it gives full control
 * over the response body format.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles validation errors triggered by @Valid annotation.
     * This is called when a request body fails bean validation constraints
     * (e.g., @NotNull, @Size, @Min).
     *
     * <p>Advantage over per-controller validation: a single handler works for ALL
     * controllers, so both CountryController and EmployeeController benefit.</p>
     *
     * @param ex      MethodArgumentNotValidException containing all validation errors
     * @param headers Response headers
     * @param status  HTTP status (400 Bad Request)
     * @param request The web request
     * @return ResponseEntity with timestamp, status, and list of error messages
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        LOGGER.info("Start");
        LOGGER.debug("Validation failed: {}", ex.getMessage());

        // Map that contains the error details
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        // Extract all validation error messages from field errors
        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        // Add errors to the response map
        body.put("errors", errors);

        LOGGER.debug("Validation errors: {}", errors);
        LOGGER.info("End");
        return new ResponseEntity<>(body, headers, status);
    }

    /**
     * Handles errors when the HTTP request body cannot be read or parsed.
     * This occurs BEFORE validation, for example when a string value is
     * provided for a numeric field (e.g., id: "abc" instead of id: 123).
     *
     * @param ex      HttpMessageNotReadableException containing the parse error
     * @param headers Response headers
     * @param status  HTTP status (400 Bad Request)
     * @param request The web request
     * @return ResponseEntity with timestamp, status, error, and message details
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        LOGGER.info("Start");
        LOGGER.debug("Message not readable: {}", ex.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", "Bad Request");

        // If the root cause is an InvalidFormatException, identify the problematic field
        if (ex.getCause() instanceof InvalidFormatException) {
            final Throwable cause = ex.getCause();
            for (InvalidFormatException.Reference reference : ((InvalidFormatException) cause).getPath()) {
                body.put("message", "Incorrect format for field '" + reference.getFieldName() + "'");
            }
        } else {
            body.put("message", "Malformed JSON request");
        }

        LOGGER.info("End");
        return new ResponseEntity<>(body, headers, status);
    }
}

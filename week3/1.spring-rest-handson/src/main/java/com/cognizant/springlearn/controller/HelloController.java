package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for the Hello World endpoint.
 *
 * <p>Demonstrates the most basic Spring REST controller.
 * @RestController combines @Controller and @ResponseBody,
 * so the return value is written directly to the HTTP response body.</p>
 *
 * URL: GET /hello
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    public HelloController() {
        LOGGER.debug("Inside HelloController Constructor.");
    }

    /**
     * Returns a hard-coded "Hello World!!" string.
     * Spring serializes this as plain text (Content-Type: text/plain).
     *
     * @return "Hello World!!" string
     */
    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("Start");
        String response = "Hello World!!";
        LOGGER.debug("sayHello() returning: {}", response);
        LOGGER.info("End");
        return response;
    }
}

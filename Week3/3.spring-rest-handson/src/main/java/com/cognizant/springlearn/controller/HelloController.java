package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController – carried forward from Handbook 2.
 * GET /hello → "Hello World!!"
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    public HelloController() {
        LOGGER.debug("Inside HelloController Constructor.");
    }

    @GetMapping("/hello")
    public String sayHello() {
        LOGGER.info("Start");
        String response = "Hello World!!";
        LOGGER.debug("sayHello() returning: {}", response);
        LOGGER.info("End");
        return response;
    }
}

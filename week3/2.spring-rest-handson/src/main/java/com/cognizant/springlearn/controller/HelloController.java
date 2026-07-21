package com.cognizant.springlearn.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller – Hello World endpoint.
 *
 * @RestController = @Controller + @ResponseBody.
 * The return value of each method is written directly to the HTTP response body
 * (no view resolution). Spring converts the return type to the appropriate
 * Content-Type (String → text/plain, Object → application/json via Jackson).
 *
 * Handbook 2 – "Hello World RESTful Web Service"
 *   Method : GET
 *   URL    : /hello
 *   Response: Hello World!!
 *
 * Test in browser : http://localhost:8083/hello
 * Test in Postman : GET http://localhost:8083/hello
 * Test with curl  : curl http://localhost:8083/hello
 */
@RestController
public class HelloController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    public HelloController() {
        LOGGER.debug("Inside HelloController Constructor.");
    }

    /**
     * Returns a hard-coded "Hello World!!" string.
     *
     * @GetMapping maps HTTP GET /hello to this method.
     * Spring MVC's DispatcherServlet routes the request here based on the URL.
     *
     * @return plain text "Hello World!!"
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

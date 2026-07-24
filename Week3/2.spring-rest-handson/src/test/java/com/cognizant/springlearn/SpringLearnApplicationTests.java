package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.CountryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration test class for the spring-learn application.
 *
 * Handbook 2 – "MockMVC – Test get country service" and
 *              "MockMVC – Test get country service for exceptional scenario"
 *
 * @SpringBootTest
 *   Loads the FULL Spring application context (all beans, controllers, services, DAOs).
 *   Unlike unit tests which mock dependencies, this tests the complete stack.
 *
 * @AutoConfigureMockMvc
 *   Auto-configures MockMvc so HTTP requests can be simulated without starting
 *   a real HTTP server. Spring's DispatcherServlet handles requests in-process.
 *
 * MockMvc request flow:
 *   mvc.perform(get("/url"))              – build and execute a mock GET request
 *   .andExpect(status().isOk())           – assert HTTP 200
 *   .andExpect(jsonPath("$.code").exists()) – assert JSON field "code" is present
 *   .andExpect(jsonPath("$.code").value("IN")) – assert JSON field value
 *
 * JsonPath syntax:
 *   $  – root of the JSON document
 *   $.code – field "code" at root level
 *   $[0].code – field "code" of first element in a JSON array
 *
 * Running tests:
 *   Eclipse : right-click SpringLearnApplicationTests → Run As → JUnit Test
 *   Maven   : mvn clean test
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    /**
     * Handbook 2 – "Test loading CountryController"
     * Spring injects CountryController if it is loaded in the application context.
     * The test verifies the controller bean was created successfully.
     */
    @Autowired
    private CountryController countryController;

    /**
     * MockMvc is auto-configured by @AutoConfigureMockMvc.
     * It simulates HTTP requests and captures responses for assertion.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Handbook 2 – contextLoads()
     *
     * Verifies that CountryController is created and wired into the application context.
     * assertNotNull() fails the test if countryController is null (bean not created).
     *
     * The constructor log in CountryController ("Inside CountryController Constructor.")
     * should appear in the test console output, confirming the bean is instantiated.
     */
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
    }

    /**
     * Handbook 2 – "Test service to get the country"
     *
     * Step-by-step as specified in the handbook:
     * 1. perform(get("/countries/country")) – simulate GET /countries/country
     * 2. andExpect(status().isOk())         – HTTP 200 received
     * 3. andExpect(jsonPath("$.code").exists())    – "code" field present in JSON
     * 4. andExpect(jsonPath("$.code").value("IN")) – "code" equals "IN"
     * 5. andExpect(jsonPath("$.name").exists())    – "name" field present
     * 6. andExpect(jsonPath("$.name").value("India")) – "name" equals "India"
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    public void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/country"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    /**
     * Handbook 2 – "MockMVC – Test get country service for exceptional scenario"
     *
     * Sends a GET request with an unknown country code "az".
     * CountryService.getCountry("az") throws CountryNotFoundException.
     * @ResponseStatus(NOT_FOUND) on CountryNotFoundException causes Spring
     * to respond with HTTP 404.
     *
     * andExpect(status().isNotFound()) asserts HTTP 404 is returned.
     *
     * The handbook also mentions checking status().reason("Country Not found").
     * status().reason() checks the HTTP reason phrase in the response header.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    public void testGetCountryException() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/az"));
        actions.andExpect(status().isNotFound());
    }

    /**
     * Additional test – GET /countries returns JSON array with all four countries.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    public void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].code").exists());
        actions.andExpect(jsonPath("$[0].name").exists());
    }

    /**
     * Additional test – GET /hello returns HTTP 200 with "Hello World!!" body.
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    public void testSayHello() throws Exception {
        ResultActions actions = mvc.perform(get("/hello"));
        actions.andExpect(status().isOk());
    }

    /**
     * Additional test – GET /countries/IN (uppercase) returns India (case-insensitive).
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    public void testGetCountryByCodeCaseInsensitive() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/IN"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").value("India"));
    }
}

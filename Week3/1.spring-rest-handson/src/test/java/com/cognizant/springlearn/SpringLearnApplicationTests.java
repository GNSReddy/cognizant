package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.CountryController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for the Spring Learn application.
 *
 * <p>@SpringBootTest loads the full application context for integration testing.
 * @AutoConfigureMockMvc auto-configures MockMvc so HTTP requests can be simulated
 * without actually starting a real HTTP server.</p>
 *
 * <p>MockMvc Test Flow:
 * mvc.perform(get("/url"))       -> Simulates HTTP GET request
 * .andExpect(status().isOk())    -> Asserts HTTP 200 response
 * .andExpect(jsonPath("$.field").exists())  -> Asserts JSON field exists
 * .andExpect(jsonPath("$.field").value("x")) -> Asserts JSON field value</p>
 *
 * <p>Covers Handbook 2 MockMVC exercises:
 * - contextLoads(): Test if CountryController is loaded
 * - testGetCountry(): Test GET /countries/india returns India (code=IN, name=India)
 * - testGetCountryException(): Test GET /countries/az returns 404</p>
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    /**
     * Autowired CountryController to verify Spring loads it correctly.
     * If null, the test fails, indicating Spring could not create the bean.
     */
    @Autowired
    private CountryController countryController;

    /**
     * MockMvc enables testing REST endpoints without a running server.
     * Requests are handled by the DispatcherServlet in a test context.
     */
    @Autowired
    private MockMvc mvc;

    /**
     * Handbook 2 Test: Verifies CountryController is loaded in Spring context.
     * assertNotNull() fails the test if countryController is null (not autowired).
     */
    @Test
    public void contextLoads() {
        assertNotNull(countryController);
    }

    /**
     * Handbook 2 Test: Full end-to-end MockMVC test for GET /countries/india.
     *
     * <p>Test Steps:
     * 1. Perform GET request to /countries/india
     * 2. Assert HTTP status is 200 OK
     * 3. Assert JSON response has "code" field
     * 4. Assert "code" value is "IN"
     * 5. Assert JSON response has "name" field
     * 6. Assert "name" value is "India"</p>
     *
     * <p>@WithMockUser simulates an authenticated user with ROLE_USER
     * so Spring Security doesn't block the request.</p>
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/india"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    /**
     * Handbook 2 Test: Verifies that requesting an unknown country code returns 404.
     *
     * <p>When GET /countries/az is called, CountryService throws CountryNotFoundException,
     * which is annotated with @ResponseStatus(HttpStatus.NOT_FOUND).
     * The test asserts the HTTP response status is 404 Not Found.</p>
     *
     * @throws Exception if MockMvc request fails
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetCountryException() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/az"));
        actions.andExpect(status().isNotFound());
    }

    /**
     * Test: Verifies GET /countries returns a JSON array of countries.
     * @throws Exception if MockMvc request fails
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries"));
        actions.andExpect(status().isOk());
        // Assert the response is a JSON array (non-empty)
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].code").exists());
    }

    /**
     * Test: Verifies GET /employees returns a JSON array of employees.
     * @throws Exception if MockMvc request fails
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllEmployees() throws Exception {
        ResultActions actions = mvc.perform(get("/employees"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].name").exists());
    }

    /**
     * Test: Verifies GET /departments returns a JSON array of departments.
     * @throws Exception if MockMvc request fails
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testGetAllDepartments() throws Exception {
        ResultActions actions = mvc.perform(get("/departments"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].name").exists());
    }

    /**
     * Test: Verifies GET /hello returns Hello World!! string.
     * @throws Exception if MockMvc request fails
     */
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testSayHello() throws Exception {
        ResultActions actions = mvc.perform(get("/hello"));
        actions.andExpect(status().isOk());
    }
}

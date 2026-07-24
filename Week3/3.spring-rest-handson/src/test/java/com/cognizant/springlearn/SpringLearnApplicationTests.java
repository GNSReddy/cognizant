package com.cognizant.springlearn;

import com.cognizant.springlearn.controller.CountryController;
import com.cognizant.springlearn.controller.DepartmentController;
import com.cognizant.springlearn.controller.EmployeeController;
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
 * Integration tests for Handbook 3 – Employee and Department REST services.
 *
 * Tests carried forward from Handbook 2 plus new Handbook 3 tests for:
 *   GET /employees  – returns array of 4 employees with nested department and skills
 *   GET /departments – returns array of 3 departments
 *
 * Each test verifies:
 *   1. HTTP status 200
 *   2. Response is a JSON array
 *   3. First element has expected fields (id, name, etc.)
 */
@SpringBootTest
@AutoConfigureMockMvc
class SpringLearnApplicationTests {

    @Autowired
    private CountryController countryController;

    @Autowired
    private EmployeeController employeeController;

    @Autowired
    private DepartmentController departmentController;

    @Autowired
    private MockMvc mvc;

    // =====================================================================
    // Carried forward from Handbook 2
    // =====================================================================

    @Test
    public void contextLoads() {
        assertNotNull(countryController);
        assertNotNull(employeeController);
        assertNotNull(departmentController);
    }

    @Test
    public void testGetCountry() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/country"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$.code").exists());
        actions.andExpect(jsonPath("$.code").value("IN"));
        actions.andExpect(jsonPath("$.name").exists());
        actions.andExpect(jsonPath("$.name").value("India"));
    }

    @Test
    public void testGetCountryException() throws Exception {
        ResultActions actions = mvc.perform(get("/countries/az"));
        actions.andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllCountries() throws Exception {
        ResultActions actions = mvc.perform(get("/countries"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].code").exists());
    }

    // =====================================================================
    // Handbook 3 – Employee tests
    // =====================================================================

    /**
     * Test GET /employees returns a JSON array of all employees.
     *
     * Verifies:
     *   - HTTP 200
     *   - Response body is a JSON array
     *   - First element has "id", "name", "salary", "permanent", "dateOfBirth"
     *   - First element has nested "department" object with "id" and "name"
     *   - First element has "skills" array with at least one skill
     */
    @Test
    public void testGetAllEmployees() throws Exception {
        ResultActions actions = mvc.perform(get("/employees"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].id").exists());
        actions.andExpect(jsonPath("$[0].name").exists());
        actions.andExpect(jsonPath("$[0].salary").exists());
        actions.andExpect(jsonPath("$[0].permanent").exists());
        actions.andExpect(jsonPath("$[0].dateOfBirth").exists());
        actions.andExpect(jsonPath("$[0].department").exists());
        actions.andExpect(jsonPath("$[0].department.name").exists());
        actions.andExpect(jsonPath("$[0].skills").isArray());
    }

    /**
     * Test GET /employees returns 4 employees as specified in the handbook.
     */
    @Test
    public void testGetAllEmployeesCount() throws Exception {
        ResultActions actions = mvc.perform(get("/employees"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$[0].name").value("Alice Johnson"));
        actions.andExpect(jsonPath("$[1].name").value("Bob Smith"));
        actions.andExpect(jsonPath("$[2].name").value("Carol Williams"));
        actions.andExpect(jsonPath("$[3].name").value("David Brown"));
    }

    // =====================================================================
    // Handbook 3 – Department tests
    // =====================================================================

    /**
     * Test GET /departments returns a JSON array of all departments.
     *
     * "Also verify if department REST service is called by looking into the logs."
     * → DepartmentController, DepartmentService, DepartmentDao logs should appear.
     */
    @Test
    public void testGetAllDepartments() throws Exception {
        ResultActions actions = mvc.perform(get("/departments"));
        actions.andExpect(status().isOk());
        actions.andExpect(jsonPath("$").isArray());
        actions.andExpect(jsonPath("$[0].id").exists());
        actions.andExpect(jsonPath("$[0].name").exists());
        actions.andExpect(jsonPath("$[0].name").value("Information Technology"));
        actions.andExpect(jsonPath("$[1].name").value("Human Resources"));
        actions.andExpect(jsonPath("$[2].name").value("Finance"));
    }
}

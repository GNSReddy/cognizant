package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.EmployeeService;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Employee CRUD operations.
 *
 * <p>URL base: /employees (following REST naming conventions from Handbook 4).
 * GET    /employees        -> getAllEmployees()
 * PUT    /employees        -> updateEmployee()
 * DELETE /employees/{id}   -> deleteEmployee()
 * </p>
 */
@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    public EmployeeController() {
        LOGGER.debug("Inside EmployeeController Constructor.");
    }

    /**
     * Returns the complete list of all employees.
     * Spring's Jackson library converts the List<Employee> to a JSON array.
     *
     * URL: GET /employees
     *
     * @return JSON array of all employees
     */
    @GetMapping
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.debug("getAllEmployees() returning {} employees", employees.size());
        LOGGER.info("End");
        return employees;
    }

    /**
     * Updates an existing employee's details.
     * @Valid ensures the Employee bean is validated before processing.
     * GlobalExceptionHandler handles any validation failures.
     *
     * URL: PUT /employees
     * Content-Type: application/json
     *
     * @param employee Updated employee data from JSON request body
     * @throws EmployeeNotFoundException if the employee ID is not found
     */
    @PutMapping
    public void updateEmployee(@RequestBody @Valid Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("updateEmployee() received employee: {}", employee.getName());
        employeeService.updateEmployee(employee);
        LOGGER.info("End");
    }

    /**
     * Deletes an employee by their ID.
     *
     * URL: DELETE /employees/{id}
     *
     * @param id Employee ID to delete
     * @throws EmployeeNotFoundException if no employee with the given ID exists
     */
    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id) throws EmployeeNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("deleteEmployee() called with id: {}", id);
        employeeService.deleteEmployee(id);
        LOGGER.debug("Employee with id {} deleted successfully.", id);
        LOGGER.info("End");
    }
}

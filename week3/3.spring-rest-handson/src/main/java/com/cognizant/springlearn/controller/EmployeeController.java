package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Employee resources.
 *
 * Handbook 3 – "In EmployeeController, incorporate the following:
 *   Include a new get method with name getAllEmployees() that returns the employee list
 *   Mark this method as GetMapping annotation with the URL as '/employees'
 *   Within this method invoke employeeService.getAllEmployees() and return the same."
 *
 * @RequestMapping("/employees") sets the base URL for all methods in this controller.
 * This follows REST naming conventions (plural resource name).
 *
 * Test with Postman:
 *   GET http://localhost:8083/employees
 *   Expected: JSON array of 4 employees, each with nested department and skills
 *
 * Also verify in logs that DepartmentDao/EmployeeDao constructors fired at startup,
 * showing "Loaded N employees/departments from employee.xml"
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
     * Handbook 3: GET /employees – returns all employees as a JSON array.
     *
     * How Jackson serialises Employee to JSON:
     *  1. Calls getId()      → "id": 1
     *  2. Calls getName()    → "name": "Alice Johnson"
     *  3. Calls getSalary()  → "salary": 75000.0
     *  4. Calls getDepartment() → nested object {"id":1,"name":"Information Technology"}
     *  5. Calls getSkills()  → array [{"id":1,"name":"Java"},...]
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
}

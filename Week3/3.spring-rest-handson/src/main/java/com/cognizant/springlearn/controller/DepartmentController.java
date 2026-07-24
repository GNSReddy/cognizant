package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Department;
import com.cognizant.springlearn.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Department resources.
 *
 * Handbook 3 – "Create a new REST Service, define below list of classes:
 *   DepartmentController – getAllDepartments() with URL '/departments',
 *   this method will return array of departments"
 *
 * Test with Postman:
 *   GET http://localhost:8083/departments
 *   Expected:
 *   [
 *     {"id":1,"name":"Information Technology"},
 *     {"id":2,"name":"Human Resources"},
 *     {"id":3,"name":"Finance"}
 *   ]
 *
 * "Also verify if department REST service is called by looking into the logs."
 * → Check the console for DepartmentController and DepartmentService log lines.
 */
@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    public DepartmentController() {
        LOGGER.debug("Inside DepartmentController Constructor.");
    }

    /**
     * Handbook 3: GET /departments – returns all departments as a JSON array.
     *
     * @return JSON array of all departments
     */
    @GetMapping
    public List<Department> getAllDepartments() {
        LOGGER.info("Start");
        List<Department> departments = departmentService.getAllDepartments();
        LOGGER.debug("getAllDepartments() returning {} departments", departments.size());
        LOGGER.info("End");
        return departments;
    }
}

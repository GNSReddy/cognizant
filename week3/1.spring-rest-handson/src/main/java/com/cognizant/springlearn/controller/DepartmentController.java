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
 * REST Controller for Department operations.
 *
 * <p>URL: GET /departments -> getAllDepartments()
 * Returns JSON array of all departments loaded from Spring XML config.</p>
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
     * Returns all departments.
     * Jackson automatically converts List<Department> to a JSON array.
     *
     * URL: GET /departments
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

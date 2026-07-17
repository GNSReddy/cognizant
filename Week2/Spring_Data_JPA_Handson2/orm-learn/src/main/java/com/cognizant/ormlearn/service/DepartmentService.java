package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.repository.DepartmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * DepartmentService - Service Layer for Department operations.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 5
 * Topic: Implement One-to-Many Relationship
 *
 * Demonstrates @OneToMany LAZY loading:
 *   - Department is fetched first (without employees)
 *   - Inside the same @Transactional method, calling getEmployees()
 *     triggers Hibernate to fire a second SELECT to load the employees.
 *   - Both SELECTs happen within the same open Hibernate session.
 *
 * Why @Transactional is critical for LAZY loading:
 *   - Hibernate session stays open for the entire @Transactional method.
 *   - When getEmployees() is called, Hibernate uses this open session
 *     to execute: SELECT * FROM employee WHERE dept_id = ?
 *   - If @Transactional were absent, the session would close after
 *     findById(), and getEmployees() would throw LazyInitializationException.
 */
@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;

    /**
     * Get a Department by its ID, with its employee list fully loaded.
     *
     * @Transactional keeps the Hibernate session open for both queries:
     *
     *   Query 1 — Load the Department:
     *     SELECT dept_id, dept_name FROM department WHERE dept_id = ?
     *
     *   Query 2 — Triggered when getEmployees().size() is called:
     *     SELECT emp_id, emp_name, emp_salary, emp_permanent,
     *            emp_date_of_joining, dept_id
     *     FROM employee WHERE dept_id = ?
     *
     * Calling department.getEmployees().size() inside the transaction
     * forces Hibernate to initialize the lazy collection BEFORE the
     * session closes. This prevents LazyInitializationException
     * when the caller accesses the employees list after return.
     *
     * @param departmentId the dept_id to look up
     * @return Department with its employees list fully initialized
     */
    @Transactional
    public Department getDepartmentById(int departmentId) {
        LOGGER.debug("Inside getDepartmentById, departmentId={}", departmentId);

        // Query 1: Load Department row (employees NOT loaded yet — LAZY)
        Optional<Department> result = departmentRepository.findById(departmentId);
        Department department = result.get();
        LOGGER.debug("Department loaded: {}", department.getName());

        // Trigger LAZY loading of employees while session is still open.
        // Calling size() causes Hibernate to execute the second SELECT:
        //   SELECT * FROM employee WHERE dept_id = ?
        // This initializes the employees collection in memory.
        LOGGER.debug("Loading employees for department (LAZY fetch)...");
        department.getEmployees().size();
        LOGGER.debug("Employees loaded: count={}", department.getEmployees().size());

        return department;
    }

}

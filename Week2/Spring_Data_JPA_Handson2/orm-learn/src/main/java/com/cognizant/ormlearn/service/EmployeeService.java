package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeService - Service Layer for Employee operations.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 4, 6
 *
 * Methods:
 *   getAllEmployees()    - Hands-on 4: load all employees with Department (EAGER)
 *   getEmployeeById()   - Hands-on 6: load single employee with Skills (LAZY init)
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // ── Hands-on 4: Get All Employees ────────────────────────────────────

    /**
     * Get all employees — each with Department loaded (EAGER via @ManyToOne).
     *
     * SQL: SELECT e.*, d.* FROM employee e
     *      INNER JOIN department d ON e.dept_id = d.dept_id
     *
     * @return List of all employees with their Department populated
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        LOGGER.debug("Inside getAllEmployees");
        return employeeRepository.findAll();
    }

    // ── Hands-on 6: Get Single Employee With Skills ───────────────────────

    /**
     * Get a single Employee by ID, with their Skills list fully initialized.
     *
     * @Transactional keeps the Hibernate session open for BOTH queries:
     *
     *   Query 1 — Load Employee + Department (EAGER join):
     *     SELECT e.emp_id, e.emp_name, e.emp_salary, e.emp_permanent,
     *            e.emp_date_of_joining, d.dept_id, d.dept_name
     *     FROM employee e
     *     INNER JOIN department d ON e.dept_id = d.dept_id
     *     WHERE e.emp_id = ?
     *
     *   Query 2 — Triggered by employee.getSkills().size() (LAZY):
     *     SELECT s.skill_id, s.skill_name
     *     FROM skill s
     *     INNER JOIN employee_skill es ON s.skill_id = es.skill_id
     *     WHERE es.emp_id = ?
     *
     * Calling getSkills().size() inside @Transactional forces Hibernate
     * to initialize the lazy skills collection before the session closes.
     * This prevents LazyInitializationException in the caller.
     *
     * @param id the emp_id to look up
     * @return Employee with Department (EAGER) and Skills (LAZY initialized)
     */
    @Transactional
    public Employee getEmployeeById(int id) {
        LOGGER.debug("Inside getEmployeeById, id={}", id);

        // Query 1: Load Employee + Department in one JOIN
        Optional<Employee> result = employeeRepository.findById(id);
        Employee employee = result.get();
        LOGGER.debug("Employee loaded: {}", employee.getName());

        // Query 2: Trigger LAZY loading of skills while session is still open
        // Without this, getSkills() in main() would throw LazyInitializationException
        LOGGER.debug("Loading skills for employee (LAZY fetch)...");
        employee.getSkills().size();
        LOGGER.debug("Skills loaded: count={}", employee.getSkills().size());

        return employee;
    }

}

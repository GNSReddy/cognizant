package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EmployeeService - Service Layer for Employee @Query operations.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 2
 * Topic: @Query annotation on Employee / Department tables
 *
 * Each method delegates to a @Query-annotated method in EmployeeRepository.
 * Spring Data JPA executes the JPQL or native SQL specified in @Query.
 *
 * @Transactional:
 *   Opens a Hibernate session for the duration of each service method.
 *   Required because @ManyToOne(EAGER) needs an active session to
 *   load the Department object when Employee rows are returned.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Get all employees in a specific department by department name.
     *
     * Delegates to: @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
     * SQL: SELECT e.*, d.* FROM employee e JOIN department d ON e.dept_id = d.dept_id
     *      WHERE d.dept_name = ?
     *
     * @param deptName department name e.g. "IT", "Finance"
     * @return List of employees in that department
     */
    @Transactional
    public List<Employee> getEmployeesByDepartment(String deptName) {
        LOGGER.debug("Inside getEmployeesByDepartment, deptName={}", deptName);
        return employeeRepository.findEmployeesByDepartmentName(deptName);
    }

    /**
     * Get employees earning above a salary threshold.
     *
     * Delegates to: @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
     * SQL: SELECT ... FROM employee WHERE emp_salary > ?
     *
     * @param salary minimum salary threshold
     * @return List of employees with salary > threshold
     */
    @Transactional
    public List<Employee> getEmployeesAboveSalary(double salary) {
        LOGGER.debug("Inside getEmployeesAboveSalary, salary={}", salary);
        return employeeRepository.findEmployeesBySalaryGreaterThan(salary);
    }

    /**
     * Get only permanent employees.
     *
     * Delegates to: @Query("SELECT e FROM Employee e WHERE e.permanent = true")
     * SQL: SELECT ... FROM employee WHERE emp_permanent = 1
     *
     * @return List of permanent employees
     */
    @Transactional
    public List<Employee> getPermanentEmployees() {
        LOGGER.debug("Inside getPermanentEmployees");
        return employeeRepository.findPermanentEmployees();
    }

    /**
     * Get all employees sorted by salary highest first.
     *
     * Delegates to: @Query("SELECT e FROM Employee e ORDER BY e.salary DESC")
     * SQL: SELECT ... FROM employee ORDER BY emp_salary DESC
     *
     * @return List of employees sorted by salary descending
     */
    @Transactional
    public List<Employee> getEmployeesSortedBySalary() {
        LOGGER.debug("Inside getEmployeesSortedBySalary");
        return employeeRepository.findAllEmployeesOrderedBySalaryDesc();
    }

    /**
     * Count employees in a department by name.
     *
     * Delegates to: @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.name = :deptName")
     * SQL: SELECT COUNT(*) FROM employee JOIN department WHERE dept_name = ?
     *
     * @param deptName department name to count for
     * @return count of employees in the given department
     */
    @Transactional
    public long countByDepartment(String deptName) {
        LOGGER.debug("Inside countByDepartment, deptName={}", deptName);
        return employeeRepository.countEmployeesByDepartmentName(deptName);
    }

    /**
     * Search employees by name keyword using JPQL LIKE.
     *
     * Delegates to: @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword%")
     * SQL: SELECT ... FROM employee WHERE emp_name LIKE '%keyword%'
     *
     * @param keyword substring to search in employee names
     * @return List of employees whose name contains the keyword
     */
    @Transactional
    public List<Employee> searchByName(String keyword) {
        LOGGER.debug("Inside searchByName, keyword={}", keyword);
        return employeeRepository.findEmployeesByNameKeyword(keyword);
    }

    /**
     * Get employees in a department using native SQL query.
     *
     * Delegates to: @Query(nativeQuery=true) raw SQL with actual table/column names.
     *
     * @param deptName department name (actual DB value)
     * @return List of employees via native SQL
     */
    @Transactional
    public List<Employee> getEmployeesByDeptNative(String deptName) {
        LOGGER.debug("Inside getEmployeesByDeptNative, deptName={}", deptName);
        return employeeRepository.findEmployeesByDeptNameNative(deptName);
    }

}

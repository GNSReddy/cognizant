package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * EmployeeService - Service Layer for Employee operations.
 *
 * Handbook: Spring Data JPA Hands-on 3
 *   Hands-on 2: @Query JPQL methods
 *   Hands-on 3: Pagination using PageRequest ← ADDED NOW
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // ── Hands-on 2: @Query methods (carried forward) ─────────────────────
    @Transactional
    public List<Employee> getEmployeesByDepartment(String deptName) {
        return employeeRepository.findEmployeesByDepartmentName(deptName);
    }

    @Transactional
    public List<Employee> getEmployeesAboveSalary(double salary) {
        return employeeRepository.findEmployeesBySalaryGreaterThan(salary);
    }

    @Transactional
    public List<Employee> getPermanentEmployees() {
        return employeeRepository.findPermanentEmployees();
    }

    @Transactional
    public List<Employee> getEmployeesSortedBySalary() {
        return employeeRepository.findAllEmployeesOrderedBySalaryDesc();
    }

    @Transactional
    public long countByDepartment(String deptName) {
        return employeeRepository.countEmployeesByDepartmentName(deptName);
    }

    @Transactional
    public List<Employee> searchByName(String keyword) {
        return employeeRepository.findEmployeesByNameKeyword(keyword);
    }

    @Transactional
    public List<Employee> getEmployeesByDeptNative(String deptName) {
        return employeeRepository.findEmployeesByDeptNameNative(deptName);
    }

    // ── Hands-on 3: Pagination ────────────────────────────────────────────

    /**
     * Get all employees in a paginated fashion.
     *
     * PageRequest.of(pageNumber, pageSize):
     *   page 0 → OFFSET 0,  LIMIT pageSize
     *   page 1 → OFFSET pageSize, LIMIT pageSize
     *   page 2 → OFFSET pageSize*2, LIMIT pageSize
     *
     * With only 8 employees in the DB:
     *   PageRequest.of(0, 3) → page 1: [Alice, Bob, Carol]
     *   PageRequest.of(1, 3) → page 2: [David, Eve, Frank]
     *   PageRequest.of(2, 3) → page 3: [Grace, Henry]
     *   PageRequest.of(3, 3) → page 4: [] (empty)
     *
     * @param pageNumber 0-indexed page number
     * @param pageSize   number of employees per page
     * @return Page<Employee> with page data + metadata
     */
    @Transactional
    public Page<Employee> getEmployeesByPage(int pageNumber, int pageSize) {
        LOGGER.debug("Inside getEmployeesByPage, page={}, size={}", pageNumber, pageSize);
        return employeeRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    /**
     * Get permanent employees only — paginated.
     *
     * SQL: SELECT ... FROM employee WHERE emp_permanent=1 LIMIT ? OFFSET ?
     *
     * @param pageNumber 0-indexed page number
     * @param pageSize   records per page
     * @return Page<Employee> of only permanent employees
     */
    @Transactional
    public Page<Employee> getPermanentEmployeesByPage(int pageNumber, int pageSize) {
        LOGGER.debug("Inside getPermanentEmployeesByPage, page={}, size={}", pageNumber, pageSize);
        return employeeRepository.findPermanentEmployeesPageable(PageRequest.of(pageNumber, pageSize));
    }

}

package com.cognizant.springlearn.service;

import com.cognizant.springlearn.dao.EmployeeDao;
import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Employee business logic.
 *
 * <p>Delegates persistence to EmployeeDao. @Transactional ensures
 * data consistency in transactional operations.</p>
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Retrieves all employees.
     *
     * @return List of all employees
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start");
        List<Employee> employees = employeeDao.getAllEmployees();
        LOGGER.debug("getAllEmployees() returned {} employees", employees.size());
        LOGGER.info("End");
        return employees;
    }

    /**
     * Updates an existing employee's details.
     *
     * @param employee Employee data to update
     * @throws EmployeeNotFoundException if the employee ID does not exist
     */
    @Transactional
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Updating employee: {}", employee);
        employeeDao.updateEmployee(employee);
        LOGGER.info("End");
    }

    /**
     * Deletes an employee by their ID.
     *
     * @param id Employee ID
     * @throws EmployeeNotFoundException if no employee with the given ID exists
     */
    @Transactional
    public void deleteEmployee(Integer id) throws EmployeeNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Deleting employee with id: {}", id);
        employeeDao.deleteEmployee(id);
        LOGGER.info("End");
    }
}

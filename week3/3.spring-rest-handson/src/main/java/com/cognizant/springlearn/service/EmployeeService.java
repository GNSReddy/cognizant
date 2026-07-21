package com.cognizant.springlearn.service;

import com.cognizant.springlearn.dao.EmployeeDao;
import com.cognizant.springlearn.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service layer for Employee business logic.
 *
 * Handbook 3 – "In EmployeeService, incorporate the following:
 *   Change the annotation for this class from @Component to @Service
 *   Create method getAllEmployees() that invokes employeeDao.getAllEmployees()
 *   Define @Transactional annotation for this method."
 *
 * @Service (specialisation of @Component) communicates that this class holds
 * business/service logic and is part of the service layer in the three-tier
 * architecture: Controller → Service → DAO.
 *
 * @Transactional ensures that getAllEmployees() participates in a transaction.
 * For read-only operations on an in-memory list this has no practical effect,
 * but it establishes the correct pattern for when a real database is added.
 */
@Service
public class EmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeService.class);

    @Autowired
    private EmployeeDao employeeDao;

    /**
     * Handbook 3: getAllEmployees() with @Transactional.
     * Delegates directly to EmployeeDao and returns the full employee list.
     *
     * @return List of all employees loaded from employee.xml
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start");
        List<Employee> employees = employeeDao.getAllEmployees();
        LOGGER.debug("getAllEmployees() – returned {} employees", employees.size());
        LOGGER.info("End");
        return employees;
    }
}

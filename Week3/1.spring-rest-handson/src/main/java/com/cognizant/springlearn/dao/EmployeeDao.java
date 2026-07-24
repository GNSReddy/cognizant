package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.model.Employee;
import com.cognizant.springlearn.service.exception.EmployeeNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Employee data.
 *
 * <p>Loads employee list from employee.xml at construction time
 * and stores it in a static list for in-memory CRUD operations.</p>
 *
 * <p>Static EMPLOYEE_LIST acts as an in-memory database for the hands-on exercises.</p>
 */
@Repository
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    /** In-memory employee list, populated from employee.xml at startup. */
    private static List<Employee> EMPLOYEE_LIST;

    /**
     * Constructor loads the employee list from Spring XML configuration.
     * Called once by Spring when creating the EmployeeDao bean.
     */
    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        LOGGER.info("Start - EmployeeDao Constructor, loading employee list from employee.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        EMPLOYEE_LIST = (ArrayList<Employee>) context.getBean("employeeList", ArrayList.class);
        LOGGER.debug("Loaded {} employees from XML configuration.", EMPLOYEE_LIST.size());
        LOGGER.info("End");
    }

    /**
     * Returns all employees from the static in-memory list.
     *
     * @return List of all employees
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start");
        LOGGER.debug("Total employees: {}", EMPLOYEE_LIST.size());
        LOGGER.info("End");
        return EMPLOYEE_LIST;
    }

    /**
     * Updates an existing employee in the in-memory list.
     * Matches by employee ID and replaces the entry.
     *
     * @param employee Employee with updated details
     * @throws EmployeeNotFoundException if no employee exists with the given ID
     */
    public void updateEmployee(Employee employee) throws EmployeeNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Updating employee with id: {}", employee.getId());
        for (int i = 0; i < EMPLOYEE_LIST.size(); i++) {
            if (EMPLOYEE_LIST.get(i).getId().equals(employee.getId())) {
                EMPLOYEE_LIST.set(i, employee);
                LOGGER.debug("Employee updated successfully: {}", employee.getName());
                LOGGER.info("End");
                return;
            }
        }
        LOGGER.warn("Employee with id {} not found.", employee.getId());
        throw new EmployeeNotFoundException("Employee with id " + employee.getId() + " not found");
    }

    /**
     * Deletes an employee from the in-memory list by ID.
     *
     * @param id Employee ID to delete
     * @throws EmployeeNotFoundException if no employee exists with the given ID
     */
    public void deleteEmployee(Integer id) throws EmployeeNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Deleting employee with id: {}", id);
        boolean removed = EMPLOYEE_LIST.removeIf(emp -> emp.getId().equals(id));
        if (!removed) {
            LOGGER.warn("Employee with id {} not found for deletion.", id);
            throw new EmployeeNotFoundException("Employee with id " + id + " not found");
        }
        LOGGER.debug("Employee with id {} deleted successfully.", id);
        LOGGER.info("End");
    }
}

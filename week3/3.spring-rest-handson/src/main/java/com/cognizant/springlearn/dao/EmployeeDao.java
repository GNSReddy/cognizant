package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.model.Employee;
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
 * Handbook 3 – "Create static employee list data using spring xml configuration"
 *
 * Design:
 *   EMPLOYEE_LIST is static so it persists across requests (acts as in-memory DB).
 *   The constructor loads employee.xml ONCE when Spring creates the EmployeeDao bean.
 *   getAllEmployees() returns the cached list without re-reading the XML every call.
 *
 * Why static?
 *   EmployeeDao is a singleton Spring bean (@Repository → @Component).
 *   A static field ensures the list is shared and consistent across the application.
 *   Future CRUD operations (add/update/delete) will modify this same list.
 */
@Repository
public class EmployeeDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeDao.class);

    /**
     * Handbook 3: "Create static variable with name EMPLOYEE_LIST of type ArrayList<Employee>"
     * Populated once from employee.xml in the constructor.
     */
    private static List<Employee> EMPLOYEE_LIST;

    /**
     * Handbook 3: "Include constructor that reads employee list from xml config
     *              and set the EMPLOYEE_LIST"
     *
     * ClassPathXmlApplicationContext("employee.xml") bootstraps a Spring container
     * from employee.xml found on the classpath (src/main/resources).
     * context.getBean("employeeList") retrieves the ArrayList<Employee> bean.
     */
    @SuppressWarnings("unchecked")
    public EmployeeDao() {
        LOGGER.info("Start – EmployeeDao Constructor loading employee.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        EMPLOYEE_LIST = (ArrayList<Employee>) context.getBean("employeeList", ArrayList.class);
        LOGGER.debug("Loaded {} employees from employee.xml", EMPLOYEE_LIST.size());
        LOGGER.info("End");
    }

    /**
     * Handbook 3: "Create method getAllEmployees() that returns the EMPLOYEE_LIST"
     *
     * @return the static in-memory list of all employees
     */
    public List<Employee> getAllEmployees() {
        LOGGER.info("Start");
        LOGGER.debug("Returning {} employees from EMPLOYEE_LIST", EMPLOYEE_LIST.size());
        LOGGER.info("End");
        return EMPLOYEE_LIST;
    }
}

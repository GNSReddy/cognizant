package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Department data.
 *
 * Handbook 3 – "Create REST service for department"
 * "DepartmentDao – getAllDepartments()
 *  Create a static variable DEPARTMENT_LIST,
 *  this should be populated from spring xml configuration"
 *
 * Mirrors the same pattern as EmployeeDao:
 *   Static DEPARTMENT_LIST populated from employee.xml in constructor.
 *   employee.xml defines the "departmentList" ArrayList<Department> bean.
 */
@Repository
public class DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDao.class);

    /**
     * Handbook 3: static variable populated from spring xml configuration.
     */
    private static List<Department> DEPARTMENT_LIST;

    /**
     * Constructor loads department list from employee.xml once at bean creation.
     */
    @SuppressWarnings("unchecked")
    public DepartmentDao() {
        LOGGER.info("Start – DepartmentDao Constructor loading employee.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        DEPARTMENT_LIST = (ArrayList<Department>) context.getBean("departmentList", ArrayList.class);
        LOGGER.debug("Loaded {} departments from employee.xml", DEPARTMENT_LIST.size());
        LOGGER.info("End");
    }

    /**
     * Returns all departments from the static list.
     *
     * @return list of all Department objects
     */
    public List<Department> getAllDepartments() {
        LOGGER.info("Start");
        LOGGER.debug("Returning {} departments from DEPARTMENT_LIST", DEPARTMENT_LIST.size());
        LOGGER.info("End");
        return DEPARTMENT_LIST;
    }
}

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
 * <p>Populates a static DEPARTMENT_LIST from Spring XML configuration (employee.xml)
 * at construction time.</p>
 */
@Repository
public class DepartmentDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentDao.class);

    /** In-memory department list populated from employee.xml. */
    private static List<Department> DEPARTMENT_LIST;

    /**
     * Constructor loads department list from Spring XML configuration.
     */
    @SuppressWarnings("unchecked")
    public DepartmentDao() {
        LOGGER.info("Start - DepartmentDao Constructor, loading department list from employee.xml");
        ApplicationContext context = new ClassPathXmlApplicationContext("employee.xml");
        DEPARTMENT_LIST = (ArrayList<Department>) context.getBean("departmentList", ArrayList.class);
        LOGGER.debug("Loaded {} departments from XML configuration.", DEPARTMENT_LIST.size());
        LOGGER.info("End");
    }

    /**
     * Returns all departments from the static in-memory list.
     *
     * @return List of all departments
     */
    public List<Department> getAllDepartments() {
        LOGGER.info("Start");
        LOGGER.debug("Total departments: {}", DEPARTMENT_LIST.size());
        LOGGER.info("End");
        return DEPARTMENT_LIST;
    }
}

package com.cognizant.springlearn.service;

import com.cognizant.springlearn.dao.DepartmentDao;
import com.cognizant.springlearn.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Department business logic.
 * Delegates data access to DepartmentDao.
 */
@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * Returns all departments.
     *
     * @return List of all departments
     */
    public List<Department> getAllDepartments() {
        LOGGER.info("Start");
        List<Department> departments = departmentDao.getAllDepartments();
        LOGGER.debug("getAllDepartments() returned {} departments", departments.size());
        LOGGER.info("End");
        return departments;
    }
}

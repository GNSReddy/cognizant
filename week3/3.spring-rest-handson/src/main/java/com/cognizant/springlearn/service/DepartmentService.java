package com.cognizant.springlearn.service;

import com.cognizant.springlearn.dao.DepartmentDao;
import com.cognizant.springlearn.model.Department;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Department.
 *
 * Handbook 3 – "Create a new REST Service, define below list of classes:
 *   DepartmentService – getAllDepartments()"
 *
 * Delegates data retrieval to DepartmentDao.
 */
@Service
public class DepartmentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentDao departmentDao;

    /**
     * Returns all departments.
     *
     * @return list of all Department objects from DEPARTMENT_LIST
     */
    public List<Department> getAllDepartments() {
        LOGGER.info("Start");
        List<Department> departments = departmentDao.getAllDepartments();
        LOGGER.debug("getAllDepartments() – returned {} departments", departments.size());
        LOGGER.info("End");
        return departments;
    }
}

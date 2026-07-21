package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Department model – represents a company department.
 *
 * Handbook 3: "Create one or two more departments" using Spring XML beans.
 * Injected into Employee via <property name="department" ref="deptIT"/>.
 *
 * Jackson serialises this nested object inside Employee JSON:
 * "department": {"id":1,"name":"Information Technology"}
 */
public class Department {

    private static final Logger LOGGER = LoggerFactory.getLogger(Department.class);

    private Integer id;
    private String name;

    public Department() {
        LOGGER.debug("Inside Department Constructor.");
    }

    public Integer getId() {
        LOGGER.debug("Inside Department getId() - id: {}", id);
        return id;
    }

    public void setId(Integer id) {
        LOGGER.debug("Inside Department setId() - id: {}", id);
        this.id = id;
    }

    public String getName() {
        LOGGER.debug("Inside Department getName() - name: {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside Department setName() - name: {}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }
}

package com.cognizant.springlearn.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Department model representing a company department.
 */
public class Department {

    private static final Logger LOGGER = LoggerFactory.getLogger(Department.class);

    @NotNull
    @Min(1)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 30)
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

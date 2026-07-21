package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Department model with Bean Validation (Handbook 4).
 */
public class Department {

    private static final Logger LOGGER = LoggerFactory.getLogger(Department.class);

    @NotNull(message = "Department id must not be null")
    @Min(value = 1, message = "Department id must be greater than or equal to 1")
    private Integer id;

    @NotBlank(message = "Department name must not be blank")
    @Size(min = 1, max = 50, message = "Department name must be between 1 and 50 characters")
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

package com.cognizant.springlearn.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Skill model representing an employee skill (e.g. Java, Spring, Angular).
 */
public class Skill {

    private static final Logger LOGGER = LoggerFactory.getLogger(Skill.class);

    @NotNull
    @Min(1)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    public Skill() {
        LOGGER.debug("Inside Skill Constructor.");
    }

    public Integer getId() {
        LOGGER.debug("Inside Skill getId() - id: {}", id);
        return id;
    }

    public void setId(Integer id) {
        LOGGER.debug("Inside Skill setId() - id: {}", id);
        this.id = id;
    }

    public String getName() {
        LOGGER.debug("Inside Skill getName() - name: {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside Skill setName() - name: {}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Skill{id=" + id + ", name='" + name + "'}";
    }
}

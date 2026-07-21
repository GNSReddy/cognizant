package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Skill model with Bean Validation (Handbook 4).
 *
 * Validation applies when Skill is part of an Employee body in POST/PUT requests.
 * @Valid on the Employee parameter triggers cascaded validation on nested objects
 * if @Valid is also placed on the skills field — not added here to match handbook scope.
 */
public class Skill {

    private static final Logger LOGGER = LoggerFactory.getLogger(Skill.class);

    private Integer id;

    @NotBlank(message = "Skill name must not be blank")
    @Size(min = 1, max = 30, message = "Skill name must be between 1 and 30 characters")
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

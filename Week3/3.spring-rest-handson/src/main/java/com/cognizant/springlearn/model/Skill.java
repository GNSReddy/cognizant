package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Skill model – represents a technical or functional skill an employee possesses.
 *
 * Handbook 3: Defined as Spring beans in employee.xml and referenced by Employee
 * beans via <ref bean="..."/>. Reusing skill bean instances avoids duplication
 * (e.g. multiple employees can share the same "skillJava" bean reference).
 *
 * Jackson serialises this to: {"id":1,"name":"Java"}
 */
public class Skill {

    private static final Logger LOGGER = LoggerFactory.getLogger(Skill.class);

    private Integer id;
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

package com.cognizant.springlearn.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Country model bean representing a country with ISO code and name.
 * Used by Hands-on 4, 5, 6 (Handbook 1) and REST services (Handbook 2, 4).
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    /** ISO 2-character country code. Must be exactly 2 characters (Handbook 4 validation). */
    @NotNull
    @Size(min = 2, max = 2, message = "Country code should be 2 characters")
    private String code;

    private String name;

    /**
     * Default constructor with debug log to demonstrate Spring bean instantiation.
     * Singleton scope: called once. Prototype scope: called per getBean().
     */
    public Country() {
        LOGGER.debug("Inside Country Constructor.");
    }

    public String getCode() {
        LOGGER.debug("Inside Country getCode() - code: {}", code);
        return code;
    }

    public void setCode(String code) {
        LOGGER.debug("Inside Country setCode() - code: {}", code);
        this.code = code;
    }

    public String getName() {
        LOGGER.debug("Inside Country getName() - name: {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside Country setName() - name: {}", name);
        this.name = name;
    }

    @Override
    public String toString() {
        return "Country{code='" + code + "', name='" + name + "'}";
    }
}

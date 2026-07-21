package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Country model – carried forward from Handbooks 1 & 2.
 * Used by CountryController REST service and loaded from country.xml.
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    private String code;
    private String name;

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

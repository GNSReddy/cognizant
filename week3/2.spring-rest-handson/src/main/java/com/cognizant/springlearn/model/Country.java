package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Country model bean.
 *
 * Hands-on 4 (Handbook 1): Loaded from country.xml via Spring ApplicationContext.
 * Hands-on 5: Demonstrates singleton vs prototype scope (constructor log count).
 * Handbook 2 REST: Returned by CountryController – Jackson serialises it to JSON.
 *
 * Spring XML property injection calls setCode() and setName() after the constructor.
 * Debug logs inside each method show exactly when Spring invokes them.
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    private String code;
    private String name;

    /**
     * Default (no-arg) constructor required by Spring for bean instantiation.
     * Log here reveals: singleton → logged ONCE; prototype → logged per getBean().
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

package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Country model with Bean Validation annotations (Handbook 4).
 *
 * Handbook 4 – "Add validation to the Country class"
 * Validation annotations from javax.validation (JSR-303 / JSR-380):
 *
 *   @NotNull   – field value must not be null
 *   @Size      – string length must be within min/max bounds
 *   @NotBlank  – string must not be null, empty, or whitespace-only
 *
 * These annotations are checked by Hibernate Validator (the implementation)
 * when @Valid is placed on the @RequestBody parameter in the controller.
 *
 * If validation fails, Spring throws MethodArgumentNotValidException,
 * which is caught by GlobalExceptionHandler and returned as HTTP 400.
 *
 * Example invalid request → HTTP 400:
 *   {"code":"I","name":""}
 *   → code must be exactly 2 chars, name must not be blank
 */
public class Country {

    private static final Logger LOGGER = LoggerFactory.getLogger(Country.class);

    /**
     * ISO 3166-1 alpha-2 country code.
     * @NotNull – must be provided in the JSON body
     * @Size(min=2, max=2) – must be exactly 2 characters (e.g. "IN", "US")
     */
    @NotNull(message = "Country code must not be null")
    @Size(min = 2, max = 2, message = "Country code must be exactly 2 characters")
    private String code;

    /**
     * Full country name.
     * @NotBlank – must not be null or whitespace
     * @Size(max=50) – reasonable upper bound
     */
    @NotBlank(message = "Country name must not be blank")
    @Size(max = 50, message = "Country name must not exceed 50 characters")
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

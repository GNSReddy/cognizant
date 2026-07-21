package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

/**
 * REST Controller for Country operations.
 *
 * <p>Handles all CRUD operations for Country resources.
 * URL base: /countries (following REST naming conventions from Handbook 4).
 * The /country endpoint (without 's') is retained for backward compatibility
 * with Handbook 2 requirements.</p>
 *
 * <p>@RequestMapping at class level with /countries covers:
 * GET  /countries        -> getAllCountries()
 * GET  /countries/{code} -> getCountry()
 * POST /countries        -> addCountry()
 * </p>
 */
@RestController
@RequestMapping("/countries")
public class CountryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryController.class);

    @Autowired
    private CountryService countryService;

    public CountryController() {
        LOGGER.debug("Inside CountryController Constructor.");
    }

    /**
     * Backward-compatible endpoint returning India country details.
     * Loads India bean directly from country.xml (as specified in Handbook 2).
     *
     * URL: GET /country
     *
     * @return Country object representing India, serialized as JSON
     */
    @GetMapping("/india")
    public Country getCountryIndia() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("in", Country.class);
        LOGGER.debug("getCountryIndia() returning: {}", country.toString());
        LOGGER.info("End");
        return country;
    }

    /**
     * Returns all countries.
     * Spring automatically converts the List to a JSON array via Jackson.
     *
     * URL: GET /countries
     *
     * @return JSON array of all countries
     */
    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("getAllCountries() returning {} countries", countries.size());
        LOGGER.info("End");
        return countries;
    }

    /**
     * Returns a specific country by its ISO code (case-insensitive).
     * Uses @PathVariable to extract {code} from the URL path.
     *
     * URL: GET /countries/{code}
     *
     * @param code ISO country code (e.g., "IN", "us")
     * @return Matching Country object as JSON
     * @throws CountryNotFoundException if the code doesn't match any country
     */
    @GetMapping("/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("getCountry() called with code: {}", code);
        Country country = countryService.getCountry(code);
        LOGGER.debug("getCountry() returning: {}", country.toString());
        LOGGER.info("End");
        return country;
    }

    /**
     * Creates/adds a new country based on JSON request body.
     * @Valid triggers Spring to validate the Country bean against
     * its constraint annotations before entering this method.
     * Validation errors are handled by GlobalExceptionHandler.
     *
     * URL: POST /countries
     * Content-Type: application/json
     *
     * @param country Country data from the JSON request body
     * @return The country as received (for verification)
     */
    @PostMapping
    public Country addCountry(@RequestBody @Valid Country country) {
        LOGGER.info("Start");
        LOGGER.debug("addCountry() received: code={}, name={}", country.getCode(), country.getName());
        // Future: persist to database. For now, return echoed data.
        LOGGER.info("End");
        return country;
    }
}

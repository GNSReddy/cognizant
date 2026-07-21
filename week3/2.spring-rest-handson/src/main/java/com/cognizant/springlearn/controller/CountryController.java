package com.cognizant.springlearn.controller;

import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.CountryService;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Country resources.
 *
 * Handbook 2 exercises covered here:
 *
 * 1. "REST – Country Web Service"
 *    GET /country  → getCountryIndia()
 *    Loads "in" bean directly from country.xml and returns it as JSON.
 *    Jackson automatically converts Country → {"code":"IN","name":"India"}
 *
 * 2. "REST – Get all countries"
 *    GET /countries  → getAllCountries()
 *    Returns the full list as a JSON array.
 *
 * 3. "REST – Get country based on country code"
 *    GET /countries/{code}  → getCountry(code)
 *    Uses @PathVariable to extract the code from the URL.
 *    Delegates to CountryService.getCountry() for case-insensitive lookup.
 *
 * 4. "REST – Get country exceptional scenario"
 *    GET /countries/az  → CountryNotFoundException → HTTP 404
 *
 * @RequestMapping at class level sets the base URL for methods 2 & 3.
 * Method getCountryIndia() is on a different path (/country) for backward
 * compatibility with the handbook exercise wording.
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
     * Handbook 2 – "REST – Country Web Service"
     *
     * Loads the India bean directly from country.xml as specified in the handbook.
     * @RequestMapping maps any HTTP method to GET /country.
     *
     * How Jackson converts the bean to JSON:
     *  1. Spring's DispatcherServlet sees the return type is Country (POJO).
     *  2. Jackson's MappingJackson2HttpMessageConverter serialises it.
     *  3. For each field, Jackson calls the getter (e.g. getCode() → "code").
     *  4. Result: {"code":"IN","name":"India"}
     *
     * @return Country object for India
     */
    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("in", Country.class);
        LOGGER.debug("getCountryIndia() returning: {}", country);
        LOGGER.info("End");
        return country;
    }

    /**
     * Handbook 2 – "REST – Get all countries"
     *
     * @GetMapping maps HTTP GET /countries to this method.
     * Jackson converts List<Country> to a JSON array automatically.
     *
     * Sample response:
     * [
     *   {"code":"IN","name":"India"},
     *   {"code":"US","name":"United States"},
     *   {"code":"DE","name":"Germany"},
     *   {"code":"JP","name":"Japan"}
     * ]
     *
     * @return list of all countries
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
     * Handbook 2 – "REST – Get country based on country code"
     *
     * @PathVariable binds the {code} segment from the URL to the method parameter.
     * Example: GET /countries/in  → code = "in"
     *
     * The lookup in CountryService.getCountry() is case-insensitive, so
     * /countries/IN, /countries/in, /countries/In all work.
     *
     * Exceptional scenario (Handbook 2 – next exercise):
     * GET /countries/az → CountryNotFoundException → HTTP 404 "Country not found"
     *
     * @param code country ISO code extracted from URL path
     * @return matching Country as JSON
     * @throws CountryNotFoundException when the code does not match any country
     */
    @GetMapping("/{code}")
    public Country getCountry(@PathVariable String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("getCountry() called with code: {}", code);
        Country country = countryService.getCountry(code);
        LOGGER.debug("getCountry() returning: {}", country);
        LOGGER.info("End");
        return country;
    }
}

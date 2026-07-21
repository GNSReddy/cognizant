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
 * CountryController – carried forward from Handbook 2.
 *
 * GET /countries/country  → India bean directly from XML
 * GET /countries          → all countries
 * GET /countries/{code}   → specific country (case-insensitive)
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

    @RequestMapping("/country")
    public Country getCountryIndia() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        Country country = context.getBean("in", Country.class);
        LOGGER.debug("getCountryIndia() returning: {}", country);
        LOGGER.info("End");
        return country;
    }

    @GetMapping
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("getAllCountries() returning {} countries", countries.size());
        LOGGER.info("End");
        return countries;
    }

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

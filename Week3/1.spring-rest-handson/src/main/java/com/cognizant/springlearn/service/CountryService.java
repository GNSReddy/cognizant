package com.cognizant.springlearn.service;

import com.cognizant.springlearn.dao.CountryDao;
import com.cognizant.springlearn.model.Country;
import com.cognizant.springlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer for Country business logic.
 *
 * <p>Acts as an intermediary between CountryController and CountryDao.
 * All business rules (e.g., case-insensitive lookup) live here.</p>
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryDao countryDao;

    /**
     * Returns all countries from the data source.
     *
     * @return List of all Country objects
     */
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryDao.getAllCountries();
        LOGGER.debug("getAllCountries() returned {} countries", countries.size());
        LOGGER.info("End");
        return countries;
    }

    /**
     * Finds and returns a country by its ISO code (case-insensitive).
     *
     * @param code Country ISO code (e.g., "IN", "in", "Us")
     * @return Matching Country object
     * @throws CountryNotFoundException if no country matches the given code
     */
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Looking up country with code: {}", code);
        List<Country> countries = countryDao.getAllCountries();

        // Lambda expression for case-insensitive country code matching
        Country found = countries.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (found == null) {
            LOGGER.warn("Country not found for code: {}", code);
            throw new CountryNotFoundException("Country not found for code: " + code);
        }

        LOGGER.debug("Country found: {}", found.toString());
        LOGGER.info("End");
        return found;
    }
}

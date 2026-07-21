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
 * @Service is a specialisation of @Component. It marks this class as holding
 * business logic and separates it from the data-access layer (CountryDao).
 *
 * Business rule: getCountry() performs a case-insensitive search so that
 * /countries/IN, /countries/in, /countries/In all return the same result.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    /** Spring injects CountryDao automatically because it is a @Repository bean. */
    @Autowired
    private CountryDao countryDao;

    /**
     * Returns the complete list of countries.
     *
     * @return all Country objects loaded from country.xml
     */
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryDao.getAllCountries();
        LOGGER.debug("getAllCountries() – {} countries returned", countries.size());
        LOGGER.info("End");
        return countries;
    }

    /**
     * Finds a country by ISO code using case-insensitive matching.
     *
     * Lambda stream expression replaces an explicit for-loop:
     *   stream() – creates a stream from the list
     *   filter() – keeps only elements matching the predicate
     *   findFirst() – returns the first match as Optional
     *   orElse(null) – returns null if no match found
     *
     * @param code ISO country code (e.g. "IN", "in", "uS")
     * @return matching Country
     * @throws CountryNotFoundException when no country matches the code
     */
    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Looking up country with code: {}", code);

        List<Country> countries = countryDao.getAllCountries();

        Country found = countries.stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst()
                .orElse(null);

        if (found == null) {
            LOGGER.warn("Country not found for code: {}", code);
            throw new CountryNotFoundException("Country not found for code: " + code);
        }

        LOGGER.debug("Country found: {}", found);
        LOGGER.info("End");
        return found;
    }
}

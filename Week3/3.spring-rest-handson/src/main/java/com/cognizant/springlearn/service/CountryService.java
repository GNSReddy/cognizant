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
 * Service layer for Country – carried forward from Handbook 2.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryDao countryDao;

    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryDao.getAllCountries();
        LOGGER.debug("getAllCountries() – {} countries", countries.size());
        LOGGER.info("End");
        return countries;
    }

    public Country getCountry(String code) throws CountryNotFoundException {
        LOGGER.info("Start");
        LOGGER.debug("Looking up code: {}", code);
        Country found = countryDao.getAllCountries().stream()
                .filter(c -> c.getCode().equalsIgnoreCase(code))
                .findFirst().orElse(null);
        if (found == null) {
            LOGGER.warn("Country not found for code: {}", code);
            throw new CountryNotFoundException("Country not found for code: " + code);
        }
        LOGGER.debug("Found: {}", found);
        LOGGER.info("End");
        return found;
    }
}

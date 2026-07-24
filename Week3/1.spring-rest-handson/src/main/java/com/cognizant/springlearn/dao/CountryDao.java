package com.cognizant.springlearn.dao;

import com.cognizant.springlearn.model.Country;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for Country data.
 * Loads country data from country.xml Spring XML configuration.
 *
 * <p>Purpose: Centralizes data retrieval so controllers/services don't
 * directly access Spring XML configuration.</p>
 */
@Repository
public class CountryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDao.class);

    /**
     * Returns the full list of countries loaded from country.xml.
     *
     * @return List of all Country beans defined in country.xml
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = (ArrayList<Country>) context.getBean("countryList", ArrayList.class);
        LOGGER.debug("Countries loaded: {}", countries.size());
        LOGGER.info("End");
        return countries;
    }
}

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
 *
 * Loads the country list from country.xml each time getAllCountries() is called.
 * @Repository marks this as a Spring-managed DAO bean (specialisation of @Component).
 */
@Repository
public class CountryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDao.class);

    /**
     * Loads and returns all countries defined in country.xml.
     *
     * ClassPathXmlApplicationContext reads country.xml from src/main/resources.
     * context.getBean("countryList") retrieves the ArrayList<Country> bean.
     *
     * @return List of all four Country beans
     */
    @SuppressWarnings("unchecked")
    public List<Country> getAllCountries() {
        LOGGER.info("Start");
        ApplicationContext context = new ClassPathXmlApplicationContext("country.xml");
        List<Country> countries = (ArrayList<Country>) context.getBean("countryList", ArrayList.class);
        LOGGER.debug("Loaded {} countries from country.xml", countries.size());
        LOGGER.info("End");
        return countries;
    }
}

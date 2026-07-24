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
 * DAO for Country data – loads from country.xml.
 * Carried forward from Handbook 2.
 */
@Repository
public class CountryDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryDao.class);

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

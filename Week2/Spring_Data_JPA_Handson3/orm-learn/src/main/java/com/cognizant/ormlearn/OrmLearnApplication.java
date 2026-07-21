package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * OrmLearnApplication - Spring Boot Entry Point
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 1
 * Topic: Write JPQL Queries using @Query annotation
 *
 * Five test methods demonstrating each @Query pattern:
 *   testFindCountryByName()        - positional param ?1
 *   testSearchByKeyword()          - named param :keyword + LIKE
 *   testGetAllCountriesOrdered()   - ORDER BY in JPQL
 *   testFindByNameStartingWith()   - LIKE with named param
 *   testCountCountries()           - COUNT aggregate
 *   testFindCountriesEndingWithIa()- native SQL query
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);
        LOGGER.info("Inside main");

        // ── Hands-on 1: @Query JPQL methods ──────────────────────────
        testFindCountryByName();
        testSearchByKeyword();
        testGetAllCountriesOrdered();
        testFindByNameStartingWith();
        testCountCountries();
        testFindCountriesEndingWithIa();
    }

    /**
     * testFindCountryByName
     * @Query: SELECT c FROM Country c WHERE c.name = ?1
     * SQL   : SELECT * FROM country WHERE co_name = 'India'
     * Expect: Country{code='IN', name='India'}
     */
    private static void testFindCountryByName() {
        LOGGER.info("Start testFindCountryByName");
        List<Country> countries = countryService.findCountryByName("India");
        LOGGER.debug("Result count: {}", countries.size());
        for (Country c : countries) {
            LOGGER.debug("{}", c);
        }
        LOGGER.info("End testFindCountryByName");
    }

    /**
     * testSearchByKeyword
     * @Query: SELECT c FROM Country c WHERE c.name LIKE %:keyword%
     * SQL   : SELECT * FROM country WHERE co_name LIKE '%land%'
     * Expect: Finland, Iceland, Ireland, Greenland, Swaziland, etc.
     */
    private static void testSearchByKeyword() {
        LOGGER.info("Start testSearchByKeyword");
        List<Country> countries = countryService.searchByKeyword("land");
        LOGGER.debug("Countries containing 'land': count={}", countries.size());
        for (Country c : countries) {
            LOGGER.debug("{}", c);
        }
        LOGGER.info("End testSearchByKeyword");
    }

    /**
     * testGetAllCountriesOrdered
     * @Query: SELECT c FROM Country c ORDER BY c.name ASC
     * SQL   : SELECT * FROM country ORDER BY co_name ASC
     * Expect: 249 countries from Aland Islands to Zimbabwe
     */
    private static void testGetAllCountriesOrdered() {
        LOGGER.info("Start testGetAllCountriesOrdered");
        List<Country> countries = countryService.getAllCountriesOrdered();
        LOGGER.debug("Total sorted countries: {}", countries.size());
        LOGGER.debug("First: {}", countries.get(0));
        LOGGER.debug("Last : {}", countries.get(countries.size() - 1));
        LOGGER.info("End testGetAllCountriesOrdered");
    }

    /**
     * testFindByNameStartingWith
     * @Query: SELECT c FROM Country c WHERE c.name LIKE :letter%
     * SQL   : SELECT * FROM country WHERE co_name LIKE 'Z%'
     * Expect: Zambia, Zimbabwe
     */
    private static void testFindByNameStartingWith() {
        LOGGER.info("Start testFindByNameStartingWith");
        List<Country> countries = countryService.findByNameStartingWith("Z");
        LOGGER.debug("Countries starting with 'Z': count={}", countries.size());
        for (Country c : countries) {
            LOGGER.debug("{}", c);
        }
        LOGGER.info("End testFindByNameStartingWith");
    }

    /**
     * testCountCountries
     * @Query: SELECT COUNT(c) FROM Country c
     * SQL   : SELECT COUNT(*) FROM country
     * Expect: 249
     */
    private static void testCountCountries() {
        LOGGER.info("Start testCountCountries");
        long count = countryService.countCountries();
        LOGGER.debug("Total countries in DB: {}", count);
        LOGGER.info("End testCountCountries");
    }

    /**
     * testFindCountriesEndingWithIa
     * Native SQL: SELECT * FROM country WHERE co_name LIKE '%ia'
     * Expect: India, Arabia, Malaysia, Romania, Bulgaria...
     */
    private static void testFindCountriesEndingWithIa() {
        LOGGER.info("Start testFindCountriesEndingWithIa");
        List<Country> countries = countryService.findCountriesEndingWithIa();
        LOGGER.debug("Countries ending with 'ia': count={}", countries.size());
        for (Country c : countries) {
            LOGGER.debug("{}", c);
        }
        LOGGER.info("End testFindCountriesEndingWithIa");
    }

}

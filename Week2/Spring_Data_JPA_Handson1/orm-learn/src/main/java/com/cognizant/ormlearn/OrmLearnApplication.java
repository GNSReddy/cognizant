package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

/**
 * OrmLearnApplication - Spring Boot Entry Point
 *
 * Handbook: spring-data-jpa-handson.docx
 *
 * Updated for Hands-on 9 (final exercise of Handbook 1):
 *   - testUpdateCountry() from Hands-on 8 is commented out
 *   - testDeleteCountry() is added and invoked
 *
 * Complete list of test methods (all hands-on 1 through 9):
 *   testGetAllCountries()   - Hands-on 1
 *   testFindCountryByCode() - Hands-on 6
 *   testAddCountry()        - Hands-on 7
 *   testUpdateCountry()     - Hands-on 8
 *   testDeleteCountry()     - Hands-on 9  ← ACTIVE NOW
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService countryService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService = context.getBean(CountryService.class);
        LOGGER.info("Inside main");

        // ── Hands-on 1 (commented out) ────────────────────────────────
        // testGetAllCountries();

        // ── Hands-on 6 (commented out) ────────────────────────────────
        // testFindCountryByCode();

        // ── Hands-on 7 (commented out) ────────────────────────────────
        // testAddCountry();

        // ── Hands-on 8 (commented out) ────────────────────────────────
        // testUpdateCountry();

        // ── Hands-on 9: Delete Country ────────────────────────────────
        testDeleteCountry();
    }

    // ── Hands-on 1 ───────────────────────────────────────────────────────

    private static void testGetAllCountries() {
        LOGGER.info("Start");
        List<Country> countries = countryService.getAllCountries();
        LOGGER.debug("countries={}", countries);
        LOGGER.info("End");
    }

    // ── Hands-on 6 ───────────────────────────────────────────────────────

    private static void testFindCountryByCode() {
        LOGGER.info("Start");
        try {
            Country country = countryService.findCountryByCode("IN");
            LOGGER.debug("Country:{}", country);
        } catch (CountryNotFoundException e) {
            LOGGER.error("CountryNotFoundException: {}", e.getMessage());
        }
        LOGGER.info("End");
    }

    // ── Hands-on 7 ───────────────────────────────────────────────────────

    private static void testAddCountry() {
        LOGGER.info("Start");
        Country newCountry = new Country();
        newCountry.setCode("ZZ");
        newCountry.setName("Test Country");
        LOGGER.debug("Country to add: {}", newCountry);
        countryService.addCountry(newCountry);
        LOGGER.debug("Country added successfully");
        try {
            Country fetchedCountry = countryService.findCountryByCode("ZZ");
            LOGGER.debug("Verified - Country fetched after add: {}", fetchedCountry);
        } catch (CountryNotFoundException e) {
            LOGGER.error("Verification failed: {}", e.getMessage());
        }
        LOGGER.info("End");
    }

    // ── Hands-on 8 ───────────────────────────────────────────────────────

    private static void testUpdateCountry() {
        LOGGER.info("Start");
        try {
            LOGGER.debug("Updating country ZZ with new name...");
            countryService.updateCountry("ZZ", "Updated Test Country");
            LOGGER.debug("Country updated successfully");
            Country updatedCountry = countryService.findCountryByCode("ZZ");
            LOGGER.debug("Verified country after update: {}", updatedCountry);
        } catch (CountryNotFoundException e) {
            LOGGER.error("CountryNotFoundException: {}", e.getMessage());
        }
        LOGGER.info("End");
    }

    // ── Hands-on 9 ───────────────────────────────────────────────────────

    /**
     * testDeleteCountry - Tests deleteCountry() service method.
     *
     * Handbook steps:
     *   Step 1: Invoke deleteCountry() in CountryService passing a country code
     *   Step 2: Check in database table if the country is deleted
     *
     * Test scenario:
     *   - Delete country with code "ZZ" (inserted in Hands-on 7)
     *   - Verify deletion by attempting to fetch "ZZ" again
     *     → Should throw CountryNotFoundException (record no longer exists)
     *
     * Expected SQL:
     *   SELECT * FROM country WHERE co_code = 'ZZ'  (deleteById check)
     *   DELETE FROM country WHERE co_code = 'ZZ'    (actual delete)
     *   SELECT * FROM country WHERE co_code = 'ZZ'  (verification — row gone)
     *
     * Expected log:
     *   DEBUG  Deleting country with code ZZ...
     *   DEBUG  Country with code ZZ deleted successfully
     *   ERROR  Verified deleted - CountryNotFoundException: Country not found for code: ZZ
     */
    private static void testDeleteCountry() {
        LOGGER.info("Start");

        // Step 1: Call deleteCountry() passing country code "ZZ"
        // SQL: DELETE FROM country WHERE co_code = 'ZZ'
        LOGGER.debug("Deleting country with code ZZ...");
        countryService.deleteCountry("ZZ");
        LOGGER.debug("Delete operation completed");

        // Step 2: Verify that the country is deleted
        // Attempt to fetch "ZZ" — should throw CountryNotFoundException
        // confirming the row no longer exists in the database
        try {
            Country deletedCountry = countryService.findCountryByCode("ZZ");
            LOGGER.debug("Country still exists (unexpected): {}", deletedCountry);
        } catch (CountryNotFoundException e) {
            LOGGER.info("Verified deleted - CountryNotFoundException: {}", e.getMessage());
        }

        // Step 3: Also verify in MySQL manually:
        //   mysql> SELECT * FROM country WHERE co_code = 'ZZ';
        //   Expected: Empty set (0 rows)

        LOGGER.info("End");
    }

}

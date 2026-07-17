package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CountryService - Service Layer for Country operations.
 *
 * Complete set of CRUD methods as per Hands-on 1 handbook:
 *   Hands-on 1 : getAllCountries()    - SELECT all countries
 *   Hands-on 6 : findCountryByCode() - SELECT by primary key, throw if absent
 *   Hands-on 7 : addCountry()        - INSERT new country record
 *   Hands-on 8 : updateCountry()     - UPDATE country name by code
 *   Hands-on 9 : deleteCountry()     - DELETE country by code  ← ADDED NOW
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    // ── Hands-on 1: Get All Countries ────────────────────────────────────

    /**
     * Retrieves all countries from the country table.
     * SQL: SELECT co_code, co_name FROM country
     *
     * @return List of all Country entities
     */
    @Transactional
    public List<Country> getAllCountries() {
        LOGGER.debug("Inside getAllCountries");
        return countryRepository.findAll();
    }

    // ── Hands-on 6: Find Country By Code ─────────────────────────────────

    /**
     * Finds a single country by its 2-letter ISO country code.
     * Throws CountryNotFoundException if code does not exist in DB.
     *
     * @param countryCode the 2-letter ISO code e.g. "IN", "US"
     * @return Country entity matching the code
     * @throws CountryNotFoundException if no country found for the given code
     */
    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        LOGGER.debug("Inside findCountryByCode, countryCode={}", countryCode);

        Optional<Country> result = countryRepository.findById(countryCode);

        if (!result.isPresent()) {
            LOGGER.error("Country not found for code: {}", countryCode);
            throw new CountryNotFoundException("Country not found for code: " + countryCode);
        }

        Country country = result.get();
        LOGGER.debug("Country found: {}", country);
        return country;
    }

    // ── Hands-on 7: Add a New Country ────────────────────────────────────

    /**
     * Saves a new Country record to the database.
     * SQL: INSERT INTO country (co_code, co_name) VALUES (?, ?)
     *
     * @param country the Country object to persist
     */
    @Transactional
    public void addCountry(Country country) {
        LOGGER.debug("Inside addCountry, country={}", country);
        countryRepository.save(country);
        LOGGER.debug("Country added successfully");
    }

    // ── Hands-on 8: Update a Country ─────────────────────────────────────

    /**
     * Updates the name of an existing country identified by its code.
     * Steps: findById() → setName() → save()
     * SQL: SELECT ... then UPDATE country SET co_name=? WHERE co_code=?
     *
     * @param code the 2-letter country code to identify the record
     * @param name the new name to replace the existing name
     * @throws CountryNotFoundException if no country found for the given code
     */
    @Transactional
    public void updateCountry(String code, String name) throws CountryNotFoundException {
        LOGGER.debug("Inside updateCountry, code={}, newName={}", code, name);

        Optional<Country> result = countryRepository.findById(code);

        if (!result.isPresent()) {
            LOGGER.error("Country not found for code: {}", code);
            throw new CountryNotFoundException("Country not found for code: " + code);
        }

        Country country = result.get();
        LOGGER.debug("Country before update: {}", country);
        country.setName(name);
        countryRepository.save(country);
        LOGGER.debug("Country updated successfully: {}", country);
    }

    // ── Hands-on 9: Delete a Country ─────────────────────────────────────

    /**
     * Deletes a country record from the database by its 2-letter code.
     *
     * Handbook method signature:
     *   public void deleteCountry(String code)
     *
     * Implementation:
     *   Calls deleteById() method of JpaRepository, passing the country code.
     *   deleteById() internally:
     *     1. Checks if the entity with given ID exists
     *     2. If yes, loads it and calls remove() → executes DELETE SQL
     *     3. If no, throws EmptyResultDataAccessException
     *
     * SQL generated:
     *   SELECT * FROM country WHERE co_code = ?  (existence check by deleteById)
     *   DELETE FROM country WHERE co_code = ?     (actual delete)
     *
     * @Transactional - Spring opens session, begins transaction,
     *                  commits after delete, closes session.
     *                  Rollback occurs automatically if any exception is thrown.
     *
     * @param code the 2-letter ISO country code of the record to delete
     */
    @Transactional
    public void deleteCountry(String code) {
        LOGGER.debug("Inside deleteCountry, code={}", code);

        // Call deleteById() from JpaRepository
        // This executes: DELETE FROM country WHERE co_code = ?
        countryRepository.deleteById(code);

        LOGGER.debug("Country with code {} deleted successfully", code);
    }

}

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
 * CountryService - Service Layer for Country CRUD and Query operations.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 1
 *
 * Contains all Handson 1 CRUD methods (carried forward) PLUS:
 *   searchCountries()            - find by keyword (Hands-on 1 of Handbook 2)
 *   getSortedCountries()         - sorted alphabetically (Hands-on 1 of Handbook 2)
 *   findCountriesStartingWith()  - find by first letter (Hands-on 1 of Handbook 2)
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    // ── CRUD Methods (carried from Handson 1) ────────────────────────────

    @Transactional
    public List<Country> getAllCountries() {
        LOGGER.debug("Inside getAllCountries");
        return countryRepository.findAll();
    }

    @Transactional
    public Country findCountryByCode(String countryCode) throws CountryNotFoundException {
        LOGGER.debug("Inside findCountryByCode, countryCode={}", countryCode);
        Optional<Country> result = countryRepository.findById(countryCode);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + countryCode);
        }
        return result.get();
    }

    @Transactional
    public void addCountry(Country country) {
        LOGGER.debug("Inside addCountry, country={}", country);
        countryRepository.save(country);
    }

    @Transactional
    public void updateCountry(String code, String name) throws CountryNotFoundException {
        LOGGER.debug("Inside updateCountry, code={}", code);
        Optional<Country> result = countryRepository.findById(code);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found for code: " + code);
        }
        Country country = result.get();
        country.setName(name);
        countryRepository.save(country);
    }

    @Transactional
    public void deleteCountry(String code) {
        LOGGER.debug("Inside deleteCountry, code={}", code);
        countryRepository.deleteById(code);
    }

    // ── Hands-on 1 of Handbook 2: Query Method Services ─────────────────

    /**
     * Search countries whose name contains the given keyword.
     *
     * Delegates to: CountryRepository.findByNameContaining(keyword)
     * SQL: SELECT * FROM country WHERE co_name LIKE '%keyword%'
     *
     * Example: searchCountries("ia") returns:
     *   India, Arabia, Malaysia, Romania, Bulgaria, Croatia, Serbia...
     *
     * @Transactional - opens Hibernate session for the query
     *
     * @param keyword the substring to search for in country names
     * @return List of countries whose name contains the keyword
     */
    @Transactional
    public List<Country> searchCountries(String keyword) {
        LOGGER.debug("Inside searchCountries, keyword={}", keyword);
        return countryRepository.findByNameContaining(keyword);
    }

    /**
     * Get all countries sorted alphabetically by name (A → Z).
     *
     * Delegates to: CountryRepository.findAllByOrderByNameAsc()
     * SQL: SELECT * FROM country ORDER BY co_name ASC
     *
     * @Transactional - opens Hibernate session for the query
     *
     * @return List of all countries sorted alphabetically by name
     */
    @Transactional
    public List<Country> getSortedCountries() {
        LOGGER.debug("Inside getSortedCountries");
        return countryRepository.findAllByOrderByNameAsc();
    }

    /**
     * Find all countries whose name starts with the given letter or prefix.
     *
     * Delegates to: CountryRepository.findByNameStartingWith(letter)
     * SQL: SELECT * FROM country WHERE co_name LIKE 'letter%'
     *
     * Example: findCountriesStartingWith("A") returns:
     *   Afghanistan, Albania, Algeria, American Samoa, Andorra, Angola...
     *
     * @Transactional - opens Hibernate session for the query
     *
     * @param letter the starting character(s) to filter country names
     * @return List of countries whose name starts with the given letter
     */
    @Transactional
    public List<Country> findCountriesStartingWith(String letter) {
        LOGGER.debug("Inside findCountriesStartingWith, letter={}", letter);
        return countryRepository.findByNameStartingWith(letter);
    }

}

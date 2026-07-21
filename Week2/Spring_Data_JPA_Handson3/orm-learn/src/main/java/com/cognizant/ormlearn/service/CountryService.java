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
 * CountryService - Service Layer for Country @Query operations.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 1
 * Topic: Write JPQL Queries using @Query annotation
 *
 * Each method delegates to a @Query-annotated method in CountryRepository.
 * Spring Data JPA executes the JPQL/SQL specified in @Query.
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    // ── Base method carried forward from Handson 1 ───────────────────────

    @Transactional
    public Country findCountryByCode(String code) throws CountryNotFoundException {
        LOGGER.debug("Inside findCountryByCode, code={}", code);
        Optional<Country> result = countryRepository.findById(code);
        if (!result.isPresent()) {
            throw new CountryNotFoundException("Country not found: " + code);
        }
        return result.get();
    }

    // ── @Query Methods — Hands-on 1 of Handbook 3 ───────────────────────

    /**
     * Find country by exact name using JPQL positional parameter (?1).
     *
     * Delegates to: @Query("SELECT c FROM Country c WHERE c.name = ?1")
     * SQL: SELECT co_code, co_name FROM country WHERE co_name = ?
     *
     * @param name the exact country name to search
     * @return List of matching Country entities
     */
    @Transactional
    public List<Country> findCountryByName(String name) {
        LOGGER.debug("Inside findCountryByName, name={}", name);
        return countryRepository.findCountryByName(name);
    }

    /**
     * Search countries by keyword using JPQL named parameter (:keyword).
     *
     * Delegates to: @Query("SELECT c FROM Country c WHERE c.name LIKE %:keyword%")
     * SQL: SELECT co_code, co_name FROM country WHERE co_name LIKE '%keyword%'
     *
     * @param keyword substring to search for in country names
     * @return List of countries whose name contains the keyword
     */
    @Transactional
    public List<Country> searchByKeyword(String keyword) {
        LOGGER.debug("Inside searchByKeyword, keyword={}", keyword);
        return countryRepository.findByKeyword(keyword);
    }

    /**
     * Get all countries sorted A→Z using JPQL ORDER BY.
     *
     * Delegates to: @Query("SELECT c FROM Country c ORDER BY c.name ASC")
     * SQL: SELECT co_code, co_name FROM country ORDER BY co_name ASC
     *
     * @return List of all countries sorted alphabetically by name
     */
    @Transactional
    public List<Country> getAllCountriesOrdered() {
        LOGGER.debug("Inside getAllCountriesOrdered");
        return countryRepository.findAllCountriesOrderedByName();
    }

    /**
     * Find countries starting with a letter using JPQL LIKE.
     *
     * Delegates to: @Query("SELECT c FROM Country c WHERE c.name LIKE :letter%")
     * SQL: SELECT co_code, co_name FROM country WHERE co_name LIKE 'letter%'
     *
     * @param letter starting character to filter by
     * @return List of countries whose name starts with the given letter
     */
    @Transactional
    public List<Country> findByNameStartingWith(String letter) {
        LOGGER.debug("Inside findByNameStartingWith, letter={}", letter);
        return countryRepository.findByNameStartingWithLetter(letter);
    }

    /**
     * Count total countries using JPQL COUNT aggregate.
     *
     * Delegates to: @Query("SELECT COUNT(c) FROM Country c")
     * SQL: SELECT COUNT(*) FROM country
     *
     * @return total count of country records
     */
    @Transactional
    public long countCountries() {
        LOGGER.debug("Inside countCountries");
        return countryRepository.countAllCountries();
    }

    /**
     * Find countries ending with "ia" using native SQL query.
     *
     * Delegates to: @Query(value="SELECT * FROM country WHERE co_name LIKE '%ia'",
     *                      nativeQuery=true)
     *
     * @return List of countries whose name ends with "ia"
     */
    @Transactional
    public List<Country> findCountriesEndingWithIa() {
        LOGGER.debug("Inside findCountriesEndingWithIa");
        return countryRepository.findCountriesEndingWithIa();
    }

}

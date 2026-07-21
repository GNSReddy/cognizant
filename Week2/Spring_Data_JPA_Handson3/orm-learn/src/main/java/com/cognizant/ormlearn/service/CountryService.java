package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CountryService - Service Layer for Country operations.
 *
 * Handbook: Spring Data JPA Hands-on 3
 *   Hands-on 1: @Query methods
 *   Hands-on 3: Pagination using PageRequest ← ADDED NOW
 */
@Service
public class CountryService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CountryService.class);

    @Autowired
    private CountryRepository countryRepository;

    // ── Base CRUD ─────────────────────────────────────────────────────────
    @Transactional
    public Country findCountryByCode(String code) throws CountryNotFoundException {
        Optional<Country> result = countryRepository.findById(code);
        if (!result.isPresent()) throw new CountryNotFoundException("Country not found: " + code);
        return result.get();
    }

    // ── Hands-on 1: @Query methods (carried forward) ─────────────────────
    @Transactional
    public List<Country> findCountryByName(String name) {
        return countryRepository.findCountryByName(name);
    }

    @Transactional
    public List<Country> searchByKeyword(String keyword) {
        return countryRepository.findByKeyword(keyword);
    }

    @Transactional
    public List<Country> getAllCountriesOrdered() {
        return countryRepository.findAllCountriesOrderedByName();
    }

    @Transactional
    public List<Country> findByNameStartingWith(String letter) {
        return countryRepository.findByNameStartingWithLetter(letter);
    }

    @Transactional
    public long countCountries() {
        return countryRepository.countAllCountries();
    }

    @Transactional
    public List<Country> findCountriesEndingWithIa() {
        return countryRepository.findCountriesEndingWithIa();
    }

    // ── Hands-on 3: Pagination ────────────────────────────────────────────

    /**
     * Get all countries — paginated using PageRequest.
     *
     * PageRequest.of(pageNumber, pageSize):
     *   Creates a Pageable object specifying which page and how many records.
     *   pageNumber is 0-indexed: page 0 = first page, page 1 = second page...
     *
     * Two SQL queries fired by Hibernate:
     *   Data : SELECT co_code, co_name FROM country LIMIT {pageSize} OFFSET {pageNumber * pageSize}
     *   Count: SELECT COUNT(*) FROM country
     *
     * Page<Country> contains:
     *   getContent()        → List<Country> for the current page
     *   getTotalElements()  → total records in ALL pages (e.g., 249)
     *   getTotalPages()     → total number of pages (e.g., 249/10 = 25)
     *   getNumber()         → current page number (0-indexed)
     *   isFirst() / isLast()→ boundary checks
     *
     * @param pageNumber 0-indexed page number
     * @param pageSize   number of records per page
     * @return Page<Country> for the requested page
     */
    @Transactional
    public Page<Country> getCountriesByPage(int pageNumber, int pageSize) {
        LOGGER.debug("Inside getCountriesByPage, page={}, size={}", pageNumber, pageSize);
        return countryRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    /**
     * Search countries by keyword — paginated.
     *
     * Applies the LIKE filter AND pagination together.
     * SQL: SELECT * FROM country WHERE co_name LIKE '%keyword%' LIMIT ? OFFSET ?
     *
     * @param keyword    substring to filter by
     * @param pageNumber 0-indexed page number
     * @param pageSize   records per page
     * @return Page<Country> of filtered results for the requested page
     */
    @Transactional
    public Page<Country> searchByKeywordPageable(String keyword, int pageNumber, int pageSize) {
        LOGGER.debug("Inside searchByKeywordPageable, keyword={}, page={}, size={}",
                keyword, pageNumber, pageSize);
        return countryRepository.findByKeywordPageable(keyword, PageRequest.of(pageNumber, pageSize));
    }

}

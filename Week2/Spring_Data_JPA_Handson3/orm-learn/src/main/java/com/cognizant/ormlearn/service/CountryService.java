package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.repository.CountryRepository;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * CountryService - Service Layer for Country operations.
 *
 * Handbook: Spring Data JPA Hands-on 3
 *   Hands-on 1: @Query methods
 *   Hands-on 3: Pagination using PageRequest
 *   Hands-on 4: Sorting using Sort ← ADDED NOW
 *
 * Sort key concepts:
 *   Sort.by(Sort.Direction.ASC, "name")  → ORDER BY co_name ASC
 *   Sort.by(Sort.Direction.DESC, "name") → ORDER BY co_name DESC
 *   findAll(Sort) returns all records sorted — no pagination.
 *   PageRequest.of(page, size, Sort) = pagination + sorting together.
 *
 * Field names in Sort use the JAVA FIELD NAMES (not DB column names):
 *   "name" → mapped to co_name column by Hibernate
 *   "code" → mapped to co_code column by Hibernate
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

    // ── Hands-on 1: @Query (carried forward) ─────────────────────────────
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

    // ── Hands-on 3: Pagination (carried forward) ──────────────────────────
    @Transactional
    public Page<Country> getCountriesByPage(int pageNumber, int pageSize) {
        return countryRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    @Transactional
    public Page<Country> searchByKeywordPageable(String keyword, int pageNumber, int pageSize) {
        return countryRepository.findByKeywordPageable(keyword, PageRequest.of(pageNumber, pageSize));
    }

    // ── Hands-on 4: Sorting ───────────────────────────────────────────────

    /**
     * Get all countries sorted by name in ASCENDING order.
     *
     * Sort.by(Sort.Direction.ASC, "name"):
     *   "name" = Java field name in Country entity
     *   Hibernate maps this to co_name column
     *
     * Generated SQL:
     *   SELECT co_code, co_name FROM country ORDER BY co_name ASC
     *
     * Note: findAll(Sort) is inherited from JpaRepository.
     *   No new method needed in CountryRepository.
     *
     * @return List of all countries sorted A → Z by name
     */
    @Transactional
    public List<Country> getCountriesSortedAsc() {
        LOGGER.debug("Inside getCountriesSortedAsc");
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        return countryRepository.findAll(sort);
    }

    /**
     * Get all countries sorted by name in DESCENDING order.
     *
     * Sort.by(Sort.Direction.DESC, "name"):
     * Generated SQL:
     *   SELECT co_code, co_name FROM country ORDER BY co_name DESC
     *
     * @return List of all countries sorted Z → A by name
     */
    @Transactional
    public List<Country> getCountriesSortedDesc() {
        LOGGER.debug("Inside getCountriesSortedDesc");
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        return countryRepository.findAll(sort);
    }

    /**
     * Get all countries — sorted AND paginated.
     *
     * Combining Sort with PageRequest:
     *   PageRequest.of(pageNumber, pageSize, Sort)
     *   Sorting direction is applied within each page.
     *
     * Generated SQL:
     *   SELECT co_code, co_name FROM country
     *   ORDER BY co_name ASC LIMIT ? OFFSET ?
     *
     * @param pageNumber 0-indexed page
     * @param pageSize   records per page
     * @param direction  ASC or DESC
     * @return Page<Country> sorted and paginated
     */
    @Transactional
    public Page<Country> getCountriesSortedAndPaged(int pageNumber, int pageSize,
                                                     Sort.Direction direction) {
        LOGGER.debug("Inside getCountriesSortedAndPaged, page={}, size={}, dir={}",
                pageNumber, pageSize, direction);
        Sort sort = Sort.by(direction, "name");
        return countryRepository.findAll(PageRequest.of(pageNumber, pageSize, sort));
    }

}

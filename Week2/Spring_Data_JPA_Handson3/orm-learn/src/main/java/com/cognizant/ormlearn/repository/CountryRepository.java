package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CountryRepository - Spring Data JPA Repository for Country entity.
 *
 * Handbook: Spring Data JPA Hands-on 3
 *   Hands-on 1: @Query JPQL on Country
 *   Hands-on 3: Pagination using Pageable ← ADDED NOW
 *
 * Pagination key concepts:
 *   Pageable : Holds page number (0-based), page size, and optional Sort.
 *   Page<T>  : Return type wrapping a List + metadata (totalPages, totalElements).
 *
 * JpaRepository already inherits:
 *   Page<T> findAll(Pageable pageable);
 * We override it here explicitly to make it visible for learning purposes.
 *
 * SQL generated with Pageable:
 *   SELECT * FROM country LIMIT ? OFFSET ?
 *   SELECT COUNT(*) FROM country        ← for totalElements metadata
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // ── Hands-on 1: @Query JPQL (carried forward) ────────────────────────

    @Query("SELECT c FROM Country c WHERE c.name = ?1")
    List<Country> findCountryByName(String name);

    @Query("SELECT c FROM Country c WHERE c.name LIKE %:keyword%")
    List<Country> findByKeyword(@Param("keyword") String keyword);

    @Query("SELECT c FROM Country c ORDER BY c.name ASC")
    List<Country> findAllCountriesOrderedByName();

    @Query("SELECT c FROM Country c WHERE c.name LIKE :letter%")
    List<Country> findByNameStartingWithLetter(@Param("letter") String letter);

    @Query("SELECT COUNT(c) FROM Country c")
    long countAllCountries();

    @Query(value = "SELECT * FROM country WHERE co_name LIKE '%ia'", nativeQuery = true)
    List<Country> findCountriesEndingWithIa();

    // ── Hands-on 3: Pagination ────────────────────────────────────────────

    /**
     * Get all countries in a paginated fashion.
     *
     * Pageable parameter carries:
     *   - pageNumber : which page to fetch (0-indexed)
     *   - pageSize   : how many records per page
     *   - sort       : optional sorting (can be Sort.unsorted())
     *
     * Spring Data JPA auto-generates TWO SQL queries:
     *   Query 1 (data): SELECT co_code, co_name FROM country LIMIT ? OFFSET ?
     *   Query 2 (count): SELECT COUNT(*) FROM country
     *   Both are needed to populate Page<Country> metadata.
     *
     * @param pageable the pagination information (page, size)
     * @return Page<Country> containing the current page data + metadata
     */
    Page<Country> findAll(Pageable pageable);

    /**
     * Get countries whose name contains a keyword — with pagination.
     *
     * Combines @Query LIKE search WITH Pageable parameter.
     * Spring applies LIMIT/OFFSET on top of the WHERE LIKE filter.
     *
     * SQL: SELECT * FROM country WHERE co_name LIKE '%keyword%' LIMIT ? OFFSET ?
     *
     * @param keyword  substring to filter countries by name
     * @param pageable the pagination information
     * @return Page<Country> of filtered results for the requested page
     */
    @Query("SELECT c FROM Country c WHERE c.name LIKE %:keyword%")
    Page<Country> findByKeywordPageable(@Param("keyword") String keyword, Pageable pageable);

}

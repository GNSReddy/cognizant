package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CountryRepository - Spring Data JPA Repository with @Query annotation.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 1
 * Topic: Write JPQL Queries using @Query annotation
 *
 * Difference: Derived Query Methods vs @Query
 * ──────────────────────────────────────────────
 * Derived (Handson 2):
 *   Method name → Spring parses and generates SQL automatically.
 *   e.g., findByNameContaining(String kw) → WHERE co_name LIKE '%kw%'
 *   Limited to simple conditions Spring can parse from method names.
 *
 * @Query (Handson 3):
 *   Developer writes explicit JPQL or native SQL in the annotation.
 *   Full power of JPQL — joins, subqueries, functions, named params.
 *   @Query uses JPQL (entity class names and field names, NOT table/column names).
 *   @Query(nativeQuery=true) uses raw SQL (table/column names).
 *
 * JPQL vs SQL:
 *   JPQL : SELECT c FROM Country c WHERE c.name LIKE :name
 *                  ↑ entity class    ↑ Java field name
 *   SQL  : SELECT * FROM country WHERE co_name LIKE :name
 *                  ↑ table name      ↑ MySQL column name
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // ── @Query — JPQL Queries ─────────────────────────────────────────────

    /**
     * Find a country by its exact name using JPQL @Query.
     *
     * @Query:
     *   "SELECT c FROM Country c WHERE c.name = ?1"
     *   - Country c : refers to the Java entity class, not the DB table
     *   - c.name    : refers to the Java field 'name', not column 'co_name'
     *   - ?1        : positional parameter (first method argument)
     *
     * Generated SQL:
     *   SELECT co_code, co_name FROM country WHERE co_name = ?
     *
     * @param name exact country name to search for
     * @return List of matching Country entities (usually 0 or 1)
     */
    @Query("SELECT c FROM Country c WHERE c.name = ?1")
    List<Country> findCountryByName(String name);

    /**
     * Find countries whose name contains a keyword — JPQL with named @Param.
     *
     * @Query with named parameter (:keyword) and @Param:
     *   Named params improve readability over positional (?1, ?2...).
     *   @Param("keyword") binds the method argument to :keyword in the query.
     *
     * LIKE with % appended in Java:
     *   The % wildcard must be added to the argument before calling.
     *   Service passes "%" + keyword + "%" as the argument.
     *   OR use JPQL CONCAT function: LIKE CONCAT('%', :keyword, '%')
     *
     * Generated SQL:
     *   SELECT co_code, co_name FROM country WHERE co_name LIKE ?
     *
     * @param keyword substring to search within country names
     * @return List of countries whose name contains the keyword
     */
    @Query("SELECT c FROM Country c WHERE c.name LIKE %:keyword%")
    List<Country> findByKeyword(@Param("keyword") String keyword);

    /**
     * Find all countries sorted alphabetically using JPQL ORDER BY.
     *
     * @Query with ORDER BY clause:
     *   JPQL supports ORDER BY using Java field names.
     *   c.name ASC → Hibernate generates: ORDER BY co_name ASC
     *
     * Generated SQL:
     *   SELECT co_code, co_name FROM country ORDER BY co_name ASC
     *
     * @return List of all countries sorted A → Z by name
     */
    @Query("SELECT c FROM Country c ORDER BY c.name ASC")
    List<Country> findAllCountriesOrderedByName();

    /**
     * Find countries whose name starts with a given letter — JPQL.
     *
     * Uses JPQL LIKE with a trailing wildcard.
     * The service passes "A" and the query becomes: WHERE co_name LIKE 'A%'
     *
     * Generated SQL:
     *   SELECT co_code, co_name FROM country WHERE co_name LIKE 'A%'
     *
     * @param letter the starting character(s) to filter by
     * @return List of countries whose name starts with the given letter
     */
    @Query("SELECT c FROM Country c WHERE c.name LIKE :letter%")
    List<Country> findByNameStartingWithLetter(@Param("letter") String letter);

    /**
     * Count total number of countries using JPQL COUNT function.
     *
     * @Query with aggregate function:
     *   JPQL supports COUNT, SUM, AVG, MIN, MAX just like SQL.
     *   Returns a Long (single scalar value, not a list of entities).
     *
     * Generated SQL:
     *   SELECT COUNT(*) FROM country
     *
     * @return total count of country records in the table
     */
    @Query("SELECT COUNT(c) FROM Country c")
    long countAllCountries();

    /**
     * Find countries using a native SQL query (nativeQuery = true).
     *
     * @Query(nativeQuery = true):
     *   Bypasses JPQL — uses raw MySQL SQL.
     *   Column names in WHERE clause must be actual DB column names (co_name).
     *   Returns List<Country> — Spring maps result rows to Country entity.
     *
     * When to use native queries:
     *   - Database-specific functions not in JPQL (e.g., MySQL REGEXP)
     *   - Complex joins that are difficult to express in JPQL
     *   - Performance-critical queries needing exact SQL control
     *
     * Generated SQL (sent AS-IS to MySQL):
     *   SELECT * FROM country WHERE co_name LIKE '%ia'
     *
     * @return List of countries whose name ends with "ia"
     */
    @Query(value = "SELECT * FROM country WHERE co_name LIKE '%ia'",
           nativeQuery = true)
    List<Country> findCountriesEndingWithIa();

}

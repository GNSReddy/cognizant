package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * CountryRepository - Spring Data JPA Repository for Country entity.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 1
 * Topic: Write queries on country table using Query Methods
 *
 * Query Methods (Derived Queries):
 *   Spring Data JPA parses the method name and auto-generates SQL.
 *   No @Query annotation or SQL string needed — just declare the method.
 *
 * Naming Convention:
 *   findBy<FieldName><Condition>(args)
 *   findAllBy<OrderBy>
 *
 * Methods added in Hands-on 1:
 *   findByNameContaining()    - LIKE '%keyword%' search
 *   findAllByOrderByNameAsc() - SELECT all, ORDER BY co_name ASC
 *   findByNameStartingWith()  - LIKE 'letter%' search
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // ── From Handson 1 (getAllCountries, findById, save, deleteById) ──────
    // All inherited automatically from JpaRepository — no need to declare.

    // ── Hands-on 1 of Handbook 2: Query Methods ──────────────────────────

    /**
     * Find all countries whose name CONTAINS the given keyword (case-insensitive).
     *
     * Query Method: findByNameContaining(keyword)
     * Spring generates SQL:
     *   SELECT * FROM country WHERE co_name LIKE '%keyword%'
     *
     * Use case: search for "ia" → returns India, Arabia, Malaysia, Romania...
     *
     * @param keyword substring to search for within country names
     * @return List of countries whose name contains the keyword
     */
    List<Country> findByNameContaining(String keyword);

    /**
     * Find all countries sorted alphabetically by name in ascending order.
     *
     * Query Method: findAllByOrderByNameAsc()
     * Spring generates SQL:
     *   SELECT * FROM country ORDER BY co_name ASC
     *
     * Use case: Display countries A to Z in a sorted dropdown
     *
     * @return List of all countries sorted A→Z by name
     */
    List<Country> findAllByOrderByNameAsc();

    /**
     * Find all countries whose name STARTS WITH the given letter or prefix.
     *
     * Query Method: findByNameStartingWith(letter)
     * Spring generates SQL:
     *   SELECT * FROM country WHERE co_name LIKE 'letter%'
     *
     * Use case: search for "A" → returns Afghanistan, Albania, Algeria...
     *
     * @param letter the starting character or prefix to filter by
     * @return List of countries whose name starts with the given letter
     */
    List<Country> findByNameStartingWith(String letter);

}

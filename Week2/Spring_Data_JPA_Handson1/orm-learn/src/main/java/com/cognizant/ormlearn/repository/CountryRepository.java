package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * CountryRepository - Spring Data JPA Repository Interface
 *
 * Extends JpaRepository<Country, String> where:
 *   Country - the entity type this repository manages
 *   String  - the type of the primary key (@Id field = co_code which is a String)
 *
 * By extending JpaRepository, Spring Data JPA automatically provides:
 *   findAll()         - SELECT * FROM country
 *   findById(code)    - SELECT * FROM country WHERE co_code = ?
 *   save(country)     - INSERT or UPDATE
 *   deleteById(code)  - DELETE WHERE co_code = ?
 *   count()           - SELECT COUNT(*)
 *   existsById(code)  - check if record exists
 *
 * No implementation class is needed.
 * Spring generates a proxy implementation at runtime.
 *
 * @Repository - Marks this as a Spring bean and enables exception translation.
 *               Converts JPA-specific exceptions into Spring's DataAccessException hierarchy.
 *
 * Handbook: com.cognizant.ormlearn.repository.CountryRepository
 */
@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    // Spring Data JPA provides all basic CRUD methods automatically.
    // Custom Query Methods will be added in subsequent Hands-on exercises.

}

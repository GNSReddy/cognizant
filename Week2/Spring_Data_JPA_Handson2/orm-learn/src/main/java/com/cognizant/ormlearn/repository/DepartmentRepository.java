package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DepartmentRepository - Spring Data JPA Repository for Department entity.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 5
 * Topic: Implement One-to-Many Relationship
 *
 * Extends JpaRepository<Department, Integer>:
 *   Department - entity type
 *   Integer    - type of @Id field (dept_id is int)
 *
 * findById(id) executes:
 *   SELECT dept_id, dept_name FROM department WHERE dept_id = ?
 *
 * Note: The employees list is NOT fetched by this query (FetchType.LAZY).
 * It is loaded separately inside @Transactional when getEmployees() is called.
 *
 * @Repository - Marks as Spring bean, enables exception translation.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    // All CRUD methods inherited from JpaRepository.
    // No custom Query Methods needed for this hands-on.

}

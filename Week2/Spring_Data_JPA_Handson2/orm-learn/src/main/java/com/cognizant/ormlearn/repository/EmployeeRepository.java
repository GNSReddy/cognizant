package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * EmployeeRepository - Spring Data JPA Repository for Employee entity.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 4
 * Topic: Implement Many-to-One Relationship
 *
 * Extends JpaRepository<Employee, Integer>:
 *   Employee - the entity type this repository manages
 *   Integer  - the type of the @Id field (emp_id is an int)
 *
 * Spring Data JPA auto-provides all CRUD methods:
 *   findAll()      → SELECT e.*, d.* FROM employee e JOIN department d ON e.dept_id = d.dept_id
 *   findById(id)   → same JOIN but WHERE emp_id = ?
 *   save(employee) → INSERT or UPDATE
 *   deleteById(id) → DELETE FROM employee WHERE emp_id = ?
 *
 * The JOIN with department happens automatically because Employee
 * has @ManyToOne(EAGER) Department — Hibernate includes the JOIN.
 *
 * @Repository - Marks as Spring bean, enables JPA exception translation.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // Basic CRUD inherited from JpaRepository.
    // Additional Query Methods will be added in Hands-on 5 and 6 as needed.

}

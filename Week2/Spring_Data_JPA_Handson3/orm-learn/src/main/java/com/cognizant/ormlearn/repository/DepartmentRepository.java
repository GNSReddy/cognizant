package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DepartmentRepository - Spring Data JPA Repository for Department entity.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 2
 * Topic: @Query annotation on Employee / Department tables
 *
 * All standard CRUD operations inherited from JpaRepository.
 * @Query methods are declared in EmployeeRepository (which navigates
 * e.department.name in JPQL to perform joins).
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department, Integer> {
    // CRUD inherited from JpaRepository.
}

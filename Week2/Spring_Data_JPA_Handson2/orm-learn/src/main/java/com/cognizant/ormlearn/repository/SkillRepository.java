package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * SkillRepository - Spring Data JPA Repository for Skill entity.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 6
 * Topic: Implement Many-to-Many Relationship
 *
 * Extends JpaRepository<Skill, Integer>:
 *   Skill   - the entity type this repository manages
 *   Integer - the type of @Id field (skill_id is int)
 *
 * Inherits all CRUD methods from JpaRepository.
 * The ManyToMany relationship is managed through Employee (owning side),
 * so no custom queries are needed on the Skill side for this hands-on.
 *
 * @Repository - Spring bean, enables JPA exception translation.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {

    // CRUD methods inherited from JpaRepository.
    // Skill retrieval via Employee's skills list is handled
    // through EmployeeRepository + @ManyToMany mapping in Employee entity.

}

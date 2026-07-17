package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Skill - JPA Entity mapped to the 'skill' table.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 6
 * Topic: Implement Many-to-Many Relationship
 *
 * Change from Hands-on 3:
 *   Added @ManyToMany(mappedBy = "skills") List<Employee> employees
 *   This is the INVERSE side of the bidirectional ManyToMany relationship.
 *
 * Bidirectional ManyToMany:
 *   Employee (owning side)  → @ManyToMany @JoinTable  List<Skill>    skills
 *   Skill    (inverse side) → @ManyToMany(mappedBy)  List<Employee> employees
 *
 * INVERSE SIDE Rules:
 *   - Uses mappedBy = "skills" to point to the field in Employee
 *     where @ManyToMany and @JoinTable are declared.
 *   - Does NOT declare @JoinTable — that is already done in Employee.
 *   - Hibernate uses the OWNING SIDE to manage the join table.
 *   - FetchType.LAZY: employees list loaded only when getEmployees() is called.
 *
 * Important: toString() does NOT include employees to avoid
 *   circular reference → Employee.toString() → Skill.toString() → Employee...
 */
@Entity
@Table(name = "skill")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int id;

    @Column(name = "skill_name")
    private String name;

    /**
     * @ManyToMany INVERSE SIDE — Skill can be held by many Employees.
     *
     * mappedBy = "skills":
     *   Refers to the field named "skills" in the Employee entity.
     *   That field is where @JoinTable is declared.
     *   This tells Hibernate: "Don't manage the join table from here —
     *   the Employee entity owns it."
     *
     * FetchType.LAZY:
     *   Employees list is NOT loaded when a Skill is loaded.
     *   Loaded on demand when getEmployees() is called inside @Transactional.
     *
     * SQL Hibernate uses to load employees for a skill:
     *   SELECT e.emp_id, e.emp_name, ...
     *   FROM employee e
     *   INNER JOIN employee_skill es ON e.emp_id = es.emp_id
     *   WHERE es.skill_id = ?
     */
    @ManyToMany(mappedBy = "skills", fetch = FetchType.LAZY)
    private List<Employee> employees;

    // ── Default Constructor ────────────────────────────────────────────

    public Skill() {
    }

    // ── Getters ────────────────────────────────────────────────────────

    public int getId()                     { return id; }
    public String getName()                { return name; }
    public List<Employee> getEmployees()   { return employees; }

    // ── Setters ────────────────────────────────────────────────────────

    public void setId(int id)                         { this.id = id; }
    public void setName(String name)                  { this.name = name; }
    public void setEmployees(List<Employee> employees) { this.employees = employees; }

    // ── toString (excludes employees to prevent circular reference) ────

    @Override
    public String toString() {
        return "Skill{id=" + id + ", name='" + name + "'}";
    }
}

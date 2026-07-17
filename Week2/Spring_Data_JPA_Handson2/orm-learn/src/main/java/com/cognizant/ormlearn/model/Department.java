package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 * Department - JPA Entity mapped to the 'department' table.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 5
 * Topic: Implement One-to-Many Relationship
 *
 * Change from Hands-on 3:
 *   Added @OneToMany List<Employee> employees
 *   This is the INVERSE (parent) side of the bidirectional relationship.
 *
 * Bidirectional Relationship:
 *   Employee  (owning side)  → has @ManyToOne @JoinColumn Department
 *   Department (inverse side) → has @OneToMany(mappedBy) List<Employee>
 *
 * @OneToMany Rules:
 *   - mappedBy = "department" refers to the field name in Employee class
 *     where @ManyToOne is declared. This tells Hibernate:
 *     "The FK is managed by Employee.department, not by a new column here."
 *   - FetchType.LAZY (default for @OneToMany):
 *     Employees are NOT loaded when Department is loaded.
 *     They are loaded only when department.getEmployees() is called,
 *     and ONLY if a Hibernate session is still open (inside @Transactional).
 *   - No @JoinColumn here — the FK (dept_id) lives in the employee table.
 */
@Entity
@Table(name = "department")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dept_id")
    private int id;

    @Column(name = "dept_name")
    private String name;

    /**
     * One Department has Many Employees — inverse side of the relationship.
     *
     * @OneToMany:
     *   Declares this as the "one" side of the One-to-Many relationship.
     *   Department can have many Employees.
     *
     * mappedBy = "department":
     *   Tells Hibernate that the Employee entity's field named "department"
     *   (the @ManyToOne field) is the owning side that holds the foreign key.
     *   No separate FK column is added to the department table.
     *
     * FetchType.LAZY:
     *   Employees are NOT fetched automatically when Department is loaded.
     *   Hibernate will execute a separate SELECT for employees only when
     *   getEmployees() is called inside an open @Transactional method.
     *   SQL: SELECT * FROM employee WHERE dept_id = ?
     *
     * cascade = {} (none by default):
     *   Operations on Department do NOT automatically cascade to Employee.
     */
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;

    // ── Default Constructor ────────────────────────────────────────────

    public Department() {
    }

    // ── Getters ────────────────────────────────────────────────────────

    public int getId()                   { return id; }
    public String getName()              { return name; }
    public List<Employee> getEmployees() { return employees; }

    // ── Setters ────────────────────────────────────────────────────────

    public void setId(int id)                           { this.id = id; }
    public void setName(String name)                    { this.name = name; }
    public void setEmployees(List<Employee> employees)  { this.employees = employees; }

    // ── toString (excludes employees to avoid infinite recursion) ──────

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }
}

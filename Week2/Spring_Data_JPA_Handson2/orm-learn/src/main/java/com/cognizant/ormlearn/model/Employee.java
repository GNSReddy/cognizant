package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Employee - JPA Entity mapped to the 'employee' table.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 6
 * Topic: Implement Many-to-Many Relationship
 *
 * Evolution of this entity across the hands-on exercises:
 *   Hands-on 3: plain columns only (departmentId as int)
 *   Hands-on 4: @ManyToOne Department department added
 *   Hands-on 5: no change here (Department got @OneToMany)
 *   Hands-on 6: @ManyToMany List<Skill> skills added  ← NOW
 *
 * @ManyToMany Relationship (Employee side — OWNING SIDE):
 *   One Employee can have many Skills.
 *   One Skill can be assigned to many Employees.
 *   The join table 'employee_skill' maps this relationship.
 *
 * Employee is the OWNING SIDE because it declares @JoinTable.
 * Skill is the INVERSE SIDE (uses mappedBy).
 *
 * The 'employee_skill' table already exists from payroll.sql:
 *   CREATE TABLE employee_skill (
 *       emp_id   INT NOT NULL (FK → employee),
 *       skill_id INT NOT NULL (FK → skill),
 *       PRIMARY KEY (emp_id, skill_id)
 *   );
 */
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emp_id")
    private int id;

    @Column(name = "emp_name")
    private String name;

    @Column(name = "emp_salary")
    private double salary;

    @Column(name = "emp_permanent")
    private boolean permanent;

    @Temporal(TemporalType.DATE)
    @Column(name = "emp_date_of_joining")
    private Date dateOfJoining;

    /**
     * @ManyToOne — carried forward from Hands-on 4.
     * Many Employees belong to ONE Department.
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Department department;

    /**
     * @ManyToMany — Employee OWNING SIDE.
     *
     * @ManyToMany:
     *   Declares that an Employee can have many Skills
     *   and a Skill can be associated with many Employees.
     *   FetchType.LAZY: Skills are NOT loaded when Employee is loaded.
     *   They are loaded only when getSkills() is called
     *   inside an open @Transactional Hibernate session.
     *
     * @JoinTable:
     *   Points to the existing join table 'employee_skill'.
     *   - name           : the join table name
     *   - joinColumns    : the FK column in employee_skill that points to THIS entity (Employee)
     *   - inverseJoinColumns: the FK column in employee_skill that points to the OTHER entity (Skill)
     *
     *   SQL Hibernate uses to load skills for an employee:
     *     SELECT s.skill_id, s.skill_name
     *     FROM skill s
     *     INNER JOIN employee_skill es ON s.skill_id = es.skill_id
     *     WHERE es.emp_id = ?
     */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "employee_skill",
            joinColumns        = @JoinColumn(name = "emp_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    // ── Default Constructor ────────────────────────────────────────────

    public Employee() {
    }

    // ── Getters ────────────────────────────────────────────────────────

    public int getId()                { return id; }
    public String getName()           { return name; }
    public double getSalary()         { return salary; }
    public boolean isPermanent()      { return permanent; }
    public Date getDateOfJoining()    { return dateOfJoining; }
    public Department getDepartment() { return department; }
    public List<Skill> getSkills()    { return skills; }

    // ── Setters ────────────────────────────────────────────────────────

    public void setId(int id)                         { this.id = id; }
    public void setName(String name)                  { this.name = name; }
    public void setSalary(double salary)              { this.salary = salary; }
    public void setPermanent(boolean permanent)       { this.permanent = permanent; }
    public void setDateOfJoining(Date dateOfJoining)  { this.dateOfJoining = dateOfJoining; }
    public void setDepartment(Department department)  { this.department = department; }
    public void setSkills(List<Skill> skills)         { this.skills = skills; }

    // ── toString (excludes skills to avoid lazy-load issues in logging) ─

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", salary=" + salary
                + ", permanent=" + permanent
                + ", dateOfJoining=" + dateOfJoining
                + ", department=" + (department != null ? department.getName() : "null")
                + '}';
    }
}

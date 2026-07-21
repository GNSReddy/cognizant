package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Employee - JPA Entity mapped to the 'employee' table.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 2
 * Topic: @Query annotation on Employee / Department tables
 *
 * Table DDL:
 *   CREATE TABLE employee (
 *       emp_id              INT AUTO_INCREMENT PRIMARY KEY,
 *       emp_name            VARCHAR(100)  NOT NULL,
 *       emp_salary          DECIMAL(10,2) NOT NULL,
 *       emp_permanent       BIT(1)        NOT NULL,
 *       emp_date_of_joining DATE          NOT NULL,
 *       dept_id             INT           NOT NULL  (FK → department)
 *   );
 *
 * @ManyToOne Department:
 *   Enables JPQL JOIN queries like:
 *     SELECT e FROM Employee e WHERE e.department.name = :deptName
 *   Hibernate navigates e.department → joins employee + department table.
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
     * @ManyToOne — required for JPQL join expressions in @Query.
     * e.department.name in JPQL → Hibernate generates:
     *   INNER JOIN department d ON e.dept_id = d.dept_id WHERE d.dept_name = ?
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dept_id")
    private Department department;

    public Employee() { }

    public int getId()              { return id; }
    public String getName()         { return name; }
    public double getSalary()       { return salary; }
    public boolean isPermanent()    { return permanent; }
    public Date getDateOfJoining()  { return dateOfJoining; }
    public Department getDepartment() { return department; }

    public void setId(int id)                        { this.id = id; }
    public void setName(String name)                 { this.name = name; }
    public void setSalary(double salary)             { this.salary = salary; }
    public void setPermanent(boolean permanent)      { this.permanent = permanent; }
    public void setDateOfJoining(Date dateOfJoining) { this.dateOfJoining = dateOfJoining; }
    public void setDepartment(Department department) { this.department = department; }

    @Override
    public String toString() {
        return "Employee{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", salary=" + salary
                + ", permanent=" + permanent
                + ", dept=" + (department != null ? department.getName() : "null")
                + '}';
    }
}

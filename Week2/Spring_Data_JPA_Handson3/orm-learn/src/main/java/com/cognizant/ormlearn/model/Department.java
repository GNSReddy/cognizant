package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Department - JPA Entity mapped to the 'department' table.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 2
 * Topic: @Query annotation on Employee / Department tables
 *
 * Table DDL:
 *   CREATE TABLE department (
 *       dept_id   INT AUTO_INCREMENT PRIMARY KEY,
 *       dept_name VARCHAR(50) NOT NULL
 *   );
 *
 * Used as the parent side of the Employee @ManyToOne relationship.
 * @Query JOIN queries reference e.department.name to filter employees by dept.
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

    public Department() { }

    public int getId()      { return id; }
    public String getName() { return name; }
    public void setId(int id)        { this.id = id; }
    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Department{id=" + id + ", name='" + name + "'}";
    }
}

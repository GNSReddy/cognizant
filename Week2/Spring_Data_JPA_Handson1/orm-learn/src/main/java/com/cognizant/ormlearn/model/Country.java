package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Country - JPA Persistence Class
 *
 * Maps to the 'country' table in the ormlearn MySQL schema.
 *
 * Table DDL:
 *   CREATE TABLE country (
 *       co_code VARCHAR(2)  PRIMARY KEY,
 *       co_name VARCHAR(50) NOT NULL
 *   );
 *
 * Handbook: com.cognizant.ormlearn.model.Country
 *
 * Annotations:
 *   @Entity  - Marks this class as a JPA entity (tells Hibernate to map it to a DB table)
 *   @Table   - Specifies the exact database table name this entity maps to
 *   @Id      - Marks the field as the primary key of the table
 *   @Column  - Maps each Java field to the exact database column name
 *
 * NOTE: Spring Boot 2.7.x uses javax.persistence.* (not jakarta.persistence.*)
 */
@Entity
@Table(name = "country")
public class Country {

    /**
     * Primary key - maps to co_code column (VARCHAR 2)
     * Stores the 2-letter ISO country code e.g., "IN", "US"
     *
     * @Id     - Marks this as the primary key
     * @Column - Maps to the co_code column in country table
     */
    @Id
    @Column(name = "co_code")
    private String code;

    /**
     * Maps to co_name column (VARCHAR 50)
     * Stores the full country name e.g., "India", "United States of America"
     *
     * @Column - Maps to the co_name column in country table
     */
    @Column(name = "co_name")
    private String name;

    // ── Default Constructor (required by JPA/Hibernate) ────────────────

    public Country() {
    }

    // ── Parameterized Constructor ───────────────────────────────────────

    public Country(String code, String name) {
        this.code = code;
        this.name = name;
    }

    // ── Getters ────────────────────────────────────────────────────────

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // ── Setters ────────────────────────────────────────────────────────

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ── toString ───────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Country{"
                + "code='" + code + '\''
                + ", name='" + name + '\''
                + '}';
    }
}

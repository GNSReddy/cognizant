package com.cognizant.ormlearn.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Stock - JPA Entity mapped to the 'stock' table.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 2
 * Topic: Write queries on stock table using Query Methods
 *
 * Table DDL:
 *   CREATE TABLE stock (
 *       id        INT AUTO_INCREMENT PRIMARY KEY,
 *       st_code   VARCHAR(10) NOT NULL,
 *       st_name   VARCHAR(50) NOT NULL,
 *       st_date   DATE        NOT NULL,
 *       st_open   DECIMAL(10,2) NOT NULL,
 *       st_close  DECIMAL(10,2) NOT NULL,
 *       st_volume BIGINT      NOT NULL
 *   );
 *
 * Key Annotations:
 *   @Id + @GeneratedValue(IDENTITY) - Auto-increment primary key
 *   @Temporal(TemporalType.DATE)    - Maps java.util.Date → MySQL DATE column
 *
 * Multiple rows per st_code (same stock on different dates).
 * e.g., FB has 7 rows — one for each trading day.
 */
@Entity
@Table(name = "stock")
public class Stock {

    /**
     * Auto-increment primary key.
     * @GeneratedValue(GenerationType.IDENTITY) lets MySQL assign the id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    /**
     * Stock ticker symbol — maps to st_code column.
     * e.g., "FB", "GOOGL", "NFLX"
     */
    @Column(name = "st_code")
    private String code;

    /**
     * Company full name — maps to st_name column.
     * e.g., "Facebook", "Alphabet Inc", "Netflix"
     */
    @Column(name = "st_name")
    private String name;

    /**
     * Trading date — maps to st_date column (MySQL DATE).
     *
     * @Temporal(TemporalType.DATE):
     *   Instructs Hibernate to store only the DATE portion (yyyy-MM-dd),
     *   ignoring time and timezone components.
     *   Without this, java.util.Date would map to TIMESTAMP (date + time).
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "st_date")
    private Date date;

    /**
     * Opening price for the trading day — maps to st_open column.
     * MySQL DECIMAL(10,2) maps to Java double.
     */
    @Column(name = "st_open")
    private double open;

    /**
     * Closing price for the trading day — maps to st_close column.
     * MySQL DECIMAL(10,2) maps to Java double.
     */
    @Column(name = "st_close")
    private double close;

    /**
     * Volume of shares traded — maps to st_volume column.
     * MySQL BIGINT maps to Java long.
     */
    @Column(name = "st_volume")
    private long volume;

    // ── Default Constructor (required by JPA) ──────────────────────────

    public Stock() {
    }

    // ── Getters ────────────────────────────────────────────────────────

    public int getId()       { return id; }
    public String getCode()  { return code; }
    public String getName()  { return name; }
    public Date getDate()    { return date; }
    public double getOpen()  { return open; }
    public double getClose() { return close; }
    public long getVolume()  { return volume; }

    // ── Setters ────────────────────────────────────────────────────────

    public void setId(int id)          { this.id = id; }
    public void setCode(String code)   { this.code = code; }
    public void setName(String name)   { this.name = name; }
    public void setDate(Date date)     { this.date = date; }
    public void setOpen(double open)   { this.open = open; }
    public void setClose(double close) { this.close = close; }
    public void setVolume(long volume) { this.volume = volume; }

    // ── toString ───────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Stock{"
                + "id=" + id
                + ", code='" + code + '\''
                + ", name='" + name + '\''
                + ", date=" + date
                + ", open=" + open
                + ", close=" + close
                + ", volume=" + volume
                + '}';
    }
}

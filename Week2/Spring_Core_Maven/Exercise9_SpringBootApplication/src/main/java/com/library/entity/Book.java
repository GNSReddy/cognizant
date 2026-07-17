package com.library.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Book Entity class — represents the BOOK table in the H2 in-memory database.
 *
 * JPA Annotations:
 *   @Entity      - Marks this class as a JPA entity (maps to a database table)
 *   @Table       - Specifies the table name in the database (optional; defaults to class name)
 *   @Id          - Marks the field as the primary key
 *   @GeneratedValue - Specifies how the primary key is generated
 *   @Column      - Specifies column properties (name, nullable, length, etc.)
 *
 * Spring Data JPA (via Hibernate) will automatically:
 *   1. Create the BOOK table in H2 on startup (ddl-auto=create-drop)
 *   2. Map each field to a column
 *   3. Handle all SQL operations via BookRepository
 */
@Entity
@Table(name = "BOOK")
public class Book {

    /**
     * @Id — This field is the primary key of the BOOK table.
     *
     * @GeneratedValue(strategy = GenerationType.IDENTITY)
     *   — The database auto-increments the ID value.
     *   Other strategies:
     *     AUTO     - JPA selects the strategy (default)
     *     SEQUENCE - Uses a database sequence object
     *     TABLE    - Uses a separate key generation table
     *     IDENTITY - Uses database auto-increment column
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * @Column(name = "TITLE", nullable = false, length = 200)
     *   - Maps this field to the TITLE column
     *   - nullable = false → NOT NULL constraint in the database
     *   - length = 200 → maximum character length
     */
    @Column(name = "TITLE", nullable = false, length = 200)
    private String title;

    /**
     * Maps to the AUTHOR column — cannot be null.
     */
    @Column(name = "AUTHOR", nullable = false, length = 100)
    private String author;

    /**
     * Maps to the ISBN column — must be unique.
     */
    @Column(name = "ISBN", unique = true, length = 20)
    private String isbn;

    /**
     * Maps to the PRICE column.
     */
    @Column(name = "PRICE")
    private Double price;

    /**
     * Default no-argument constructor.
     * Required by JPA specification — Hibernate needs it to create entity instances.
     */
    public Book() {
    }

    /**
     * Parameterized constructor for creating Book instances easily.
     *
     * @param title  the book title
     * @param author the book author
     * @param isbn   the ISBN number
     * @param price  the book price
     */
    public Book(String title, String author, String isbn, Double price) {
        this.title  = title;
        this.author = author;
        this.isbn   = isbn;
        this.price  = price;
    }

    // ── Getters ──────────────────────────────────────────────────────────

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public Double getPrice() {
        return price;
    }

    // ── Setters ──────────────────────────────────────────────────────────

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    // ── toString ─────────────────────────────────────────────────────────

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", title='" + title + '\''
                + ", author='" + author + '\''
                + ", isbn='" + isbn + '\''
                + ", price=" + price
                + '}';
    }
}

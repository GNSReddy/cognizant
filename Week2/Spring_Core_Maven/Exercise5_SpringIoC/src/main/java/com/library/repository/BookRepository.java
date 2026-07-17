package com.library.repository;

import java.util.Arrays;
import java.util.List;

/**
 * BookRepository class represents the data access layer for Book entities.
 * 
 * This bean is managed by the Spring IoC Container.
 * The container controls:
 *   - When this object is created (instantiation)
 *   - How many instances exist (scope: singleton = 1 instance)
 *   - Lifecycle callbacks (init-method, destroy-method)
 *   - When this object is destroyed (container shutdown)
 */
public class BookRepository {

    /**
     * Default constructor - called by the IoC container during bean instantiation.
     */
    public BookRepository() {
        System.out.println("[IoC] BookRepository: Constructor called - Bean instantiated by IoC Container.");
    }

    /**
     * Initialization callback method.
     * Called by the IoC container AFTER the bean is instantiated and
     * all properties are set (configured via init-method="init" in XML).
     */
    public void init() {
        System.out.println("[IoC] BookRepository: init() called - Bean initialization complete.");
        System.out.println("[IoC] BookRepository: Database connection simulated.");
    }

    /**
     * Destruction callback method.
     * Called by the IoC container when the container is shutting down
     * (configured via destroy-method="cleanup" in XML).
     */
    public void cleanup() {
        System.out.println("[IoC] BookRepository: cleanup() called - Releasing resources.");
        System.out.println("[IoC] BookRepository: Database connection closed.");
    }

    /**
     * Simulates fetching all books from the data source.
     * @return a list of book titles
     */
    public List<String> findAllBooks() {
        System.out.println("BookRepository: Finding all books in the library...");
        List<String> books = Arrays.asList(
            "Spring in Action",
            "Effective Java",
            "Clean Code",
            "Head First Design Patterns",
            "Java Concurrency in Practice"
        );
        System.out.println("BookRepository: Returned " + books.size() + " books from the database.");
        return books;
    }

    /**
     * Simulates saving a book to the data source.
     * @param bookName the name of the book to save
     */
    public void saveBook(String bookName) {
        System.out.println("BookRepository: Saving book '" + bookName + "' to the database.");
        System.out.println("BookRepository: Book '" + bookName + "' saved successfully.");
    }
}

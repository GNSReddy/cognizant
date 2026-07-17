package com.library.repository;

import java.util.Arrays;
import java.util.List;

/**
 * BookRepository class represents the data access layer for Book entities.
 * This class is managed by the Spring IoC container as a bean.
 * Methods in this class will be intercepted by the LoggingAspect
 * to log execution times.
 */
public class BookRepository {

    /**
     * Simulates fetching all books from the data source.
     * A small delay is added to simulate database access time.
     * @return a list of book titles
     */
    public List<String> findAllBooks() {
        System.out.println("BookRepository: Finding all books in the library...");
        // Simulate database query delay
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
     * A small delay is added to simulate database write time.
     * @param bookName the name of the book to save
     */
    public void saveBook(String bookName) {
        System.out.println("BookRepository: Saving book '" + bookName + "' to the database.");
        // Simulate database write delay
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("BookRepository: Book '" + bookName + "' saved successfully.");
    }
}

package com.library.repository;

/**
 * BookRepository class represents the data access layer for Book entities.
 * This class is managed by the Spring IoC container as a bean.
 * It simulates repository operations for the library management system.
 */
public class BookRepository {

    /**
     * Simulates fetching all books from the data source.
     * In a real application, this would query a database.
     */
    public void findAllBooks() {
        System.out.println("BookRepository: Finding all books in the library...");
        System.out.println("BookRepository: Returned 5 books from the database.");
    }

    /**
     * Simulates saving a book to the data source.
     * @param bookName the name of the book to save
     */
    public void saveBook(String bookName) {
        System.out.println("BookRepository: Saving book '" + bookName + "' to the database.");
    }
}

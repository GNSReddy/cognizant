package com.library.repository;

import java.util.Arrays;
import java.util.List;

/**
 * BookRepository class represents the data access layer for Book entities.
 * This bean is injected into BookService via both constructor and setter injection.
 */
public class BookRepository {

    /**
     * Default constructor.
     */
    public BookRepository() {
        System.out.println("[IoC] BookRepository: Instantiated by Spring IoC Container.");
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

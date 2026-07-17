package com.library.service;

/**
 * BookService class represents the business logic layer for Book operations.
 * This class is managed by the Spring IoC container as a bean.
 * It provides service-level methods for the library management system.
 */
public class BookService {

    /**
     * Handles the business logic for retrieving all books.
     * In this exercise, it demonstrates that the bean is loaded by the Spring context.
     */
    public void getAllBooks() {
        System.out.println("BookService: Getting all books from the library...");
    }

    /**
     * Handles the business logic for adding a new book.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Adding book '" + bookName + "' to the library.");
    }
}

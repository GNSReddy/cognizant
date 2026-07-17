package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class represents the business logic layer for Book operations.
 * BookRepository is injected via Setter Injection.
 * Methods in this class will be intercepted by the LoggingAspect
 * to log execution times.
 */
public class BookService {

    // Dependency: injected by Spring via setter injection
    private BookRepository bookRepository;

    /**
     * Setter method for BookRepository (Setter Injection).
     * @param bookRepository the BookRepository instance to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books by delegating to BookRepository.
     * This method will be intercepted by LoggingAspect to log execution time.
     */
    public void getAllBooks() {
        System.out.println("BookService: Delegating to BookRepository to get all books...");
        List<String> books = bookRepository.findAllBooks();
        System.out.println("BookService: Received " + books.size() + " books:");
        for (String book : books) {
            System.out.println("  -> " + book);
        }
    }

    /**
     * Adds a book by delegating to BookRepository.
     * This method will be intercepted by LoggingAspect to log execution time.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Delegating to BookRepository to save the book...");
        bookRepository.saveBook(bookName);
    }
}

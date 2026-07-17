package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class represents the business logic layer for Book operations.
 * 
 * This bean is managed by the Spring IoC Container.
 * The IoC container:
 *   1. Creates this bean using the default constructor
 *   2. Injects BookRepository via the setter method (Setter Injection)
 *   3. Calls init() method after all dependencies are injected
 *   4. Calls cleanup() method when the container shuts down
 * 
 * The key principle of IoC: BookService does NOT create BookRepository.
 * Instead, the container INJECTS it — control is INVERTED.
 */
public class BookService {

    // Dependency managed by the IoC container
    private BookRepository bookRepository;

    /**
     * Default constructor - called by the IoC container during bean instantiation.
     */
    public BookService() {
        System.out.println("[IoC] BookService: Constructor called - Bean instantiated by IoC Container.");
    }

    /**
     * Setter method for BookRepository.
     * The IoC container calls this method to inject the BookRepository dependency.
     * This is configured via <property name="bookRepository" ref="bookRepository" />
     * in applicationContext.xml.
     * 
     * @param bookRepository the BookRepository instance injected by the IoC container
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[IoC] BookService: setBookRepository() called - Dependency Injected by IoC Container.");
    }

    /**
     * Getter method for BookRepository.
     * @return the injected BookRepository instance
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Initialization callback method.
     * Called by the IoC container AFTER constructor AND setter injection are complete.
     * This is the place to perform any custom initialization logic.
     */
    public void init() {
        System.out.println("[IoC] BookService: init() called - Bean initialization complete.");
        System.out.println("[IoC] BookService: Ready to serve requests.");
    }

    /**
     * Destruction callback method.
     * Called by the IoC container during container shutdown.
     * This is the place to release resources (close connections, etc.).
     */
    public void cleanup() {
        System.out.println("[IoC] BookService: cleanup() called - Releasing resources.");
    }

    /**
     * Retrieves all books by delegating to the injected BookRepository.
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
     * Adds a book by delegating to the injected BookRepository.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Delegating to BookRepository to save the book...");
        bookRepository.saveBook(bookName);
    }
}

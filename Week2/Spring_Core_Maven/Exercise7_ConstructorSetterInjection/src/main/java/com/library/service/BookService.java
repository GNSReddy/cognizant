package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class demonstrates BOTH Constructor Injection and Setter Injection.
 * 
 * This class has:
 *   1. A parameterized CONSTRUCTOR for Constructor Injection
 *   2. SETTER methods for Setter Injection
 *   3. A default constructor (needed for the setter-injection bean)
 * 
 * Two beans are configured in applicationContext.xml:
 *   - "bookServiceConstructor" → uses <constructor-arg> (Constructor Injection)
 *   - "bookServiceSetter"      → uses <property> (Setter Injection)
 * 
 * Constructor Injection:
 *   - Dependencies passed via constructor parameters
 *   - Bean is FULLY INITIALIZED when created
 *   - Dependencies are MANDATORY (no default constructor fallback)
 *   - Supports IMMUTABILITY (fields can be final)
 * 
 * Setter Injection:
 *   - Dependencies set via setter methods AFTER construction
 *   - Bean may be PARTIALLY INITIALIZED before setters run
 *   - Dependencies are OPTIONAL (setter may not be called)
 *   - Allows RECONFIGURATION (setter can be called again)
 */
public class BookService {

    private BookRepository bookRepository;
    private String serviceName;

    /**
     * DEFAULT CONSTRUCTOR (no-arg).
     * Used by the IoC container for SETTER INJECTION.
     * The container first creates the bean with this constructor,
     * then calls setter methods to inject dependencies.
     */
    public BookService() {
        System.out.println("[SETTER INJECTION] BookService: Default constructor called.");
        this.serviceName = "DefaultService";
    }

    /**
     * PARAMETERIZED CONSTRUCTOR.
     * Used by the IoC container for CONSTRUCTOR INJECTION.
     * The container passes dependencies directly through this constructor.
     * The bean is fully initialized when this constructor returns.
     * 
     * Configured in XML via:
     *   <constructor-arg ref="bookRepository" />
     *   <constructor-arg value="ConstructorInjectedService" />
     * 
     * @param bookRepository the BookRepository dependency injected via constructor
     * @param serviceName the service name injected as a simple value
     */
    public BookService(BookRepository bookRepository, String serviceName) {
        this.bookRepository = bookRepository;
        this.serviceName = serviceName;
        System.out.println("[CONSTRUCTOR INJECTION] BookService: Parameterized constructor called.");
        System.out.println("[CONSTRUCTOR INJECTION] BookRepository injected: " + (bookRepository != null));
        System.out.println("[CONSTRUCTOR INJECTION] Service name: " + serviceName);
    }

    /**
     * SETTER for BookRepository.
     * Used by the IoC container for SETTER INJECTION.
     * 
     * Configured in XML via:
     *   <property name="bookRepository" ref="bookRepository" />
     * 
     * @param bookRepository the BookRepository dependency injected via setter
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[SETTER INJECTION] BookService: setBookRepository() called - BookRepository injected.");
    }

    /**
     * SETTER for serviceName.
     * Demonstrates setter injection of a simple String value.
     * 
     * Configured in XML via:
     *   <property name="serviceName" value="SetterInjectedService" />
     * 
     * @param serviceName the service name injected via setter
     */
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
        System.out.println("[SETTER INJECTION] BookService: setServiceName() called - Value: " + serviceName);
    }

    /**
     * Getter for BookRepository.
     * @return the injected BookRepository instance
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Getter for serviceName.
     * @return the service name
     */
    public String getServiceName() {
        return serviceName;
    }

    /**
     * Retrieves all books by delegating to BookRepository.
     */
    public void getAllBooks() {
        System.out.println("[" + serviceName + "] Delegating to BookRepository to get all books...");
        List<String> books = bookRepository.findAllBooks();
        System.out.println("[" + serviceName + "] Received " + books.size() + " books:");
        for (String book : books) {
            System.out.println("  -> " + book);
        }
    }

    /**
     * Adds a book by delegating to BookRepository.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("[" + serviceName + "] Delegating to BookRepository to save...");
        bookRepository.saveBook(bookName);
    }
}

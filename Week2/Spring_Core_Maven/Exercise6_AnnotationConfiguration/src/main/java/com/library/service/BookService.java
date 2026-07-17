package com.library.service;

import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * BookService class represents the business logic layer for Book operations.
 * 
 * @Service is a Spring stereotype annotation that:
 *   1. Marks this class as a Spring-managed bean (auto-detected by component scanning)
 *   2. Indicates this class belongs to the BUSINESS LOGIC LAYER (service)
 *   3. The bean id defaults to "bookService" (class name with lowercase first letter)
 *   4. Semantically identical to @Component but conveys the class's role
 * 
 * @Autowired is used for automatic dependency injection:
 *   - Spring finds a bean of the matching type (BookRepository)
 *   - Injects it automatically without needing XML <property> configuration
 *   - Can be placed on: setter methods, constructors, or fields
 */
@Service
public class BookService {

    // Dependency: BookRepository will be auto-injected by Spring
    private BookRepository bookRepository;

    /**
     * Constructor - called by the IoC container during component scanning.
     */
    public BookService() {
        System.out.println("[ANNOTATION] BookService: Instantiated by Spring via @Service annotation.");
    }

    /**
     * Setter method for BookRepository with @Autowired.
     * 
     * @Autowired tells Spring to automatically inject the BookRepository bean
     * by calling this setter method. Spring matches by TYPE — it finds a bean
     * of type BookRepository in the container and injects it here.
     * 
     * This REPLACES the XML configuration:
     *   <property name="bookRepository" ref="bookRepository" />
     * 
     * @param bookRepository the BookRepository instance auto-injected by Spring
     */
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("[ANNOTATION] BookService: BookRepository auto-injected via @Autowired.");
    }

    /**
     * Getter method for BookRepository.
     * @return the injected BookRepository instance
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Retrieves all books by delegating to the auto-injected BookRepository.
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
     * Adds a book by delegating to the auto-injected BookRepository.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Delegating to BookRepository to save the book...");
        bookRepository.saveBook(bookName);
    }
}

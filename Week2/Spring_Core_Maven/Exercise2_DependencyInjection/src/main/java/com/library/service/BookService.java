package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class represents the business logic layer for Book operations.
 * This class is managed by the Spring IoC container as a bean.
 * 
 * In this exercise, BookRepository is INJECTED into BookService via
 * Setter-based Dependency Injection configured in applicationContext.xml.
 */
public class BookService {

    // Dependency: BookRepository is injected by the Spring IoC container
    private BookRepository bookRepository;

    /**
     * Setter method for BookRepository.
     * Spring calls this method automatically during bean initialization
     * because of the <property name="bookRepository" ref="bookRepository" />
     * tag in applicationContext.xml.
     * 
     * This is SETTER INJECTION — Spring injects the dependency by calling
     * this setter method after constructing the BookService object.
     * 
     * @param bookRepository the BookRepository instance to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository has been injected via SETTER INJECTION.");
    }

    /**
     * Getter method for BookRepository.
     * @return the injected BookRepository instance
     */
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    /**
     * Delegates to BookRepository to retrieve all books.
     * This demonstrates that the dependency injection is working —
     * BookService can use BookRepository without creating it manually.
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
     * Delegates to BookRepository to save a book.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Delegating to BookRepository to save the book...");
        bookRepository.saveBook(bookName);
    }
}

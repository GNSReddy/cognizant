package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class represents the business logic layer.
 * Methods in this class are intercepted by LoggingAspect via:
 *   - @Before  (logs method name and args before execution)
 *   - @After   (logs method completion after execution)
 */
public class BookService {

    // Injected by Spring via setter injection
    private BookRepository bookRepository;

    /**
     * Setter for BookRepository — called by Spring IoC container.
     * @param bookRepository the BookRepository dependency to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Retrieves all books by delegating to BookRepository.
     * Intercepted by @Before and @After advice in LoggingAspect.
     */
    public void getAllBooks() {
        System.out.println("BookService: Requesting all books from repository...");
        List<String> books = bookRepository.findAllBooks();
        System.out.println("BookService: Displaying " + books.size() + " books:");
        for (String book : books) {
            System.out.println("  -> " + book);
        }
    }

    /**
     * Adds a new book via BookRepository.
     * Intercepted by @Before and @After advice in LoggingAspect.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Requesting repository to save book...");
        bookRepository.saveBook(bookName);
    }

    /**
     * Simulates a method that throws an exception.
     * Used to demonstrate @AfterThrowing advice.
     * @param bookId the ID of the book to delete
     */
    public void deleteBook(int bookId) {
        System.out.println("BookService: Attempting to delete book with ID: " + bookId);
        if (bookId <= 0) {
            throw new IllegalArgumentException("Book ID must be positive. Received: " + bookId);
        }
        System.out.println("BookService: Book with ID " + bookId + " deleted successfully.");
    }
}

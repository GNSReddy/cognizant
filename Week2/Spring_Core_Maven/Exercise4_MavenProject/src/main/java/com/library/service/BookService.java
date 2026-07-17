package com.library.service;

import com.library.repository.BookRepository;
import java.util.List;

/**
 * BookService class represents the business logic layer.
 * BookRepository is injected via Setter Injection.
 */
public class BookService {

    private BookRepository bookRepository;

    /**
     * Setter method for BookRepository (Setter Injection).
     * @param bookRepository the BookRepository instance to inject
     */
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        System.out.println("BookService: BookRepository has been injected via Setter Injection.");
    }

    /**
     * Retrieves all books by delegating to BookRepository.
     */
    public void getAllBooks() {
        System.out.println("BookService: Delegating to BookRepository...");
        List<String> books = bookRepository.findAllBooks();
        System.out.println("BookService: Received " + books.size() + " books:");
        for (String book : books) {
            System.out.println("  -> " + book);
        }
    }

    /**
     * Adds a book by delegating to BookRepository.
     * @param bookName the name of the book to add
     */
    public void addBook(String bookName) {
        System.out.println("BookService: Delegating to BookRepository to save...");
        bookRepository.saveBook(bookName);
    }
}

package com.library.repository;

import org.springframework.stereotype.Repository;
import java.util.Arrays;
import java.util.List;

/**
 * BookRepository class represents the data access layer for Book entities.
 * 
 * @Repository is a Spring stereotype annotation that:
 *   1. Marks this class as a Spring-managed bean (auto-detected by component scanning)
 *   2. Indicates this class belongs to the DATA ACCESS LAYER (persistence)
 *   3. Enables automatic exception translation (DataAccessException)
 *   4. The bean id defaults to "bookRepository" (class name with lowercase first letter)
 * 
 * @Repository is a specialization of @Component:
 *   @Component → generic bean
 *   @Repository → data access bean (adds exception translation)
 *   @Service → business logic bean
 *   @Controller → web/presentation bean
 */
@Repository
public class BookRepository {

    /**
     * Constructor - called by the IoC container during component scanning.
     */
    public BookRepository() {
        System.out.println("[ANNOTATION] BookRepository: Instantiated by Spring via @Repository annotation.");
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

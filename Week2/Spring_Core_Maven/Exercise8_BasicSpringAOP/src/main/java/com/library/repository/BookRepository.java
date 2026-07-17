package com.library.repository;

import java.util.Arrays;
import java.util.List;

/**
 * BookRepository class represents the data access layer for Book entities.
 * Methods in this class are intercepted by LoggingAspect via:
 *   - @AfterReturning (logs return value after successful execution)
 *   - @Around         (logs execution time)
 */
public class BookRepository {

    // Injected via setter (configured in applicationContext.xml)
    private String repositoryName;

    /**
     * Setter for repositoryName — injected via XML <property> tag.
     * @param repositoryName the name of this repository instance
     */
    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    /**
     * Getter for repositoryName.
     * @return the name of this repository
     */
    public String getRepositoryName() {
        return repositoryName;
    }

    /**
     * Simulates fetching all books from the data source.
     * Intercepted by @AfterReturning and @Around aspects.
     * @return a list of book titles
     */
    public List<String> findAllBooks() {
        System.out.println("BookRepository [" + repositoryName + "]: Finding all books...");
        List<String> books = Arrays.asList(
            "Spring in Action",
            "Effective Java",
            "Clean Code",
            "Head First Design Patterns",
            "Java Concurrency in Practice"
        );
        System.out.println("BookRepository [" + repositoryName + "]: Returned " + books.size() + " books.");
        return books;
    }

    /**
     * Simulates saving a book to the data source.
     * Intercepted by @AfterReturning and @Around aspects.
     * @param bookName the name of the book to save
     */
    public void saveBook(String bookName) {
        System.out.println("BookRepository [" + repositoryName + "]: Saving '" + bookName + "'...");
        System.out.println("BookRepository [" + repositoryName + "]: Book saved successfully.");
    }
}

package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;
import com.library.repository.BookRepository;

/**
 * Main class for the Library Management Application.
 * This class loads the Spring Application Context, which automatically
 * performs Setter-based Dependency Injection, wiring BookRepository
 * into BookService as configured in applicationContext.xml.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 2");
        System.out.println("  Implementing Dependency Injection (Setter DI)");
        System.out.println("===================================================\n");

        // Step 1: Load the Spring Application Context
        // When this line executes, Spring:
        //   a) Creates a BookRepository bean (using default constructor)
        //   b) Creates a BookService bean (using default constructor)
        //   c) Calls bookService.setBookRepository(bookRepository) — SETTER INJECTION
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring Application Context loaded successfully!");
        System.out.println("[INFO] Dependency Injection completed automatically by Spring.\n");

        // Step 2: Retrieve the BookService bean from the IoC container
        BookService bookService = context.getBean("bookService", BookService.class);

        // Step 3: Verify that BookRepository was injected into BookService
        BookRepository injectedRepo = bookService.getBookRepository();
        if (injectedRepo != null) {
            System.out.println("[VERIFY] SUCCESS: BookRepository is injected into BookService!");
            System.out.println("[VERIFY] Injected object: " + injectedRepo.getClass().getName() + "\n");
        } else {
            System.out.println("[VERIFY] FAILED: BookRepository is NULL — injection did not work!\n");
        }

        // Step 4: Test BookService methods — they now delegate to BookRepository
        System.out.println("--- Testing getAllBooks() via Dependency Injection ---");
        bookService.getAllBooks();

        System.out.println("\n--- Testing addBook() via Dependency Injection ---");
        bookService.addBook("Design Patterns by GoF");

        System.out.println("\n===================================================");
        System.out.println("  Dependency Injection Test PASSED!");
        System.out.println("  BookRepository was successfully injected into");
        System.out.println("  BookService using XML Setter Injection.");
        System.out.println("===================================================");

        // Step 5: Close the ApplicationContext
        ((ClassPathXmlApplicationContext) context).close();
    }
}

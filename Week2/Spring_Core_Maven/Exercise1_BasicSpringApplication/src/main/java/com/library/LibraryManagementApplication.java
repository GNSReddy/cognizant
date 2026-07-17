package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;
import com.library.repository.BookRepository;

/**
 * Main class for the Library Management Application.
 * This class loads the Spring Application Context from the XML configuration file,
 * retrieves beans from the IoC container, and invokes their methods to test the configuration.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 1");
        System.out.println("  Configuring a Basic Spring Application");
        System.out.println("===================================================");

        // Step 1: Load the Spring Application Context from the XML configuration file
        // ClassPathXmlApplicationContext reads the XML config from src/main/resources
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring Application Context loaded successfully!\n");

        // Step 2: Retrieve the BookService bean from the Spring IoC container
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println("[INFO] BookService bean retrieved from Spring IoC container.");

        // Step 3: Retrieve the BookRepository bean from the Spring IoC container
        BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);
        System.out.println("[INFO] BookRepository bean retrieved from Spring IoC container.");

        System.out.println("\n--- Testing BookService ---");
        // Step 4: Test BookService methods
        bookService.getAllBooks();
        bookService.addBook("Spring in Action");

        System.out.println("\n--- Testing BookRepository ---");
        // Step 5: Test BookRepository methods
        bookRepository.findAllBooks();
        bookRepository.saveBook("Effective Java");

        System.out.println("\n===================================================");
        System.out.println("  Spring Application Context Test PASSED!");
        System.out.println("===================================================");

        // Step 6: Close the ApplicationContext to release resources
        ((ClassPathXmlApplicationContext) context).close();
    }
}

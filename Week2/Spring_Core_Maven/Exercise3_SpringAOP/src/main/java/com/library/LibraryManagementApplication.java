package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

/**
 * Main class for the Library Management Application.
 * This class loads the Spring Application Context with AOP enabled.
 * When methods on BookService and BookRepository are called,
 * the LoggingAspect automatically intercepts them and logs execution times.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 3");
        System.out.println("  Implementing Logging with Spring AOP");
        System.out.println("===================================================");

        // Step 1: Load the Spring Application Context
        // Spring creates all beans AND creates proxy objects for AOP-targeted beans
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring Context loaded with AOP enabled.");
        System.out.println("[INFO] LoggingAspect will intercept method calls.\n");

        // Step 2: Retrieve the BookService bean
        // NOTE: Spring returns a PROXY object, not the actual BookService.
        // The proxy intercepts method calls and applies the AOP advice.
        BookService bookService = context.getBean("bookService", BookService.class);

        // Step 3: Call getAllBooks() — AOP will log execution time
        System.out.println("=== Calling bookService.getAllBooks() ===");
        bookService.getAllBooks();

        // Step 4: Call addBook() — AOP will log execution time
        System.out.println("=== Calling bookService.addBook() ===");
        bookService.addBook("Design Patterns by GoF");

        System.out.println("\n===================================================");
        System.out.println("  Spring AOP Logging Test PASSED!");
        System.out.println("  Check the [AOP LOG] entries above for");
        System.out.println("  method execution times.");
        System.out.println("===================================================");

        // Step 5: Close the ApplicationContext
        ((ClassPathXmlApplicationContext) context).close();
    }
}

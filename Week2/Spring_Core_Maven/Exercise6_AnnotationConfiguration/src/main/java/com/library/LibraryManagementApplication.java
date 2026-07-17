package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;
import com.library.repository.BookRepository;

/**
 * Main class for the Library Management Application.
 * 
 * This exercise demonstrates Annotation-Based Configuration:
 *   - @Repository on BookRepository (replaces <bean> in XML)
 *   - @Service on BookService (replaces <bean> in XML)
 *   - @Autowired on setter method (replaces <property> in XML)
 *   - <context:component-scan> in XML (enables auto-detection)
 * 
 * Comparison: XML Configuration vs Annotation Configuration
 * ──────────────────────────────────────────────────────────
 * XML (Previous exercises):
 *   <bean id="bookRepository" class="com.library.repository.BookRepository" />
 *   <bean id="bookService" class="com.library.service.BookService">
 *       <property name="bookRepository" ref="bookRepository" />
 *   </bean>
 * 
 * Annotation (This exercise):
 *   @Repository on BookRepository
 *   @Service on BookService
 *   @Autowired on setBookRepository()
 *   <context:component-scan base-package="com.library" />
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 6");
        System.out.println("  Configuring Beans with Annotations");
        System.out.println("===================================================\n");

        // Load the Spring Application Context
        // component-scan automatically detects @Service and @Repository classes
        System.out.println("[INFO] Loading Spring Context with Component Scanning...\n");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring Context loaded successfully with annotations!\n");

        // Display all beans discovered by component scanning
        System.out.println("--- Beans discovered by Component Scanning ---");
        String[] beanNames = context.getBeanDefinitionNames();
        System.out.println("Total beans registered: " + beanNames.length);
        for (String beanName : beanNames) {
            System.out.println("  -> Bean: " + beanName
                    + " | Type: " + context.getBean(beanName).getClass().getSimpleName());
        }

        // Retrieve BookService bean (auto-registered with id "bookService")
        System.out.println("\n--- Retrieving Beans ---");
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println("[VERIFY] BookService bean retrieved successfully.");

        // Retrieve BookRepository bean (auto-registered with id "bookRepository")
        BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);
        System.out.println("[VERIFY] BookRepository bean retrieved successfully.");

        // Verify @Autowired injection
        System.out.println("\n--- Verifying @Autowired Injection ---");
        boolean isInjected = bookService.getBookRepository() != null;
        System.out.println("[VERIFY] BookRepository injected into BookService via @Autowired? " + isInjected);
        boolean isSameInstance = bookService.getBookRepository() == bookRepository;
        System.out.println("[VERIFY] Same instance (singleton)? " + isSameInstance);

        // Test service methods
        System.out.println("\n--- Testing getAllBooks() ---");
        bookService.getAllBooks();

        System.out.println("\n--- Testing addBook() ---");
        bookService.addBook("Spring Annotations in Action");

        System.out.println("\n===================================================");
        System.out.println("  Annotation-Based Configuration Test PASSED!");
        System.out.println("  @Repository, @Service, @Autowired all working.");
        System.out.println("  No <bean> definitions needed in XML!");
        System.out.println("===================================================");

        // Close the context
        ((ClassPathXmlApplicationContext) context).close();
    }
}

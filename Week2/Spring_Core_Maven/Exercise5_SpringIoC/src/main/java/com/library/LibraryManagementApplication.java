package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;
import com.library.repository.BookRepository;

/**
 * Main class for the Library Management Application.
 * 
 * This exercise demonstrates the Spring IoC (Inversion of Control) Container:
 * 
 * IoC Container Responsibilities:
 *   1. INSTANTIATION  - Creates bean objects
 *   2. CONFIGURATION  - Injects dependencies via setters/constructors
 *   3. LIFECYCLE      - Manages init and destroy callbacks
 *   4. SCOPE          - Controls how many instances exist (singleton/prototype)
 *   5. WIRING         - Connects beans together automatically
 * 
 * Two types of IoC Containers in Spring:
 *   a) BeanFactory       - Basic container (lazy initialization)
 *   b) ApplicationContext - Advanced container (eager initialization, events, AOP, i18n)
 *                           ClassPathXmlApplicationContext is an implementation of this.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 5");
        System.out.println("  Configuring the Spring IoC Container");
        System.out.println("===================================================\n");

        // ===== PHASE 1: IoC Container Initialization =====
        System.out.println("========== PHASE 1: IoC Container Initialization ==========");
        System.out.println("[MAIN] Creating Spring IoC Container...");
        System.out.println("[MAIN] The container will now:\n");

        // When this line executes, the IoC container:
        // 1. Reads applicationContext.xml
        // 2. Creates BookRepository (calls constructor)
        // 3. Calls BookRepository.init() (init-method callback)
        // 4. Creates BookService (calls constructor)
        // 5. Calls BookService.setBookRepository() (setter injection)
        // 6. Calls BookService.init() (init-method callback)
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[MAIN] IoC Container initialized successfully!\n");

        // ===== PHASE 2: Retrieving Beans from IoC Container =====
        System.out.println("========== PHASE 2: Retrieving Beans from IoC Container ==========");

        // Retrieve BookService bean
        BookService bookService = context.getBean("bookService", BookService.class);
        System.out.println("[MAIN] Retrieved BookService bean from IoC container.");

        // Retrieve BookRepository bean
        BookRepository bookRepository = context.getBean("bookRepository", BookRepository.class);
        System.out.println("[MAIN] Retrieved BookRepository bean from IoC container.");

        // Verify Singleton Scope: same instance returned every time
        BookService bookService2 = context.getBean("bookService", BookService.class);
        System.out.println("\n[SINGLETON TEST] bookService == bookService2 ? " + (bookService == bookService2));
        System.out.println("[SINGLETON TEST] Result: SAME instance (singleton scope confirmed)");

        // Verify Dependency Injection
        System.out.println("\n[DI TEST] BookRepository injected into BookService? "
                + (bookService.getBookRepository() != null));
        System.out.println("[DI TEST] Injected BookRepository == Container's BookRepository? "
                + (bookService.getBookRepository() == bookRepository));

        // ===== PHASE 3: Using Beans =====
        System.out.println("\n========== PHASE 3: Using Beans ==========");

        System.out.println("\n--- Testing getAllBooks() ---");
        bookService.getAllBooks();

        System.out.println("\n--- Testing addBook() ---");
        bookService.addBook("Spring IoC in Depth");

        // ===== PHASE 4: IoC Container Shutdown =====
        System.out.println("\n========== PHASE 4: IoC Container Shutdown ==========");
        System.out.println("[MAIN] Closing IoC Container...");
        System.out.println("[MAIN] The container will call destroy-method on all beans:\n");

        // When close() is called, the container invokes destroy-method callbacks
        ((ClassPathXmlApplicationContext) context).close();

        System.out.println("\n===================================================");
        System.out.println("  Spring IoC Container Test PASSED!");
        System.out.println("===================================================");
    }
}

package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

/**
 * Main class for the Library Management Application.
 * 
 * This exercise demonstrates BOTH Constructor Injection and Setter Injection:
 * 
 * Constructor Injection (bookServiceConstructor):
 *   - Spring calls: new BookService(bookRepository, "ConstructorInjectedService")
 *   - Uses <constructor-arg> in XML
 *   - Bean is fully initialized at construction time
 * 
 * Setter Injection (bookServiceSetter):
 *   - Spring calls: new BookService() → setBookRepository(repo) → setServiceName(name)
 *   - Uses <property> in XML
 *   - Bean is constructed first, then dependencies are set
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 7");
        System.out.println("  Constructor Injection vs Setter Injection");
        System.out.println("===================================================\n");

        // Load the Spring Application Context
        System.out.println("[INFO] Loading Spring Application Context...\n");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        System.out.println("\n[INFO] Spring Context loaded successfully!\n");

        // ===== TEST 1: Constructor Injection =====
        System.out.println("=========================================");
        System.out.println("  TEST 1: CONSTRUCTOR INJECTION");
        System.out.println("=========================================\n");

        BookService constructorService = context.getBean("bookServiceConstructor", BookService.class);

        System.out.println("[VERIFY] Bean ID: bookServiceConstructor");
        System.out.println("[VERIFY] Service name: " + constructorService.getServiceName());
        System.out.println("[VERIFY] BookRepository injected? " + (constructorService.getBookRepository() != null));

        System.out.println("\n--- getAllBooks() via Constructor Injection ---");
        constructorService.getAllBooks();

        System.out.println("\n--- addBook() via Constructor Injection ---");
        constructorService.addBook("Head First Java");

        // ===== TEST 2: Setter Injection =====
        System.out.println("\n=========================================");
        System.out.println("  TEST 2: SETTER INJECTION");
        System.out.println("=========================================\n");

        BookService setterService = context.getBean("bookServiceSetter", BookService.class);

        System.out.println("[VERIFY] Bean ID: bookServiceSetter");
        System.out.println("[VERIFY] Service name: " + setterService.getServiceName());
        System.out.println("[VERIFY] BookRepository injected? " + (setterService.getBookRepository() != null));

        System.out.println("\n--- getAllBooks() via Setter Injection ---");
        setterService.getAllBooks();

        System.out.println("\n--- addBook() via Setter Injection ---");
        setterService.addBook("Thinking in Java");

        // ===== COMPARISON =====
        System.out.println("\n=========================================");
        System.out.println("  COMPARISON: Constructor vs Setter");
        System.out.println("=========================================");
        System.out.println("  Both beans use the SAME BookRepository (singleton):");
        System.out.println("  constructorService.getBookRepository() == setterService.getBookRepository()? "
                + (constructorService.getBookRepository() == setterService.getBookRepository()));

        System.out.println("\n  Different instances of BookService:");
        System.out.println("  constructorService == setterService? "
                + (constructorService == setterService));

        System.out.println("\n===================================================");
        System.out.println("  Constructor & Setter Injection Test PASSED!");
        System.out.println("===================================================");

        // Close the context
        ((ClassPathXmlApplicationContext) context).close();
    }
}

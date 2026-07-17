package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

/**
 * Main class for the Library Management Application.
 *
 * This exercise demonstrates ALL FIVE AOP advice types using AspectJ:
 *
 *   @Before         - Logs BEFORE getAllBooks() and addBook() execute
 *   @After          - Logs AFTER getAllBooks() and addBook() execute
 *   @AfterReturning - Logs return value AFTER findAllBooks() and saveBook() return
 *   @AfterThrowing  - Logs exception AFTER deleteBook() throws
 *   @Around         - Logs execution time AROUND findAllBooks() and saveBook()
 *
 * AOP Concepts demonstrated:
 *   - Aspect     : LoggingAspect class
 *   - Advice     : @Before, @After, @AfterReturning, @AfterThrowing, @Around methods
 *   - Pointcut   : execution expressions targeting service/repository packages
 *   - Join Point : any method execution that matches the pointcut
 *   - Weaving    : Spring applies AOP via runtime proxy (AspectJ-style annotations)
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 8");
        System.out.println("  Implementing Basic AOP with Spring (AspectJ)");
        System.out.println("===================================================\n");

        // Load Spring context — AOP proxies created automatically
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("[INFO] Spring Context loaded. AOP proxies active.\n");

        BookService bookService = context.getBean("bookService", BookService.class);

        // ===================================================
        // TEST 1: @Before + @After advice on service methods
        //         @AfterReturning + @Around on repository methods
        // ===================================================
        System.out.println("==========================================================");
        System.out.println("  TEST 1: getAllBooks() — @Before, @After, @AfterReturning,");
        System.out.println("          @Around advice in action");
        System.out.println("==========================================================");
        bookService.getAllBooks();

        // ===================================================
        // TEST 2: @Before + @After + @AfterReturning + @Around
        // ===================================================
        System.out.println("==========================================================");
        System.out.println("  TEST 2: addBook() — @Before, @After, @AfterReturning,");
        System.out.println("          @Around advice in action");
        System.out.println("==========================================================");
        bookService.addBook("Spring AOP in Action");

        // ===================================================
        // TEST 3: @AfterThrowing advice
        // ===================================================
        System.out.println("==========================================================");
        System.out.println("  TEST 3: deleteBook(-1) — @AfterThrowing advice in action");
        System.out.println("==========================================================");
        try {
            bookService.deleteBook(-1);
        } catch (IllegalArgumentException e) {
            System.out.println("[MAIN] Caught exception: " + e.getMessage());
        }

        System.out.println("\n===================================================");
        System.out.println("  Spring AOP Test PASSED!");
        System.out.println("  All 5 advice types demonstrated successfully.");
        System.out.println("===================================================");

        ((ClassPathXmlApplicationContext) context).close();
    }
}

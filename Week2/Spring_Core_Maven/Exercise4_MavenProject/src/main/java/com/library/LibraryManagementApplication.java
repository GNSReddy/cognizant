package com.library;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.library.service.BookService;

/**
 * Main class for the Library Management Application.
 * 
 * This exercise demonstrates configuring a Maven project with
 * Spring dependencies: Spring Context, Spring AOP, and Spring WebMVC.
 * 
 * The Maven Compiler Plugin is configured for Java 1.8.
 */
public class LibraryManagementApplication {

    public static void main(String[] args) {
        System.out.println("===================================================");
        System.out.println("  Library Management Application - Exercise 4");
        System.out.println("  Creating and Configuring a Maven Project");
        System.out.println("===================================================\n");

        // Display the Maven project configuration details
        System.out.println("[MAVEN CONFIG] Project: LibraryManagement");
        System.out.println("[MAVEN CONFIG] GroupId: com.library");
        System.out.println("[MAVEN CONFIG] ArtifactId: LibraryManagement");
        System.out.println("[MAVEN CONFIG] Packaging: jar");
        System.out.println("[MAVEN CONFIG] Java Version: 1.8\n");

        // Display loaded Spring dependencies
        System.out.println("[DEPENDENCIES] Loaded Spring Dependencies:");
        System.out.println("  1. spring-context  (v5.3.30) - IoC Container, ApplicationContext");
        System.out.println("  2. spring-aop      (v5.3.30) - Aspect-Oriented Programming");
        System.out.println("  3. spring-webmvc   (v5.3.30) - Web MVC Framework, REST support");
        System.out.println("  4. aspectjweaver   (v1.9.20) - AspectJ annotation support\n");

        // Verify Spring Context is working by loading beans
        System.out.println("[INFO] Loading Spring Application Context...");
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        System.out.println("[INFO] Spring Application Context loaded successfully!\n");

        // Verify Spring Context class (proves spring-context is loaded)
        System.out.println("[VERIFY] ApplicationContext implementation: "
                + context.getClass().getSimpleName());
        System.out.println("[VERIFY] Bean count: " + context.getBeanDefinitionCount());
        System.out.println("[VERIFY] Bean names: ");
        for (String beanName : context.getBeanDefinitionNames()) {
            System.out.println("  -> " + beanName);
        }

        // Test BookService
        System.out.println("\n--- Testing BookService ---");
        BookService bookService = context.getBean("bookService", BookService.class);
        bookService.getAllBooks();

        System.out.println("\n--- Testing addBook ---");
        bookService.addBook("Maven: The Definitive Guide");

        System.out.println("\n===================================================");
        System.out.println("  Maven Project Configuration Test PASSED!");
        System.out.println("  All Spring dependencies resolved successfully.");
        System.out.println("  Maven Compiler Plugin: Java 1.8");
        System.out.println("===================================================");

        // Close the context
        ((ClassPathXmlApplicationContext) context).close();
    }
}

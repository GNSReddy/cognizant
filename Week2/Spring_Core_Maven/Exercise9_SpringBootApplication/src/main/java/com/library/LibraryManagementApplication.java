package com.library;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * LibraryManagementApplication — Spring Boot Entry Point.
 *
 * @SpringBootApplication is a meta-annotation combining:
 *   @Configuration     — marks this class as a source of Spring bean definitions
 *   @EnableAutoConfiguration — enables Spring Boot's auto-configuration mechanism
 *                              (auto-configures DataSource, JPA, Tomcat, Jackson, etc.
 *                               based on dependencies present in the classpath)
 *   @ComponentScan     — scans the com.library package and sub-packages for
 *                        @Component, @Service, @Repository, @Controller beans
 *
 * SpringApplication.run() bootstraps the entire Spring Boot application:
 *   1. Creates the ApplicationContext
 *   2. Auto-configures all components
 *   3. Starts the embedded Tomcat server on port 8080
 *   4. Deploys the DispatcherServlet
 *   5. Executes any CommandLineRunner / ApplicationRunner beans
 */
@SpringBootApplication
public class LibraryManagementApplication {

    /**
     * Main method — entry point of the Spring Boot application.
     * @param args command-line arguments passed to the application
     */
    public static void main(String[] args) {
        SpringApplication.run(LibraryManagementApplication.class, args);
    }

    /**
     * CommandLineRunner bean — runs after the Spring context is loaded.
     * Seeds the H2 in-memory database with sample book data on startup.
     *
     * @Bean    — registers this method's return value as a Spring bean
     * CommandLineRunner — functional interface with run(String... args) method
     *
     * @param bookRepository the auto-injected BookRepository (Spring Data JPA)
     * @return a CommandLineRunner that inserts sample data into H2
     */
    @Bean
    public CommandLineRunner seedDatabase(BookRepository bookRepository) {
        return args -> {
            System.out.println("\n===================================================");
            System.out.println("  Library Management - Exercise 9 (Spring Boot)");
            System.out.println("  Seeding H2 Database with sample books...");
            System.out.println("===================================================\n");

            // Save sample books to the H2 in-memory database
            bookRepository.save(new Book("Spring in Action",         "Craig Walls",       "978-1617294945", 49.99));
            bookRepository.save(new Book("Effective Java",           "Joshua Bloch",      "978-0134685991", 54.99));
            bookRepository.save(new Book("Clean Code",               "Robert C. Martin",  "978-0132350884", 44.99));
            bookRepository.save(new Book("Head First Design Patterns","Eric Freeman",      "978-0596007126", 59.99));
            bookRepository.save(new Book("Java Concurrency in Practice","Brian Goetz",    "978-0321349606", 64.99));

            System.out.println("[INFO] Sample data seeded successfully!");
            System.out.println("[INFO] Total books in database: " + bookRepository.count());
            System.out.println("\n===================================================");
            System.out.println("  Application running at: http://localhost:8080");
            System.out.println("  H2 Console available at: http://localhost:8080/h2-console");
            System.out.println("  JDBC URL for H2 Console: jdbc:h2:mem:librarydb");
            System.out.println("===================================================");
            System.out.println("\n  REST API Endpoints:");
            System.out.println("  GET    http://localhost:8080/api/books");
            System.out.println("  GET    http://localhost:8080/api/books/{id}");
            System.out.println("  GET    http://localhost:8080/api/books/search?author=Craig Walls");
            System.out.println("  GET    http://localhost:8080/api/books/search?title=spring");
            System.out.println("  POST   http://localhost:8080/api/books");
            System.out.println("  PUT    http://localhost:8080/api/books/{id}");
            System.out.println("  DELETE http://localhost:8080/api/books/{id}");
            System.out.println("===================================================\n");
        };
    }
}

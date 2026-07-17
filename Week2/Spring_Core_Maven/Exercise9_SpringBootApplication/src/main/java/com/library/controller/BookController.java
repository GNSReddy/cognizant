package com.library.controller;

import com.library.entity.Book;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

/**
 * BookController — REST Controller for the Library Management System.
 *
 * @RestController is a combination of:
 *   @Controller     — marks this as a Spring MVC controller bean
 *   @ResponseBody   — all methods return JSON directly (no view resolver needed)
 *
 * @RequestMapping("/api/books") — base URL path for all endpoints in this controller.
 *
 * REST Endpoints provided:
 *   GET    /api/books              → Get all books
 *   GET    /api/books/{id}         → Get book by ID
 *   GET    /api/books/search?author=X → Search books by author
 *   GET    /api/books/search?title=X  → Search books by title keyword
 *   POST   /api/books              → Create a new book
 *   PUT    /api/books/{id}         → Update an existing book
 *   DELETE /api/books/{id}         → Delete a book by ID
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    /**
     * @Autowired — Spring Boot automatically injects the BookRepository bean.
     * Spring Data JPA generates the implementation at runtime.
     */
    @Autowired
    private BookRepository bookRepository;

    /**
     * GET /api/books
     * Returns all books from the H2 database.
     *
     * @GetMapping  — handles HTTP GET requests
     * ResponseEntity<List<Book>> — wraps the response with HTTP status code
     *
     * @return 200 OK with list of all books as JSON
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return ResponseEntity.ok(books);
    }

    /**
     * GET /api/books/{id}
     * Returns a single book by its ID.
     *
     * @PathVariable — extracts {id} from the URL path
     * Optional<Book> — findById may return empty if book not found
     *
     * @param id the book ID from the URL
     * @return 200 OK with the book, or 404 Not Found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * GET /api/books/search?author=Bloch
     * GET /api/books/search?title=spring
     * Searches books by author name or title keyword.
     *
     * @RequestParam — extracts query parameters from the URL
     * required = false — parameter is optional
     *
     * @param author optional author name to search by
     * @param title  optional title keyword to search by
     * @return 200 OK with matching books, or 400 Bad Request if no param given
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchBooks(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title) {

        if (author != null) {
            return ResponseEntity.ok(bookRepository.findByAuthor(author));
        } else if (title != null) {
            return ResponseEntity.ok(bookRepository.findByTitleContainingIgnoreCase(title));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * POST /api/books
     * Creates a new book and saves it to the H2 database.
     *
     * @PostMapping  — handles HTTP POST requests
     * @RequestBody  — deserializes the JSON request body into a Book object (via Jackson)
     *
     * @param book the Book object from the JSON request body
     * @return 201 Created with the saved book (including auto-generated ID)
     */
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
    }

    /**
     * PUT /api/books/{id}
     * Updates an existing book by its ID.
     *
     * @PutMapping  — handles HTTP PUT requests
     *
     * @param id          the ID of the book to update (from URL)
     * @param bookDetails the updated Book data (from request body)
     * @return 200 OK with updated book, or 404 Not Found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book bookDetails) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(bookDetails.getTitle());
            existingBook.setAuthor(bookDetails.getAuthor());
            existingBook.setIsbn(bookDetails.getIsbn());
            existingBook.setPrice(bookDetails.getPrice());
            Book updatedBook = bookRepository.save(existingBook);
            return ResponseEntity.ok(updatedBook);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * DELETE /api/books/{id}
     * Deletes a book by its ID from the H2 database.
     *
     * @DeleteMapping — handles HTTP DELETE requests
     *
     * @param id the ID of the book to delete
     * @return 200 OK with success message, or 404 Not Found
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.ok("Book with ID " + id + " deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Book with ID " + id + " not found.");
        }
    }
}

package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * BookRepository interface — Spring Data JPA Repository for the Book entity.
 *
 * By extending JpaRepository<Book, Long>, Spring Data JPA automatically provides:
 *
 *   CRUD Operations (inherited from CrudRepository):
 *     save(Book)                    → INSERT or UPDATE
 *     findById(Long)                → SELECT WHERE id = ?
 *     findAll()                     → SELECT * FROM BOOK
 *     deleteById(Long)              → DELETE WHERE id = ?
 *     delete(Book)                  → DELETE entity
 *     count()                       → SELECT COUNT(*)
 *     existsById(Long)              → check if record exists
 *
 *   JPA-specific Operations (inherited from JpaRepository):
 *     findAll(Sort)                 → SELECT * ORDER BY
 *     findAll(Pageable)             → Paginated results
 *     saveAll(Iterable)             → Batch INSERT/UPDATE
 *     flush()                       → Synchronize persistence context
 *
 * NO implementation class is needed — Spring generates a proxy at runtime!
 *
 * Generic parameters:
 *   Book  → the entity type this repository manages
 *   Long  → the type of the primary key (@Id field)
 *
 * @Repository marks this as a Spring bean and enables exception translation.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Custom query method — derived from method name by Spring Data JPA.
     * Spring automatically generates: SELECT * FROM BOOK WHERE AUTHOR = ?
     *
     * This demonstrates Spring Data JPA's "Query Derivation" feature:
     * method name conventions → SQL query (no SQL needed!)
     *
     * @param author the author name to search for
     * @return list of books by the given author
     */
    List<Book> findByAuthor(String author);

    /**
     * Custom query method — searches by title containing a keyword (case-insensitive).
     * Generated SQL: SELECT * FROM BOOK WHERE LOWER(TITLE) LIKE LOWER('%keyword%')
     *
     * @param keyword the keyword to search in the title
     * @return list of books whose title contains the keyword
     */
    List<Book> findByTitleContainingIgnoreCase(String keyword);
}

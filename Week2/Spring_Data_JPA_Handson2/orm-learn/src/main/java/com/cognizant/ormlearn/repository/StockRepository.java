package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * StockRepository - Spring Data JPA Repository for Stock entity.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 2
 * Topic: Write queries on stock table using Query Methods
 *
 * Extends JpaRepository<Stock, Integer>:
 *   Stock   - the entity type
 *   Integer - type of the @Id field (auto-increment int)
 *
 * Query Methods declared here — Spring Data JPA generates SQL automatically
 * by parsing the method names at application startup.
 */
@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

    /**
     * Find all stock entries for a given ticker symbol.
     *
     * Query Method: findByCode(code)
     * Spring generates SQL:
     *   SELECT * FROM stock WHERE st_code = ?
     *
     * Use case: Get all 7 FB rows or all 7 GOOGL rows
     *
     * @param code the stock ticker symbol e.g. "FB", "GOOGL", "NFLX"
     * @return List of all Stock entries for the given ticker
     */
    List<Stock> findByCode(String code);

    /**
     * Find all stock entries for a given ticker in a specific date range.
     *
     * Query Method: findByCodeAndDateBetween(code, startDate, endDate)
     * Spring generates SQL:
     *   SELECT * FROM stock
     *   WHERE st_code = ?
     *   AND st_date BETWEEN ? AND ?
     *
     * Use case: Get FB stocks traded between Jan 1 and Jan 5 2018
     *
     * @param code      the stock ticker symbol
     * @param startDate the start date of the range (inclusive)
     * @param endDate   the end date of the range (inclusive)
     * @return List of Stock entries for the ticker within the date range
     */
    List<Stock> findByCodeAndDateBetween(String code, Date startDate, Date endDate);

    /**
     * Find all stock entries where the opening price is greater than the given value.
     *
     * Query Method: findByOpenGreaterThan(price)
     * Spring generates SQL:
     *   SELECT * FROM stock WHERE st_open > ?
     *
     * Use case: Find premium stocks (e.g., GOOGL) where open > 1000.0
     *
     * @param price the threshold opening price
     * @return List of Stock entries where opening price > given price
     */
    List<Stock> findByOpenGreaterThan(double price);

}

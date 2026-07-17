package com.cognizant.ormlearn.service;

import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * StockService - Service Layer for Stock query operations.
 *
 * Handbook: Spring Data JPA Hands-on 2 — Hands on 2
 * Topic: Write queries on stock table using Query Methods
 *
 * Delegates all queries to StockRepository.
 * Spring Data JPA auto-generates the SQL from repository method names.
 *
 * @Service     - Registers this as a Spring bean
 * @Transactional - Spring manages Hibernate session and transaction per method
 */
@Service
public class StockService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StockService.class);

    /**
     * Spring auto-injects the StockRepository JPA proxy at startup.
     */
    @Autowired
    private StockRepository stockRepository;

    /**
     * Get all stock entries for a given ticker symbol.
     *
     * Delegates to: StockRepository.findByCode(code)
     * SQL: SELECT * FROM stock WHERE st_code = ?
     *
     * @param code the stock ticker e.g. "FB", "GOOGL", "NFLX"
     * @return List of all Stock entries for the given ticker
     */
    @Transactional
    public List<Stock> getAllStocksByCode(String code) {
        LOGGER.debug("Inside getAllStocksByCode, code={}", code);
        return stockRepository.findByCode(code);
    }

    /**
     * Get stock entries for a ticker in a specific date range.
     *
     * Delegates to: StockRepository.findByCodeAndDateBetween(code, start, end)
     * SQL: SELECT * FROM stock WHERE st_code = ? AND st_date BETWEEN ? AND ?
     *
     * @param code      the stock ticker symbol
     * @param startDate start of the date range (inclusive)
     * @param endDate   end of the date range (inclusive)
     * @return List of Stock entries within the date range
     */
    @Transactional
    public List<Stock> getStocksInDateRange(String code, Date startDate, Date endDate) {
        LOGGER.debug("Inside getStocksInDateRange, code={}, start={}, end={}", code, startDate, endDate);
        return stockRepository.findByCodeAndDateBetween(code, startDate, endDate);
    }

    /**
     * Get all stock entries where the opening price is above the given threshold.
     *
     * Delegates to: StockRepository.findByOpenGreaterThan(price)
     * SQL: SELECT * FROM stock WHERE st_open > ?
     *
     * @param price the minimum opening price threshold
     * @return List of Stock entries where opening price > threshold
     */
    @Transactional
    public List<Stock> getStocksAboveOpenPrice(double price) {
        LOGGER.debug("Inside getStocksAboveOpenPrice, price={}", price);
        return stockRepository.findByOpenGreaterThan(price);
    }

}

package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * OrmLearnApplication - Spring Boot Entry Point
 *
 * Handbook: Spring Data JPA Hands-on 3
 *   Hands-on 1: @Query on Country      (commented out)
 *   Hands-on 2: @Query on Employee     (commented out)
 *   Hands-on 3: Pagination             ← ACTIVE NOW
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService  countryService;
    private static EmployeeService employeeService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);
        countryService  = context.getBean(CountryService.class);
        employeeService = context.getBean(EmployeeService.class);
        LOGGER.info("Inside main");

        // ── Hands-on 1 (commented out) ────────────────────────────────
        // testFindCountryByName(); testSearchByKeyword();
        // testGetAllCountriesOrdered(); testFindByNameStartingWith();
        // testCountCountries(); testFindCountriesEndingWithIa();

        // ── Hands-on 2 (commented out) ────────────────────────────────
        // testGetEmployeesByDepartment(); testGetEmployeesAboveSalary();
        // testGetPermanentEmployees(); testGetEmployeesSortedBySalary();
        // testCountByDepartment(); testSearchByEmployeeName();
        // testGetEmployeesByDeptNative();

        // ── Hands-on 3: Pagination ────────────────────────────────────
        testCountryPagination();
        testEmployeePagination();
        testPermanentEmployeePagination();
        testCountrySearchWithPagination();
    }

    // ── Hands-on 1 stubs (kept for reference) ────────────────────────────
    private static void testFindCountryByName() {
        List<Country> list = countryService.findCountryByName("India");
        list.forEach(c -> LOGGER.debug("{}", c));
    }
    private static void testSearchByKeyword() {
        LOGGER.debug("count={}", countryService.searchByKeyword("land").size());
    }
    private static void testGetAllCountriesOrdered() {
        LOGGER.debug("count={}", countryService.getAllCountriesOrdered().size());
    }
    private static void testFindByNameStartingWith() {
        countryService.findByNameStartingWith("Z").forEach(c -> LOGGER.debug("{}", c));
    }
    private static void testCountCountries() {
        LOGGER.debug("Total: {}", countryService.countCountries());
    }
    private static void testFindCountriesEndingWithIa() {
        LOGGER.debug("count={}", countryService.findCountriesEndingWithIa().size());
    }

    // ── Hands-on 2 stubs (kept for reference) ────────────────────────────
    private static void testGetEmployeesByDepartment() {
        employeeService.getEmployeesByDepartment("IT").forEach(e -> LOGGER.debug("{}", e));
    }
    private static void testGetEmployeesAboveSalary() {
        LOGGER.debug("count={}", employeeService.getEmployeesAboveSalary(70000.0).size());
    }
    private static void testGetPermanentEmployees() {
        LOGGER.debug("count={}", employeeService.getPermanentEmployees().size());
    }
    private static void testGetEmployeesSortedBySalary() {
        employeeService.getEmployeesSortedBySalary().forEach(e -> LOGGER.debug("{}", e));
    }
    private static void testCountByDepartment() {
        LOGGER.debug("IT={}", employeeService.countByDepartment("IT"));
    }
    private static void testSearchByEmployeeName() {
        LOGGER.debug("count={}", employeeService.searchByName("son").size());
    }
    private static void testGetEmployeesByDeptNative() {
        LOGGER.debug("count={}", employeeService.getEmployeesByDeptNative("Finance").size());
    }

    // ── Hands-on 3: Pagination ────────────────────────────────────────────

    /**
     * testCountryPagination
     *
     * Demonstrates paginating through 249 countries 10 at a time.
     * Fetches pages 0, 1, 2 to show how OFFSET changes.
     *
     * SQL for page 0: SELECT * FROM country LIMIT 10 OFFSET 0
     * SQL for page 1: SELECT * FROM country LIMIT 10 OFFSET 10
     * SQL for page 2: SELECT * FROM country LIMIT 10 OFFSET 20
     *
     * Page metadata:
     *   totalElements = 249
     *   totalPages    = 25  (249 / 10 = 24.9 → rounded up)
     *   pageSize      = 10
     *   isFirst()     = true  (page 0 only)
     *   isLast()      = false (pages 0, 1, 2)
     */
    private static void testCountryPagination() {
        LOGGER.info("Start testCountryPagination");

        // Page 0 — first 10 countries
        Page<Country> page0 = countryService.getCountriesByPage(0, 10);
        LOGGER.debug("=== Page 1 of {} ===", page0.getTotalPages());
        LOGGER.debug("Total countries (all pages): {}", page0.getTotalElements());
        LOGGER.debug("Countries on this page     : {}", page0.getNumberOfElements());
        LOGGER.debug("Is first page?             : {}", page0.isFirst());
        page0.getContent().forEach(c -> LOGGER.debug("  {}", c));

        // Page 1 — next 10 countries
        Page<Country> page1 = countryService.getCountriesByPage(1, 10);
        LOGGER.debug("=== Page 2 of {} ===", page1.getTotalPages());
        page1.getContent().forEach(c -> LOGGER.debug("  {}", c));

        // Page 24 — last page (249 countries / 10 = 24 full pages + 1 partial)
        Page<Country> lastPage = countryService.getCountriesByPage(24, 10);
        LOGGER.debug("=== Page 25 of {} (Last Page) ===", lastPage.getTotalPages());
        LOGGER.debug("Countries on last page: {}", lastPage.getNumberOfElements());
        LOGGER.debug("Is last page?         : {}", lastPage.isLast());
        lastPage.getContent().forEach(c -> LOGGER.debug("  {}", c));

        LOGGER.info("End testCountryPagination");
    }

    /**
     * testEmployeePagination
     *
     * Demonstrates paginating 8 employees 3 at a time.
     * Shows all 3 pages including partial last page.
     *
     * SQL page 0: SELECT ... FROM employee LIMIT 3 OFFSET 0
     * SQL page 1: SELECT ... FROM employee LIMIT 3 OFFSET 3
     * SQL page 2: SELECT ... FROM employee LIMIT 3 OFFSET 6  (2 employees returned)
     */
    private static void testEmployeePagination() {
        LOGGER.info("Start testEmployeePagination");

        int pageSize = 3;
        Page<Employee> page = employeeService.getEmployeesByPage(0, pageSize);

        LOGGER.debug("Total employees : {}", page.getTotalElements());
        LOGGER.debug("Total pages     : {} (size={})", page.getTotalPages(), pageSize);

        // Iterate through ALL pages programmatically
        int currentPage = 0;
        do {
            page = employeeService.getEmployeesByPage(currentPage, pageSize);
            LOGGER.debug("--- Page {} of {} ---", currentPage + 1, page.getTotalPages());
            for (Employee e : page.getContent()) {
                LOGGER.debug("  {} | {} | Salary: {}",
                        e.getName(), e.getDepartment().getName(), e.getSalary());
            }
            currentPage++;
        } while (!page.isLast());

        LOGGER.info("End testEmployeePagination");
    }

    /**
     * testPermanentEmployeePagination
     *
     * Combines @Query boolean filter WITH pagination.
     * Only permanent employees are counted and paginated.
     * SQL: SELECT ... FROM employee WHERE emp_permanent=1 LIMIT 3 OFFSET 0
     */
    private static void testPermanentEmployeePagination() {
        LOGGER.info("Start testPermanentEmployeePagination");

        Page<Employee> page = employeeService.getPermanentEmployeesByPage(0, 3);
        LOGGER.debug("Total permanent employees: {}", page.getTotalElements());
        LOGGER.debug("Page 1 of {} (size=3):", page.getTotalPages());
        for (Employee e : page.getContent()) {
            LOGGER.debug("  {} | Permanent: {}", e.getName(), e.isPermanent());
        }

        LOGGER.info("End testPermanentEmployeePagination");
    }

    /**
     * testCountrySearchWithPagination
     *
     * Combines @Query LIKE search WITH pagination.
     * Filters countries containing "land" then paginates results.
     * SQL: SELECT * FROM country WHERE co_name LIKE '%land%' LIMIT 5 OFFSET 0
     */
    private static void testCountrySearchWithPagination() {
        LOGGER.info("Start testCountrySearchWithPagination");

        Page<Country> page = countryService.searchByKeywordPageable("land", 0, 5);
        LOGGER.debug("Countries containing 'land' — total: {}", page.getTotalElements());
        LOGGER.debug("Page 1 of {} (size=5):", page.getTotalPages());
        for (Country c : page.getContent()) {
            LOGGER.debug("  {}", c);
        }

        LOGGER.info("End testCountrySearchWithPagination");
    }

}

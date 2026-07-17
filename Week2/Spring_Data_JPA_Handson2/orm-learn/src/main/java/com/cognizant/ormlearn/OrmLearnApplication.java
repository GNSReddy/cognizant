package com.cognizant.ormlearn;

import com.cognizant.ormlearn.model.Country;
import com.cognizant.ormlearn.model.Department;
import com.cognizant.ormlearn.model.Employee;
import com.cognizant.ormlearn.model.Skill;
import com.cognizant.ormlearn.model.Stock;
import com.cognizant.ormlearn.service.CountryService;
import com.cognizant.ormlearn.service.DepartmentService;
import com.cognizant.ormlearn.service.EmployeeService;
import com.cognizant.ormlearn.service.StockService;
import com.cognizant.ormlearn.service.exception.CountryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * OrmLearnApplication - Spring Boot Entry Point
 *
 * Handbook: Spring Data JPA Hands-on 2 — ALL Hands-on COMPLETE
 *
 * Hands-on 1: Country Query Methods        (commented out)
 * Hands-on 2: Stock Query Methods          (commented out)
 * Hands-on 3: Payroll Tables / Bean Mapping (no test)
 * Hands-on 4: ManyToOne Relationship       (commented out)
 * Hands-on 5: OneToMany Relationship       (commented out)
 * Hands-on 6: ManyToMany Relationship      ← ACTIVE NOW
 */
@SpringBootApplication
public class OrmLearnApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrmLearnApplication.class);

    private static CountryService    countryService;
    private static StockService      stockService;
    private static EmployeeService   employeeService;
    private static DepartmentService departmentService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(OrmLearnApplication.class, args);

        countryService    = context.getBean(CountryService.class);
        stockService      = context.getBean(StockService.class);
        employeeService   = context.getBean(EmployeeService.class);
        departmentService = context.getBean(DepartmentService.class);

        LOGGER.info("Inside main");

        // ── Hands-on 1 (commented out) ────────────────────────────────
        // testSearchCountries();
        // testGetSortedCountries();
        // testFindCountriesStartingWith();

        // ── Hands-on 2 (commented out) ────────────────────────────────
        // testGetAllStocksByCode();
        // testGetStocksInDateRange();
        // testGetStocksAboveOpenPrice();

        // ── Hands-on 4 (commented out) ────────────────────────────────
        // testGetAllEmployees();

        // ── Hands-on 5 (commented out) ────────────────────────────────
        // testGetDepartmentById();

        // ── Hands-on 6: ManyToMany — Employee ↔ Skill ────────────────
        testGetEmployeeSkills();
    }

    // ── Hands-on 1 ───────────────────────────────────────────────────────
    private static void testSearchCountries() {
        LOGGER.info("Start testSearchCountries");
        List<Country> countries = countryService.searchCountries("ia");
        LOGGER.debug("count={}", countries.size());
        LOGGER.info("End testSearchCountries");
    }

    private static void testGetSortedCountries() {
        LOGGER.info("Start testGetSortedCountries");
        List<Country> countries = countryService.getSortedCountries();
        LOGGER.debug("count={}", countries.size());
        LOGGER.info("End testGetSortedCountries");
    }

    private static void testFindCountriesStartingWith() {
        LOGGER.info("Start testFindCountriesStartingWith");
        List<Country> countries = countryService.findCountriesStartingWith("A");
        LOGGER.debug("count={}", countries.size());
        LOGGER.info("End testFindCountriesStartingWith");
    }

    // ── Hands-on 2 ───────────────────────────────────────────────────────
    private static void testGetAllStocksByCode() {
        LOGGER.info("Start testGetAllStocksByCode");
        List<Stock> stocks = stockService.getAllStocksByCode("FB");
        LOGGER.debug("FB count={}", stocks.size());
        LOGGER.info("End testGetAllStocksByCode");
    }

    private static void testGetStocksInDateRange() {
        LOGGER.info("Start testGetStocksInDateRange");
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date startDate = sdf.parse("2018-01-01");
            Date endDate   = sdf.parse("2018-01-05");
            List<Stock> stocks = stockService.getStocksInDateRange("GOOGL", startDate, endDate);
            LOGGER.debug("GOOGL count={}", stocks.size());
        } catch (ParseException e) {
            LOGGER.error("Date parse error: {}", e.getMessage());
        }
        LOGGER.info("End testGetStocksInDateRange");
    }

    private static void testGetStocksAboveOpenPrice() {
        LOGGER.info("Start testGetStocksAboveOpenPrice");
        List<Stock> stocks = stockService.getStocksAboveOpenPrice(1000.0);
        LOGGER.debug("count={}", stocks.size());
        LOGGER.info("End testGetStocksAboveOpenPrice");
    }

    // ── Hands-on 4 ───────────────────────────────────────────────────────
    private static void testGetAllEmployees() {
        LOGGER.info("Start testGetAllEmployees");
        List<Employee> employees = employeeService.getAllEmployees();
        LOGGER.debug("Total: {}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("Employee: {} | Dept: {}", e.getName(), e.getDepartment().getName());
        }
        LOGGER.info("End testGetAllEmployees");
    }

    // ── Hands-on 5 ───────────────────────────────────────────────────────
    private static void testGetDepartmentById() {
        LOGGER.info("Start testGetDepartmentById");
        Department department = departmentService.getDepartmentById(3);
        LOGGER.debug("Department: {}", department.getName());
        for (Employee e : department.getEmployees()) {
            LOGGER.debug("  Employee: {}", e.getName());
        }
        LOGGER.info("End testGetDepartmentById");
    }

    // ── Hands-on 6: ManyToMany — Employee ↔ Skill ────────────────────────

    /**
     * testGetEmployeeSkills — Tests the @ManyToMany relationship.
     *
     * Calls getEmployeeById(1) to load Alice Johnson (IT employee).
     * Alice has 3 skills: Java, SQL, Spring Boot (from payroll.sql).
     *
     * Two SQL queries fired by Hibernate:
     *
     *   Query 1 — Load Employee + Department (EAGER JOIN):
     *     SELECT e.emp_id, e.emp_name, ..., d.dept_id, d.dept_name
     *     FROM employee e
     *     INNER JOIN department d ON e.dept_id = d.dept_id
     *     WHERE e.emp_id = 1
     *
     *   Query 2 — Load Skills via join table (LAZY, triggered inside @Transactional):
     *     SELECT s.skill_id, s.skill_name
     *     FROM skill s
     *     INNER JOIN employee_skill es ON s.skill_id = es.skill_id
     *     WHERE es.emp_id = 1
     *
     * Expected output:
     *   Employee: Alice Johnson | Department: IT
     *   Skills count: 3
     *     Skill: Java
     *     Skill: SQL
     *     Skill: Spring Boot
     *
     * Also tests employee 6 (Frank Miller) who has 4 skills:
     *   Java, Python, SQL, Spring Boot
     */
    private static void testGetEmployeeSkills() {
        LOGGER.info("Start testGetEmployeeSkills");

        // ── Test 1: Alice Johnson (emp_id=1) — 3 skills ───────────────
        LOGGER.debug("--- Employee 1: Alice Johnson ---");
        Employee alice = employeeService.getEmployeeById(1);
        LOGGER.debug("Employee: {} | Department: {}",
                alice.getName(), alice.getDepartment().getName());

        List<Skill> aliceSkills = alice.getSkills();
        LOGGER.debug("Skills count: {}", aliceSkills.size());
        for (Skill skill : aliceSkills) {
            LOGGER.debug("  Skill: {}", skill.getName());
        }

        // ── Test 2: Frank Miller (emp_id=6) — 4 skills ────────────────
        LOGGER.debug("--- Employee 6: Frank Miller ---");
        Employee frank = employeeService.getEmployeeById(6);
        LOGGER.debug("Employee: {} | Department: {}",
                frank.getName(), frank.getDepartment().getName());

        List<Skill> frankSkills = frank.getSkills();
        LOGGER.debug("Skills count: {}", frankSkills.size());
        for (Skill skill : frankSkills) {
            LOGGER.debug("  Skill: {}", skill.getName());
        }

        LOGGER.info("End testGetEmployeeSkills");
    }

}

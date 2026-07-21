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

import java.util.List;

/**
 * OrmLearnApplication - Spring Boot Entry Point
 *
 * Handbook: Spring Data JPA Hands-on 3
 *
 * Hands-on 1: @Query on Country table   (commented out)
 * Hands-on 2: @Query on Employee/Dept   ← ACTIVE NOW
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

        // ── Hands-on 1: @Query on Country (commented out) ─────────────
        // testFindCountryByName();
        // testSearchByKeyword();
        // testGetAllCountriesOrdered();
        // testFindByNameStartingWith();
        // testCountCountries();
        // testFindCountriesEndingWithIa();

        // ── Hands-on 2: @Query on Employee / Department ────────────────
        testGetEmployeesByDepartment();
        testGetEmployeesAboveSalary();
        testGetPermanentEmployees();
        testGetEmployeesSortedBySalary();
        testCountByDepartment();
        testSearchByEmployeeName();
        testGetEmployeesByDeptNative();
    }

    // ── Hands-on 1 methods (carried forward) ─────────────────────────────
    private static void testFindCountryByName() {
        LOGGER.info("Start testFindCountryByName");
        List<Country> list = countryService.findCountryByName("India");
        list.forEach(c -> LOGGER.debug("{}", c));
        LOGGER.info("End testFindCountryByName");
    }

    private static void testSearchByKeyword() {
        LOGGER.info("Start testSearchByKeyword");
        List<Country> list = countryService.searchByKeyword("land");
        LOGGER.debug("count={}", list.size());
        LOGGER.info("End testSearchByKeyword");
    }

    private static void testGetAllCountriesOrdered() {
        LOGGER.info("Start testGetAllCountriesOrdered");
        List<Country> list = countryService.getAllCountriesOrdered();
        LOGGER.debug("count={}", list.size());
        LOGGER.info("End testGetAllCountriesOrdered");
    }

    private static void testFindByNameStartingWith() {
        LOGGER.info("Start testFindByNameStartingWith");
        List<Country> list = countryService.findByNameStartingWith("Z");
        list.forEach(c -> LOGGER.debug("{}", c));
        LOGGER.info("End testFindByNameStartingWith");
    }

    private static void testCountCountries() {
        LOGGER.info("Start testCountCountries");
        LOGGER.debug("Total: {}", countryService.countCountries());
        LOGGER.info("End testCountCountries");
    }

    private static void testFindCountriesEndingWithIa() {
        LOGGER.info("Start testFindCountriesEndingWithIa");
        List<Country> list = countryService.findCountriesEndingWithIa();
        LOGGER.debug("count={}", list.size());
        LOGGER.info("End testFindCountriesEndingWithIa");
    }

    // ── Hands-on 2: @Query on Employee / Department ──────────────────────

    /**
     * testGetEmployeesByDepartment
     * @Query JPQL JOIN: SELECT e FROM Employee e WHERE e.department.name = :deptName
     * SQL  : SELECT e.*, d.* FROM employee e JOIN department d ... WHERE d.dept_name = 'IT'
     * Expect: Alice Johnson, David Brown, Frank Miller (3 IT employees)
     */
    private static void testGetEmployeesByDepartment() {
        LOGGER.info("Start testGetEmployeesByDepartment");
        List<Employee> employees = employeeService.getEmployeesByDepartment("IT");
        LOGGER.debug("IT dept employees count={}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("  {} | Salary: {} | Dept: {}",
                    e.getName(), e.getSalary(), e.getDepartment().getName());
        }
        LOGGER.info("End testGetEmployeesByDepartment");
    }

    /**
     * testGetEmployeesAboveSalary
     * @Query JPQL: SELECT e FROM Employee e WHERE e.salary > :salary
     * SQL  : WHERE emp_salary > 70000
     * Expect: Alice (75k), David (80k), Eve (70k excluded, >70k), Frank (90k)
     */
    private static void testGetEmployeesAboveSalary() {
        LOGGER.info("Start testGetEmployeesAboveSalary");
        List<Employee> employees = employeeService.getEmployeesAboveSalary(70000.0);
        LOGGER.debug("Employees with salary > 70000: count={}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("  {} | Salary: {}", e.getName(), e.getSalary());
        }
        LOGGER.info("End testGetEmployeesAboveSalary");
    }

    /**
     * testGetPermanentEmployees
     * @Query JPQL: SELECT e FROM Employee e WHERE e.permanent = true
     * SQL  : WHERE emp_permanent = 1
     * Expect: Alice, Bob, David, Eve, Frank, Henry (6 permanent)
     */
    private static void testGetPermanentEmployees() {
        LOGGER.info("Start testGetPermanentEmployees");
        List<Employee> employees = employeeService.getPermanentEmployees();
        LOGGER.debug("Permanent employees count={}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("  {} | Permanent: {}", e.getName(), e.isPermanent());
        }
        LOGGER.info("End testGetPermanentEmployees");
    }

    /**
     * testGetEmployeesSortedBySalary
     * @Query JPQL: SELECT e FROM Employee e ORDER BY e.salary DESC
     * SQL  : ORDER BY emp_salary DESC
     * Expect: Frank (90k) → David (80k) → Alice (75k) → Eve (70k)...
     */
    private static void testGetEmployeesSortedBySalary() {
        LOGGER.info("Start testGetEmployeesSortedBySalary");
        List<Employee> employees = employeeService.getEmployeesSortedBySalary();
        LOGGER.debug("Employees sorted by salary DESC: count={}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("  {} | Salary: {}", e.getName(), e.getSalary());
        }
        LOGGER.info("End testGetEmployeesSortedBySalary");
    }

    /**
     * testCountByDepartment
     * @Query JPQL COUNT: SELECT COUNT(e) FROM Employee e WHERE e.department.name = :deptName
     * SQL  : SELECT COUNT(*) FROM employee JOIN department WHERE dept_name = 'IT'
     * Expect: 3 (Alice, David, Frank are all in IT)
     */
    private static void testCountByDepartment() {
        LOGGER.info("Start testCountByDepartment");
        long itCount  = employeeService.countByDepartment("IT");
        long hrCount  = employeeService.countByDepartment("HR");
        long finCount = employeeService.countByDepartment("Finance");
        LOGGER.debug("IT dept count     : {}", itCount);
        LOGGER.debug("HR dept count     : {}", hrCount);
        LOGGER.debug("Finance dept count: {}", finCount);
        LOGGER.info("End testCountByDepartment");
    }

    /**
     * testSearchByEmployeeName
     * @Query JPQL LIKE: SELECT e FROM Employee e WHERE e.name LIKE %:keyword%
     * SQL  : WHERE emp_name LIKE '%son%'
     * Expect: Alice Johnson, Alice (Johnson contains "son")
     */
    private static void testSearchByEmployeeName() {
        LOGGER.info("Start testSearchByEmployeeName");
        List<Employee> employees = employeeService.searchByName("son");
        LOGGER.debug("Employees with name containing 'son': count={}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("  {}", e.getName());
        }
        LOGGER.info("End testSearchByEmployeeName");
    }

    /**
     * testGetEmployeesByDeptNative
     * Native SQL: SELECT e.* FROM employee e JOIN department d ... WHERE d.dept_name = 'Finance'
     * Expect: Bob Smith, Henry Moore (2 Finance employees via raw SQL)
     */
    private static void testGetEmployeesByDeptNative() {
        LOGGER.info("Start testGetEmployeesByDeptNative");
        List<Employee> employees = employeeService.getEmployeesByDeptNative("Finance");
        LOGGER.debug("Finance employees (native SQL) count={}", employees.size());
        for (Employee e : employees) {
            LOGGER.debug("  {} | Salary: {}", e.getName(), e.getSalary());
        }
        LOGGER.info("End testGetEmployeesByDeptNative");
    }

}

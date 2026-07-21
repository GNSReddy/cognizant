package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmployeeRepository - Spring Data JPA Repository for Employee entity.
 *
 * Handbook: Spring Data JPA Hands-on 3 — Hands on 2
 * Topic: @Query annotation on Employee / Department tables
 *
 * Key point: @Query on Employee can JOIN Department using JPQL dot-notation.
 *   JPQL: e.department.name  → Hibernate generates: JOIN department d ON e.dept_id = d.dept_id
 *   No explicit JOIN keyword needed in JPQL when @ManyToOne is declared on entity.
 *
 * JPQL uses entity/field names, NOT table/column names:
 *   Entity:  Employee     → Table:  employee
 *   Field:   name         → Column: emp_name
 *   Field:   salary       → Column: emp_salary
 *   Field:   permanent    → Column: emp_permanent
 *   Field:   department   → FK:     dept_id (joined to department table)
 *   Field:   department.name → Column: dept_name (navigated via JOIN)
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    /**
     * Find all employees belonging to a specific department by department name.
     *
     * JPQL: e.department.name traverses the @ManyToOne relationship.
     * Hibernate generates:
     *   SELECT e.emp_id, e.emp_name, e.emp_salary, e.emp_permanent,
     *          e.emp_date_of_joining, d.dept_id, d.dept_name
     *   FROM employee e
     *   INNER JOIN department d ON e.dept_id = d.dept_id
     *   WHERE d.dept_name = ?
     *
     * @param deptName department name to filter by (e.g., "IT", "Finance")
     * @return List of employees in the specified department
     */
    @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
    List<Employee> findEmployeesByDepartmentName(@Param("deptName") String deptName);

    /**
     * Find all employees whose salary is greater than the given amount.
     *
     * JPQL: uses Greater Than comparison on numeric field.
     * Generated SQL:
     *   SELECT ... FROM employee e
     *   INNER JOIN department d ON e.dept_id = d.dept_id
     *   WHERE e.emp_salary > ?
     *
     * @param salary minimum salary threshold
     * @return List of employees earning more than the given salary
     */
    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeesBySalaryGreaterThan(@Param("salary") double salary);

    /**
     * Find only permanent employees (emp_permanent = 1).
     *
     * JPQL boolean comparison:
     *   e.permanent = true  → Hibernate generates: WHERE emp_permanent = 1
     *
     * Generated SQL:
     *   SELECT ... FROM employee e WHERE e.emp_permanent = 1
     *
     * @return List of all permanent employees
     */
    @Query("SELECT e FROM Employee e WHERE e.permanent = true")
    List<Employee> findPermanentEmployees();

    /**
     * Find all employees sorted by salary in descending order.
     *
     * JPQL ORDER BY on numeric field.
     * Generated SQL:
     *   SELECT ... FROM employee e ORDER BY e.emp_salary DESC
     *
     * @return List of all employees sorted highest salary first
     */
    @Query("SELECT e FROM Employee e ORDER BY e.salary DESC")
    List<Employee> findAllEmployeesOrderedBySalaryDesc();

    /**
     * Count the number of employees in a given department.
     *
     * JPQL COUNT aggregate with @ManyToOne JOIN navigation.
     * Returns a Long scalar — NOT a List of Employee entities.
     * Generated SQL:
     *   SELECT COUNT(e.emp_id) FROM employee e
     *   INNER JOIN department d ON e.dept_id = d.dept_id
     *   WHERE d.dept_name = ?
     *
     * @param deptName the department name to count employees for
     * @return count of employees in the specified department
     */
    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.name = :deptName")
    long countEmployeesByDepartmentName(@Param("deptName") String deptName);

    /**
     * Find employees whose name contains the keyword — JPQL LIKE.
     *
     * JPQL LIKE with %:keyword% — searches substring in emp_name.
     * Generated SQL:
     *   SELECT ... FROM employee e WHERE e.emp_name LIKE '%keyword%'
     *
     * @param keyword substring to search within employee names
     * @return List of employees whose name contains the keyword
     */
    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword%")
    List<Employee> findEmployeesByNameKeyword(@Param("keyword") String keyword);

    /**
     * Find employees by department using a native SQL query.
     *
     * nativeQuery = true: bypasses JPQL, sends raw SQL to MySQL.
     * Uses actual table/column names (employee, department, dept_name, dept_id).
     * Returns List<Employee> — Spring maps result rows to Employee entity.
     *
     * Use case: complex SQL expressions not expressible in JPQL,
     *   or database-specific functions.
     *
     * @param deptName the department name (actual DB value)
     * @return List of employees in that department via native SQL
     */
    @Query(value = "SELECT e.* FROM employee e "
                 + "INNER JOIN department d ON e.dept_id = d.dept_id "
                 + "WHERE d.dept_name = :deptName",
           nativeQuery = true)
    List<Employee> findEmployeesByDeptNameNative(@Param("deptName") String deptName);

}

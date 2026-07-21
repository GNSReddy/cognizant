package com.cognizant.ormlearn.repository;

import com.cognizant.ormlearn.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * EmployeeRepository - Spring Data JPA Repository for Employee entity.
 *
 * Handbook: Spring Data JPA Hands-on 3
 *   Hands-on 2: @Query JPQL on Employee/Department
 *   Hands-on 3: Pagination using Pageable ← ADDED NOW
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    // ── Hands-on 2: @Query methods (carried forward) ─────────────────────

    @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
    List<Employee> findEmployeesByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT e FROM Employee e WHERE e.salary > :salary")
    List<Employee> findEmployeesBySalaryGreaterThan(@Param("salary") double salary);

    @Query("SELECT e FROM Employee e WHERE e.permanent = true")
    List<Employee> findPermanentEmployees();

    @Query("SELECT e FROM Employee e ORDER BY e.salary DESC")
    List<Employee> findAllEmployeesOrderedBySalaryDesc();

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.department.name = :deptName")
    long countEmployeesByDepartmentName(@Param("deptName") String deptName);

    @Query("SELECT e FROM Employee e WHERE e.name LIKE %:keyword%")
    List<Employee> findEmployeesByNameKeyword(@Param("keyword") String keyword);

    @Query(value = "SELECT e.* FROM employee e "
                 + "INNER JOIN department d ON e.dept_id = d.dept_id "
                 + "WHERE d.dept_name = :deptName",
           nativeQuery = true)
    List<Employee> findEmployeesByDeptNameNative(@Param("deptName") String deptName);

    // ── Hands-on 3: Pagination ────────────────────────────────────────────

    /**
     * Get all employees in a paginated fashion.
     *
     * Inherited from JpaRepository — declared here for clarity.
     *
     * SQL (two queries):
     *   Data : SELECT e.*, d.* FROM employee e JOIN department d ... LIMIT ? OFFSET ?
     *   Count: SELECT COUNT(e.emp_id) FROM employee e
     *
     * @param pageable page number (0-based), page size, optional sort
     * @return Page<Employee> with current-page data + total elements metadata
     */
    Page<Employee> findAll(Pageable pageable);

    /**
     * Get permanent employees only — paginated.
     *
     * Combines boolean filter WITH Pageable.
     * SQL: SELECT ... FROM employee WHERE emp_permanent = 1 LIMIT ? OFFSET ?
     *
     * @param pageable pagination info
     * @return Page<Employee> of only permanent employees for the requested page
     */
    @Query("SELECT e FROM Employee e WHERE e.permanent = true")
    Page<Employee> findPermanentEmployeesPageable(Pageable pageable);

}

package com.ems.repository.primary;

import com.ems.dto.EmployeeDto;
import com.ems.entity.primary.Employee;
import com.ems.projection.EmployeeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    // --- Exercise 3: Derived Query Methods ---
    List<Employee> findByName(String name);
    List<Employee> findByEmail(String email);
    List<Employee> findByDepartmentId(Long departmentId);
    List<Employee> findByNameContaining(String name);

    // --- Exercise 5: Custom JPQL and Native Queries ---
    // JPQL Query
    @Query("SELECT e FROM Employee e WHERE e.department.name = :deptName")
    List<Employee> findEmployeesByDeptName(@Param("deptName") String deptName);

    // Native SQL Query
    @Query(value = "SELECT * FROM employees e WHERE e.email LIKE %:domain", nativeQuery = true)
    List<Employee> findEmployeesByEmailDomain(@Param("domain") String domain);

    // Named Query Bindings (matching @NamedQuery defined in Employee entity)
    Employee findByEmailNamed(@Param("email") String email);
    List<Employee> findByDepartmentNameNamed(@Param("deptName") String deptName);

    // --- Exercise 6: Paginated Queries ---
    Page<Employee> findByNameContaining(String name, Pageable pageable);

    // --- Exercise 8: Projections ---
    // Interface-based projection
    @Query("SELECT e.name as name, e.email as email, d.name as departmentName " +
           "FROM Employee e JOIN e.department d")
    List<EmployeeProjection> findAllProjectedBy();

    // Class-based projection (DTO)
    @Query("SELECT new com.ems.dto.EmployeeDto(e.id, e.name, e.email, d.name) " +
           "FROM Employee e JOIN e.department d")
    List<EmployeeDto> findAllEmployeeDtos();
}

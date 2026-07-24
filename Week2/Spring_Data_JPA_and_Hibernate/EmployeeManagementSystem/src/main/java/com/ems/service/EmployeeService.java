package com.ems.service;

import com.ems.dto.EmployeeDto;
import com.ems.entity.primary.Employee;
import com.ems.projection.EmployeeProjection;
import org.springframework.data.domain.Page;

import java.util.List;

public interface EmployeeService {

    // CRUD Operations
    Employee createEmployee(Employee employee, Long departmentId);

    Employee getEmployeeById(Long id);

    List<Employee> getAllEmployees();

    Employee updateEmployee(Long id, Employee employeeDetails, Long departmentId);

    void deleteEmployee(Long id);

    // Pagination & Sorting Operations (Exercise 6)
    Page<Employee> getAllEmployees(int page, int size, String sortBy, String sortDir);

    Page<Employee> searchEmployeesByName(String name, int page, int size, String sortBy, String sortDir);

    // Custom Queries (Exercise 5)
    List<Employee> getEmployeesByDepartmentName(String deptName);

    List<Employee> getEmployeesByEmailDomain(String domain);

    Employee getEmployeeByEmailNamed(String email);

    List<Employee> getEmployeesByDepartmentNameNamed(String deptName);

    // Projections (Exercise 8)
    List<EmployeeProjection> getEmployeeProjections();

    List<EmployeeDto> getEmployeeDtos();
}

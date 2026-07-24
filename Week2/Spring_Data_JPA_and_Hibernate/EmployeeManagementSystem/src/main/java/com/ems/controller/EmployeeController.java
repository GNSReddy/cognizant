package com.ems.controller;

import com.ems.dto.EmployeeDto;
import com.ems.entity.primary.Employee;
import com.ems.projection.EmployeeProjection;
import com.ems.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // --- Exercise 4: Basic CRUD REST APIs ---
    @PostMapping
    public ResponseEntity<Employee> createEmployee(@RequestParam Long departmentId, @Valid @RequestBody Employee employee) {
        Employee savedEmployee = employeeService.createEmployee(employee, departmentId);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, 
                                                   @RequestParam Long departmentId, 
                                                   @Valid @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = employeeService.updateEmployee(id, employeeDetails, departmentId);
        return ResponseEntity.ok(updatedEmployee);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // --- Exercise 6: Pagination and Sorting REST APIs ---
    @GetMapping("/paginated")
    public ResponseEntity<Page<Employee>> getEmployeesPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<Employee> employeePage = employeeService.getAllEmployees(page, size, sortBy, direction);
        return ResponseEntity.ok(employeePage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<Employee>> searchEmployeesByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        Page<Employee> employeePage = employeeService.searchEmployeesByName(name, page, size, sortBy, direction);
        return ResponseEntity.ok(employeePage);
    }

    // --- Exercise 5: Custom Query REST APIs ---
    @GetMapping("/dept/{deptName}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentName(@PathVariable String deptName) {
        List<Employee> employees = employeeService.getEmployeesByDepartmentName(deptName);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/email-domain")
    public ResponseEntity<List<Employee>> getEmployeesByEmailDomain(@RequestParam String domain) {
        List<Employee> employees = employeeService.getEmployeesByEmailDomain(domain);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/email-named")
    public ResponseEntity<Employee> getEmployeeByEmailNamed(@RequestParam String email) {
        Employee employee = employeeService.getEmployeeByEmailNamed(email);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/dept-named/{deptName}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartmentNameNamed(@PathVariable String deptName) {
        List<Employee> employees = employeeService.getEmployeesByDepartmentNameNamed(deptName);
        return ResponseEntity.ok(employees);
    }

    // --- Exercise 8: Projections REST APIs ---
    @GetMapping("/projections")
    public ResponseEntity<List<EmployeeProjection>> getEmployeeProjections() {
        List<EmployeeProjection> projections = employeeService.getEmployeeProjections();
        return ResponseEntity.ok(projections);
    }

    @GetMapping("/dtos")
    public ResponseEntity<List<EmployeeDto>> getEmployeeDtos() {
        List<EmployeeDto> dtos = employeeService.getEmployeeDtos();
        return ResponseEntity.ok(dtos);
    }
}

package com.ems.service.impl;

import com.ems.dto.EmployeeDto;
import com.ems.entity.primary.Department;
import com.ems.entity.primary.Employee;
import com.ems.entity.secondary.AuditLog;
import com.ems.exception.ResourceNotFoundException;
import com.ems.projection.EmployeeProjection;
import com.ems.repository.primary.DepartmentRepository;
import com.ems.repository.primary.EmployeeRepository;
import com.ems.repository.secondary.AuditLogRepository;
import com.ems.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, 
                               DepartmentRepository departmentRepository,
                               AuditLogRepository auditLogRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Transactional("primaryTransactionManager")
    public Employee createEmployee(Employee employee, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));
        employee.setDepartment(department);
        Employee savedEmployee = employeeRepository.save(employee);

        // Write audit log to secondary database (Exercise 9)
        AuditLog auditLog = AuditLog.builder()
                .action("CREATE_EMPLOYEE")
                .detail("Created employee: " + savedEmployee.getName() + " in department: " + department.getName() + " (ID: " + savedEmployee.getId() + ")")
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);

        return savedEmployee;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " + id));
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional("primaryTransactionManager")
    public Employee updateEmployee(Long id, Employee employeeDetails, Long departmentId) {
        Employee employee = getEmployeeById(id);
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        employee.setName(employeeDetails.getName());
        employee.setEmail(employeeDetails.getEmail());
        employee.setDepartment(department);
        
        Employee updatedEmployee = employeeRepository.save(employee);

        // Write audit log to secondary database (Exercise 9)
        AuditLog auditLog = AuditLog.builder()
                .action("UPDATE_EMPLOYEE")
                .detail("Updated employee: " + updatedEmployee.getName() + " (ID: " + updatedEmployee.getId() + ")")
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);

        return updatedEmployee;
    }

    @Override
    @Transactional("primaryTransactionManager")
    public void deleteEmployee(Long id) {
        Employee employee = getEmployeeById(id);
        employeeRepository.delete(employee);

        // Write audit log to secondary database (Exercise 9)
        AuditLog auditLog = AuditLog.builder()
                .action("DELETE_EMPLOYEE")
                .detail("Deleted employee: " + employee.getName() + " (ID: " + id + ")")
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }

    // --- Pagination & Sorting (Exercise 6) ---
    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Page<Employee> getAllEmployees(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findAll(pageable);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Page<Employee> searchEmployeesByName(String name, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? 
                Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return employeeRepository.findByNameContaining(name, pageable);
    }

    // --- Custom Queries (Exercise 5) ---
    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Employee> getEmployeesByDepartmentName(String deptName) {
        return employeeRepository.findEmployeesByDeptName(deptName);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Employee> getEmployeesByEmailDomain(String domain) {
        return employeeRepository.findEmployeesByEmailDomain(domain);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Employee getEmployeeByEmailNamed(String email) {
        Employee emp = employeeRepository.findByEmailNamed(email);
        if (emp == null) {
            throw new ResourceNotFoundException("Employee not found with email: " + email);
        }
        return emp;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Employee> getEmployeesByDepartmentNameNamed(String deptName) {
        return employeeRepository.findByDepartmentNameNamed(deptName);
    }

    // --- Projections (Exercise 8) ---
    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<EmployeeProjection> getEmployeeProjections() {
        return employeeRepository.findAllProjectedBy();
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<EmployeeDto> getEmployeeDtos() {
        return employeeRepository.findAllEmployeeDtos();
    }
}

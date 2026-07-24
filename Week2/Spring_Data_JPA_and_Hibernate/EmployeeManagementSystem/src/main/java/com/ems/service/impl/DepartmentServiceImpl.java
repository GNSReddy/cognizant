package com.ems.service.impl;

import com.ems.entity.primary.Department;
import com.ems.entity.secondary.AuditLog;
import com.ems.exception.ResourceNotFoundException;
import com.ems.repository.primary.DepartmentRepository;
import com.ems.repository.secondary.AuditLogRepository;
import com.ems.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final AuditLogRepository auditLogRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository, AuditLogRepository auditLogRepository) {
        this.departmentRepository = departmentRepository;
        this.auditLogRepository = auditLogRepository;
    }

    @Override
    @Transactional("primaryTransactionManager")
    public Department createDepartment(Department department) {
        Department savedDept = departmentRepository.save(department);
        
        // Write audit log to secondary database (Exercise 9)
        AuditLog auditLog = AuditLog.builder()
                .action("CREATE_DEPARTMENT")
                .detail("Created department: " + savedDept.getName() + " with ID: " + savedDept.getId())
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
        
        return savedDept;
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + id));
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    @Transactional("primaryTransactionManager")
    public Department updateDepartment(Long id, Department departmentDetails) {
        Department department = getDepartmentById(id);
        department.setName(departmentDetails.getName());
        Department updatedDept = departmentRepository.save(department);
        
        // Write audit log to secondary database (Exercise 9)
        AuditLog auditLog = AuditLog.builder()
                .action("UPDATE_DEPARTMENT")
                .detail("Updated department: " + updatedDept.getName() + " (ID: " + updatedDept.getId() + ")")
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
        
        return updatedDept;
    }

    @Override
    @Transactional("primaryTransactionManager")
    public void deleteDepartment(Long id) {
        Department department = getDepartmentById(id);
        departmentRepository.delete(department);
        
        // Write audit log to secondary database (Exercise 9)
        AuditLog auditLog = AuditLog.builder()
                .action("DELETE_DEPARTMENT")
                .detail("Deleted department: " + department.getName() + " (ID: " + id + ")")
                .timestamp(LocalDateTime.now())
                .build();
        auditLogRepository.save(auditLog);
    }

    @Override
    @Transactional(value = "primaryTransactionManager", readOnly = true)
    public Department getDepartmentByName(String name) {
        Department department = departmentRepository.findByName(name);
        if (department == null) {
            throw new ResourceNotFoundException("Department not found with name: " + name);
        }
        return department;
    }
}

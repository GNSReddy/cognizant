package com.ems.service.impl;

import com.ems.entity.primary.Department;
import com.ems.entity.primary.Employee;
import com.ems.exception.ResourceNotFoundException;
import com.ems.repository.primary.DepartmentRepository;
import com.ems.service.BatchService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BatchServiceImpl implements BatchService {

    @PersistenceContext(unitName = "primary")
    private EntityManager entityManager;

    private final DepartmentRepository departmentRepository;

    @Autowired
    public BatchServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    @Transactional("primaryTransactionManager")
    public void batchInsertEmployees(List<Employee> employees, Long departmentId) {
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id: " + departmentId));

        int batchSize = 20; // Align with spring.jpa.properties.hibernate.jdbc.batch_size
        
        for (int i = 0; i < employees.size(); i++) {
            Employee employee = employees.get(i);
            employee.setDepartment(department);
            
            entityManager.persist(employee);
            
            // Periodically flush a batch of inserts and release memory
            if (i > 0 && i % batchSize == 0) {
                entityManager.flush();
                entityManager.clear();
                
                // Merge department back to the persistence context since the context was cleared
                department = entityManager.merge(department);
            }
        }
        // Flush and clear remaining entities
        entityManager.flush();
        entityManager.clear();
    }
}

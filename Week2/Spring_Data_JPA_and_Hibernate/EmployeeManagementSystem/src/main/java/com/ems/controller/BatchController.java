package com.ems.controller;

import com.ems.entity.primary.Employee;
import com.ems.service.BatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class BatchController {

    private final BatchService batchService;

    @Autowired
    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @PostMapping("/employees")
    public ResponseEntity<String> batchInsertEmployees(@RequestParam Long departmentId, @RequestParam int count) {
        List<Employee> employees = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            employees.add(Employee.builder()
                    .name("BatchEmployee_" + i)
                    .email("batchemp_" + i + "_" + System.currentTimeMillis() + "@company.com")
                    .build());
        }
        
        long startTime = System.currentTimeMillis();
        batchService.batchInsertEmployees(employees, departmentId);
        long endTime = System.currentTimeMillis();
        
        return ResponseEntity.ok("Successfully batch-inserted " + count + " employees in " + (endTime - startTime) + " ms.");
    }
}

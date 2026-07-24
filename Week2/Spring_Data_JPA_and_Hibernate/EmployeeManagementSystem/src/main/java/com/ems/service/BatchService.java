package com.ems.service;

import com.ems.entity.primary.Employee;

import java.util.List;

public interface BatchService {

    void batchInsertEmployees(List<Employee> employees, Long departmentId);
}

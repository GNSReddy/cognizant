package com.ems.repository.primary;

import com.ems.entity.primary.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    // Derived query methods
    Department findByName(String name);
    List<Department> findByNameContaining(String name);
}

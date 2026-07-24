package com.ems.projection;

import org.springframework.beans.factory.annotation.Value;

public interface EmployeeProjection {

    // Closed projection fields (highly efficient)
    String getName();
    String getEmail();
    String getDepartmentName();

    // Open projection field using SpEL @Value (evaluates expressions at runtime)
    @Value("#{target.name + ' (' + target.email + ')'}")
    String getFullNameWithEmail();
}

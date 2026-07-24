package com.cognizant.springlearn.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Thrown when an employee with the given id is not found.
 *
 * Handbook 4 – "Add EmployeeNotFoundException"
 * Used by EmployeeService.updateEmployee() and deleteEmployee() when the id
 * does not match any employee in the EMPLOYEE_LIST.
 *
 * @ResponseStatus causes Spring MVC to respond with HTTP 404 Not Found
 * and the reason string "Employee not found" in the response header.
 *
 * Example:
 *   DELETE /employees/99 → EmployeeNotFoundException → HTTP 404
 *   PUT /employees with id=99 → EmployeeNotFoundException → HTTP 404
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Employee not found")
public class EmployeeNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmployeeNotFoundException() {
        super("Employee not found");
    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}

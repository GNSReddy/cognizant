package com.cognizant.springlearn.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Employee model with Bean Validation and Jackson date formatting (Handbook 4).
 *
 * Handbook 4 validation annotations:
 *   id         – @NotNull, @Min(1)
 *   name       – @NotNull, @NotBlank, @Size(min=1, max=30)
 *   salary     – @NotNull, @Min(0)
 *   dateOfBirth– @JsonFormat(pattern="dd/MM/yyyy") – Jackson parse/format pattern
 *
 * @JsonFormat on dateOfBirth ensures Jackson both:
 *   1. Deserialises "15/03/1990" from request body (POST/PUT)
 *   2. Serialises back as "15/03/1990" in the response
 *
 * @Valid on controller parameter triggers all annotations below.
 * If any fail, MethodArgumentNotValidException is thrown →
 * GlobalExceptionHandler catches it → HTTP 400 with field errors.
 */
public class Employee {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    /** Employee id – must not be null and must be a positive integer */
    @NotNull(message = "Employee id must not be null")
    @Min(value = 1, message = "Employee id must be greater than or equal to 1")
    private Integer id;

    /** Employee name – required, non-blank, max 30 chars */
    @NotNull(message = "Employee name must not be null")
    @NotBlank(message = "Employee name must not be blank")
    @Size(min = 1, max = 30, message = "Employee name must be between 1 and 30 characters")
    private String name;

    /** Employee salary – must not be null, minimum 0 */
    @NotNull(message = "Employee salary must not be null")
    @Min(value = 0, message = "Employee salary must be greater than or equal to 0")
    private Double salary;

    /** Whether employee is permanent or contractual */
    private Boolean permanent;

    /**
     * Date of birth stored as string in dd/MM/yyyy format.
     * @JsonFormat instructs Jackson to parse/format dates using this pattern.
     * Without this annotation, Jackson defaults to ISO-8601 (yyyy-MM-dd) and
     * would fail to parse "15/03/1990".
     */
    @JsonFormat(pattern = "dd/MM/yyyy")
    private String dateOfBirth;

    /** Nested department – injected from employee.xml via <ref bean="..."/> */
    private Department department;

    /** List of skills – mapped from <list><ref bean="..."/></list> in employee.xml */
    private List<Skill> skills;

    public Employee() {
        LOGGER.debug("Inside Employee Constructor.");
    }

    public Integer getId() {
        LOGGER.debug("Inside Employee getId() - id: {}", id);
        return id;
    }

    public void setId(Integer id) {
        LOGGER.debug("Inside Employee setId() - id: {}", id);
        this.id = id;
    }

    public String getName() {
        LOGGER.debug("Inside Employee getName() - name: {}", name);
        return name;
    }

    public void setName(String name) {
        LOGGER.debug("Inside Employee setName() - name: {}", name);
        this.name = name;
    }

    public Double getSalary() {
        LOGGER.debug("Inside Employee getSalary() - salary: {}", salary);
        return salary;
    }

    public void setSalary(Double salary) {
        LOGGER.debug("Inside Employee setSalary() - salary: {}", salary);
        this.salary = salary;
    }

    public Boolean getPermanent() {
        LOGGER.debug("Inside Employee getPermanent() - permanent: {}", permanent);
        return permanent;
    }

    public void setPermanent(Boolean permanent) {
        LOGGER.debug("Inside Employee setPermanent() - permanent: {}", permanent);
        this.permanent = permanent;
    }

    public String getDateOfBirth() {
        LOGGER.debug("Inside Employee getDateOfBirth() - dob: {}", dateOfBirth);
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        LOGGER.debug("Inside Employee setDateOfBirth() - dob: {}", dateOfBirth);
        this.dateOfBirth = dateOfBirth;
    }

    public Department getDepartment() {
        LOGGER.debug("Inside Employee getDepartment()");
        return department;
    }

    public void setDepartment(Department department) {
        LOGGER.debug("Inside Employee setDepartment() - dept: {}", department);
        this.department = department;
    }

    public List<Skill> getSkills() {
        LOGGER.debug("Inside Employee getSkills()");
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        LOGGER.debug("Inside Employee setSkills() - {} skills", skills != null ? skills.size() : 0);
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary
                + ", permanent=" + permanent + ", dateOfBirth='" + dateOfBirth
                + "', department=" + department + ", skills=" + skills + "}";
    }
}

package com.cognizant.springlearn.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Employee model representing an employee with personal and professional details.
 * Includes validation annotations as per Handbook 4 requirements.
 */
public class Employee {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    @NotNull
    @Min(1)
    private Integer id;

    @NotNull
    @NotBlank
    @Size(min = 1, max = 30)
    private String name;

    @NotNull
    @Min(0)
    private Double salary;

    @NotNull
    private Boolean permanent;

    /** Date stored as String and parsed with dd/MM/yyyy pattern for JSON serialization. */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private String dateOfBirth;

    @Valid
    private Department department;

    @Valid
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
        LOGGER.debug("Inside Employee setDepartment()");
        this.department = department;
    }

    public List<Skill> getSkills() {
        LOGGER.debug("Inside Employee getSkills()");
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        LOGGER.debug("Inside Employee setSkills()");
        this.skills = skills;
    }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', salary=" + salary
                + ", permanent=" + permanent + ", dateOfBirth='" + dateOfBirth
                + "', department=" + department + ", skills=" + skills + "}";
    }
}

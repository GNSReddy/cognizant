package com.cognizant.springlearn.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Employee model – represents an employee with personal and professional details.
 *
 * Handbook 3: Populated from employee.xml Spring XML configuration.
 * department is injected as a nested bean reference.
 * skills is injected as a <list> of bean references.
 *
 * Jackson serialises this fully to JSON including nested department and skills array.
 * Example JSON response:
 * {
 *   "id": 1,
 *   "name": "Alice Johnson",
 *   "salary": 75000.0,
 *   "permanent": true,
 *   "dateOfBirth": "15/03/1990",
 *   "department": {"id":1,"name":"Information Technology"},
 *   "skills": [{"id":1,"name":"Java"},{"id":2,"name":"Spring"}]
 * }
 */
public class Employee {

    private static final Logger LOGGER = LoggerFactory.getLogger(Employee.class);

    private Integer id;
    private String name;
    private Double salary;
    private Boolean permanent;
    private String dateOfBirth;
    private Department department;
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

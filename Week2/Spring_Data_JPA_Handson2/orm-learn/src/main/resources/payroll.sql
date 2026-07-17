-- ============================================================
-- payroll.sql
-- Hands-on 3: Create Payroll Tables and Bean Mapping
-- Handbook: Spring Data JPA Hands-on 2
-- Run in MySQL BEFORE starting the Spring Boot application.
-- ddl-auto=validate requires all tables to exist before startup.
-- ============================================================

USE ormlearn;

-- ── Table 1: department ───────────────────────────────────────────────────
-- dept_id   : Auto-increment primary key
-- dept_name : Name of the department
CREATE TABLE IF NOT EXISTS department (
    dept_id   INT         NOT NULL AUTO_INCREMENT,
    dept_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (dept_id)
);

-- ── Table 2: employee ─────────────────────────────────────────────────────
-- emp_id              : Auto-increment primary key
-- emp_name            : Full name of employee
-- emp_salary          : Monthly salary (DECIMAL)
-- emp_permanent       : 1 = permanent employee, 0 = contract
-- emp_date_of_joining : Date of joining the company
-- dept_id             : Foreign key to department table
CREATE TABLE IF NOT EXISTS employee (
    emp_id              INT           NOT NULL AUTO_INCREMENT,
    emp_name            VARCHAR(100)  NOT NULL,
    emp_salary          DECIMAL(10,2) NOT NULL,
    emp_permanent       BIT(1)        NOT NULL DEFAULT 0,
    emp_date_of_joining DATE          NOT NULL,
    dept_id             INT           NOT NULL,
    PRIMARY KEY (emp_id),
    CONSTRAINT fk_emp_dept FOREIGN KEY (dept_id) REFERENCES department(dept_id)
);

-- ── Table 3: skill ────────────────────────────────────────────────────────
-- skill_id   : Auto-increment primary key
-- skill_name : Name of the technical/functional skill
CREATE TABLE IF NOT EXISTS skill (
    skill_id   INT         NOT NULL AUTO_INCREMENT,
    skill_name VARCHAR(50) NOT NULL,
    PRIMARY KEY (skill_id)
);

-- ── Table 4: employee_skill (Join table for ManyToMany) ───────────────────
-- emp_id   : Foreign key to employee table
-- skill_id : Foreign key to skill table
-- Composite primary key (emp_id, skill_id) ensures no duplicate mappings
CREATE TABLE IF NOT EXISTS employee_skill (
    emp_id   INT NOT NULL,
    skill_id INT NOT NULL,
    PRIMARY KEY (emp_id, skill_id),
    CONSTRAINT fk_es_emp   FOREIGN KEY (emp_id)   REFERENCES employee(emp_id),
    CONSTRAINT fk_es_skill FOREIGN KEY (skill_id) REFERENCES skill(skill_id)
);

-- ── Clear existing data (order matters: child tables first) ───────────────
DELETE FROM employee_skill;
DELETE FROM employee;
DELETE FROM skill;
DELETE FROM department;

-- Reset auto-increment counters
ALTER TABLE department    AUTO_INCREMENT = 1;
ALTER TABLE employee      AUTO_INCREMENT = 1;
ALTER TABLE skill         AUTO_INCREMENT = 1;

-- ── Insert Departments ────────────────────────────────────────────────────
INSERT INTO department (dept_name) VALUES ('Finance');
INSERT INTO department (dept_name) VALUES ('HR');
INSERT INTO department (dept_name) VALUES ('IT');
INSERT INTO department (dept_name) VALUES ('Marketing');

-- ── Insert Skills ─────────────────────────────────────────────────────────
INSERT INTO skill (skill_name) VALUES ('Java');
INSERT INTO skill (skill_name) VALUES ('Python');
INSERT INTO skill (skill_name) VALUES ('SQL');
INSERT INTO skill (skill_name) VALUES ('Excel');
INSERT INTO skill (skill_name) VALUES ('Communication');
INSERT INTO skill (skill_name) VALUES ('Spring Boot');

-- ── Insert Employees ─────────────────────────────────────────────────────
-- dept_id: 1=Finance, 2=HR, 3=IT, 4=Marketing
-- emp_permanent: 1=permanent, 0=contract

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Alice Johnson', 75000.00, 1, '2015-06-15', 3);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Bob Smith', 65000.00, 1, '2016-03-01', 1);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Carol Williams', 55000.00, 0, '2018-09-10', 2);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('David Brown', 80000.00, 1, '2014-01-20', 3);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Eve Davis', 70000.00, 1, '2017-07-05', 4);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Frank Miller', 90000.00, 1, '2013-11-30', 3);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Grace Wilson', 48000.00, 0, '2019-02-14', 2);

INSERT INTO employee (emp_name, emp_salary, emp_permanent, emp_date_of_joining, dept_id)
VALUES ('Henry Moore', 62000.00, 1, '2016-08-22', 1);

-- ── Insert Employee-Skill Mappings ────────────────────────────────────────
-- emp_id: 1=Alice, 2=Bob, 3=Carol, 4=David, 5=Eve, 6=Frank, 7=Grace, 8=Henry
-- skill_id: 1=Java, 2=Python, 3=SQL, 4=Excel, 5=Communication, 6=Spring Boot

INSERT INTO employee_skill (emp_id, skill_id) VALUES (1, 1); -- Alice - Java
INSERT INTO employee_skill (emp_id, skill_id) VALUES (1, 3); -- Alice - SQL
INSERT INTO employee_skill (emp_id, skill_id) VALUES (1, 6); -- Alice - Spring Boot

INSERT INTO employee_skill (emp_id, skill_id) VALUES (2, 3); -- Bob - SQL
INSERT INTO employee_skill (emp_id, skill_id) VALUES (2, 4); -- Bob - Excel

INSERT INTO employee_skill (emp_id, skill_id) VALUES (3, 4); -- Carol - Excel
INSERT INTO employee_skill (emp_id, skill_id) VALUES (3, 5); -- Carol - Communication

INSERT INTO employee_skill (emp_id, skill_id) VALUES (4, 1); -- David - Java
INSERT INTO employee_skill (emp_id, skill_id) VALUES (4, 2); -- David - Python
INSERT INTO employee_skill (emp_id, skill_id) VALUES (4, 6); -- David - Spring Boot

INSERT INTO employee_skill (emp_id, skill_id) VALUES (5, 5); -- Eve - Communication
INSERT INTO employee_skill (emp_id, skill_id) VALUES (5, 4); -- Eve - Excel

INSERT INTO employee_skill (emp_id, skill_id) VALUES (6, 1); -- Frank - Java
INSERT INTO employee_skill (emp_id, skill_id) VALUES (6, 2); -- Frank - Python
INSERT INTO employee_skill (emp_id, skill_id) VALUES (6, 3); -- Frank - SQL
INSERT INTO employee_skill (emp_id, skill_id) VALUES (6, 6); -- Frank - Spring Boot

INSERT INTO employee_skill (emp_id, skill_id) VALUES (7, 5); -- Grace - Communication
INSERT INTO employee_skill (emp_id, skill_id) VALUES (8, 3); -- Henry - SQL
INSERT INTO employee_skill (emp_id, skill_id) VALUES (8, 4); -- Henry - Excel

-- ── Verify ────────────────────────────────────────────────────────────────
SELECT 'department' AS table_name, COUNT(*) AS rows FROM department
UNION ALL
SELECT 'employee',   COUNT(*) FROM employee
UNION ALL
SELECT 'skill',      COUNT(*) FROM skill
UNION ALL
SELECT 'employee_skill', COUNT(*) FROM employee_skill;

SET SERVEROUTPUT ON;

-- Scenario 1 : Customer Management Package

CREATE OR REPLACE PACKAGE CustomerManagement AS

    PROCEDURE AddCustomer(
        p_customerId NUMBER,
        p_name VARCHAR2,
        p_dob DATE,
        p_balance NUMBER
    );

    PROCEDURE UpdateCustomer(
        p_customerId NUMBER,
        p_balance NUMBER
    );

    FUNCTION GetCustomerBalance(
        p_customerId NUMBER
    ) RETURN NUMBER;

END CustomerManagement;
/

CREATE OR REPLACE PACKAGE BODY CustomerManagement AS

    PROCEDURE AddCustomer(
        p_customerId NUMBER,
        p_name VARCHAR2,
        p_dob DATE,
        p_balance NUMBER
    )
    IS
    BEGIN

        INSERT INTO Customers
        VALUES(
            p_customerId,
            p_name,
            p_dob,
            p_balance,
            SYSDATE
        );

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Customer Added');

    END;

    PROCEDURE UpdateCustomer(
        p_customerId NUMBER,
        p_balance NUMBER
    )
    IS
    BEGIN

        UPDATE Customers
        SET Balance = p_balance,
            LastModified = SYSDATE
        WHERE CustomerID = p_customerId;

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Customer Updated');

    END;

    FUNCTION GetCustomerBalance(
        p_customerId NUMBER
    )
    RETURN NUMBER
    IS
        v_balance NUMBER;
    BEGIN

        SELECT Balance
        INTO v_balance
        FROM Customers
        WHERE CustomerID = p_customerId;

        RETURN v_balance;

    END;

END CustomerManagement;
/

BEGIN
    CustomerManagement.AddCustomer(
        10,
        'Rahul',
        TO_DATE('2002-08-15','YYYY-MM-DD'),
        25000
    );

    CustomerManagement.UpdateCustomer(10,30000);

    DBMS_OUTPUT.PUT_LINE(
        'Balance : '||
        CustomerManagement.GetCustomerBalance(10)
    );
END;
/

-- Scenario 2 : Employee Management Package

CREATE OR REPLACE PACKAGE EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_employeeId NUMBER,
        p_name VARCHAR2,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2,
        p_hireDate DATE
    );

    PROCEDURE UpdateEmployeeSalary(
        p_employeeId NUMBER,
        p_salary NUMBER
    );

    FUNCTION CalculateAnnualSalary(
        p_employeeId NUMBER
    ) RETURN NUMBER;

END EmployeeManagement;
/

CREATE OR REPLACE PACKAGE BODY EmployeeManagement AS

    PROCEDURE HireEmployee(
        p_employeeId NUMBER,
        p_name VARCHAR2,
        p_position VARCHAR2,
        p_salary NUMBER,
        p_department VARCHAR2,
        p_hireDate DATE
    )
    IS
    BEGIN

        INSERT INTO Employees
        VALUES(
            p_employeeId,
            p_name,
            p_position,
            p_salary,
            p_department,
            p_hireDate
        );

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Employee Added');

    END;

    PROCEDURE UpdateEmployeeSalary(
        p_employeeId NUMBER,
        p_salary NUMBER
    )
    IS
    BEGIN

        UPDATE Employees
        SET Salary = p_salary
        WHERE EmployeeID = p_employeeId;

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Salary Updated');

    END;

    FUNCTION CalculateAnnualSalary(
        p_employeeId NUMBER
    )
    RETURN NUMBER
    IS
        v_salary NUMBER;
    BEGIN

        SELECT Salary
        INTO v_salary
        FROM Employees
        WHERE EmployeeID = p_employeeId;

        RETURN v_salary * 12;

    END;

END EmployeeManagement;
/

BEGIN

    EmployeeManagement.HireEmployee(
        10,
        'Kiran',
        'Developer',
        60000,
        'IT',
        SYSDATE
    );

    EmployeeManagement.UpdateEmployeeSalary(
        10,
        65000
    );

    DBMS_OUTPUT.PUT_LINE(
        'Annual Salary : '||
        EmployeeManagement.CalculateAnnualSalary(10)
    );

END;
/

-- Scenario 3 : Account Operations Package

CREATE OR REPLACE PACKAGE AccountOperations AS

    PROCEDURE OpenAccount(
        p_accountId NUMBER,
        p_customerId NUMBER,
        p_accountType VARCHAR2,
        p_balance NUMBER
    );

    PROCEDURE CloseAccount(
        p_accountId NUMBER
    );

    FUNCTION GetTotalBalance(
        p_customerId NUMBER
    ) RETURN NUMBER;

END AccountOperations;
/

CREATE OR REPLACE PACKAGE BODY AccountOperations AS

    PROCEDURE OpenAccount(
        p_accountId NUMBER,
        p_customerId NUMBER,
        p_accountType VARCHAR2,
        p_balance NUMBER
    )
    IS
    BEGIN

        INSERT INTO Accounts
        VALUES(
            p_accountId,
            p_customerId,
            p_accountType,
            p_balance,
            SYSDATE
        );

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Account Opened');

    END;

    PROCEDURE CloseAccount(
        p_accountId NUMBER
    )
    IS
    BEGIN

        DELETE FROM Accounts
        WHERE AccountID = p_accountId;

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Account Closed');

    END;

    FUNCTION GetTotalBalance(
        p_customerId NUMBER
    )
    RETURN NUMBER
    IS
        v_total NUMBER;
    BEGIN

        SELECT SUM(Balance)
        INTO v_total
        FROM Accounts
        WHERE CustomerID = p_customerId;

        RETURN NVL(v_total,0);

    END;

END AccountOperations;
/

BEGIN

    AccountOperations.OpenAccount(
        10,
        1,
        'Savings',
        5000
    );

    DBMS_OUTPUT.PUT_LINE(
        'Total Balance : '||
        AccountOperations.GetTotalBalance(1)
    );

    AccountOperations.CloseAccount(10);

END;
/
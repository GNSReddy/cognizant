SET SERVEROUTPUT ON;

-- Scenario 1: Safe Fund Transfer

CREATE OR REPLACE PROCEDURE SafeTransferFunds(
    p_fromAccount IN NUMBER,
    p_toAccount IN NUMBER,
    p_amount IN NUMBER
)
IS
    v_balance NUMBER;
BEGIN

    SELECT Balance
    INTO v_balance
    FROM Accounts
    WHERE AccountID = p_fromAccount;

    IF v_balance < p_amount THEN
        RAISE_APPLICATION_ERROR(-20001,'Insufficient Balance');
    END IF;

    UPDATE Accounts
    SET Balance = Balance - p_amount
    WHERE AccountID = p_fromAccount;

    UPDATE Accounts
    SET Balance = Balance + p_amount
    WHERE AccountID = p_toAccount;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Fund Transfer Successful');

EXCEPTION

    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Account Not Found');

    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE(SQLERRM);

END;
/

BEGIN
    SafeTransferFunds(1,2,500);
END;
/

-- Scenario 2: Update Employee Salary

CREATE OR REPLACE PROCEDURE UpdateSalary(

    p_employeeId NUMBER,
    p_percentage NUMBER

)
IS
BEGIN

    UPDATE Employees

    SET Salary = Salary + (Salary * p_percentage / 100)

    WHERE EmployeeID = p_employeeId;

    IF SQL%ROWCOUNT = 0 THEN
        RAISE NO_DATA_FOUND;
    END IF;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Salary Updated Successfully');

EXCEPTION

    WHEN NO_DATA_FOUND THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE('Employee Not Found');

    WHEN OTHERS THEN
        ROLLBACK;
        DBMS_OUTPUT.PUT_LINE(SQLERRM);

END;
/

BEGIN
    UpdateSalary(1,10);
END;
/

-- Scenario 3: Add New Customer

CREATE OR REPLACE PROCEDURE AddNewCustomer(

    p_customerId NUMBER,
    p_name VARCHAR2,
    p_dob DATE,
    p_balance NUMBER

)
IS
BEGIN

    INSERT INTO Customers(

        CustomerID,
        Name,
        DOB,
        Balance,
        LastModified

    )

    VALUES(

        p_customerId,
        p_name,
        p_dob,
        p_balance,
        SYSDATE

    );

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Customer Added Successfully');

EXCEPTION

    WHEN DUP_VAL_ON_INDEX THEN

        ROLLBACK;

        DBMS_OUTPUT.PUT_LINE('Customer ID Already Exists');

    WHEN OTHERS THEN

        ROLLBACK;

        DBMS_OUTPUT.PUT_LINE(SQLERRM);

END;
/

BEGIN
    AddNewCustomer(
        3,
        'Rahul',
        TO_DATE('2002-08-15','YYYY-MM-DD'),
        15000
    );
END;
/
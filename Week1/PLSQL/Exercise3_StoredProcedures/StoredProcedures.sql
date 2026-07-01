SET SERVEROUTPUT ON;

-- Scenario 1: Process Monthly Interest

CREATE OR REPLACE PROCEDURE ProcessMonthlyInterest
IS
BEGIN

    UPDATE Accounts
    SET Balance = Balance + (Balance * 0.01)
    WHERE AccountType = 'Savings';

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Monthly Interest Processed Successfully');

END;
/

BEGIN
    ProcessMonthlyInterest;
END;
/

-- Scenario 2: Update Employee Bonus

CREATE OR REPLACE PROCEDURE UpdateEmployeeBonus(

    p_department VARCHAR2,
    p_bonusPercentage NUMBER

)
IS
BEGIN

    UPDATE Employees

    SET Salary = Salary + (Salary * p_bonusPercentage / 100)

    WHERE Department = p_department;

    COMMIT;

    DBMS_OUTPUT.PUT_LINE('Bonus Updated Successfully');

END;
/

BEGIN

    UpdateEmployeeBonus('HR',10);

END;
/

-- Scenario 3: Transfer Funds

CREATE OR REPLACE PROCEDURE TransferFunds(

    p_fromAccount NUMBER,
    p_toAccount NUMBER,
    p_amount NUMBER

)
IS

    v_balance NUMBER;

BEGIN

    SELECT Balance
    INTO v_balance
    FROM Accounts
    WHERE AccountID = p_fromAccount;

    IF v_balance >= p_amount THEN

        UPDATE Accounts
        SET Balance = Balance - p_amount
        WHERE AccountID = p_fromAccount;

        UPDATE Accounts
        SET Balance = Balance + p_amount
        WHERE AccountID = p_toAccount;

        COMMIT;

        DBMS_OUTPUT.PUT_LINE('Fund Transfer Successful');

    ELSE

        DBMS_OUTPUT.PUT_LINE('Insufficient Balance');

    END IF;

END;
/

BEGIN

    TransferFunds(1,2,300);

END;
/